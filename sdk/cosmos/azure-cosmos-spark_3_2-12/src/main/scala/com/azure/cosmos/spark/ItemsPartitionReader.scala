// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.cosmos.spark

import com.azure.cosmos.implementation.spark.OperationContextAndListenerTuple
import com.azure.cosmos.implementation.{CosmosClientMetadataCachesSnapshot, ImplementationBridgeHelpers, SparkBridgeImplementationInternal, Strings}
import com.azure.cosmos.models.{CosmosParameterizedQuery, CosmosQueryRequestOptions, ModelBridgeInternal}
import com.azure.cosmos.spark.diagnostics.{DiagnosticsContext, DiagnosticsLoader, LoggerHelper, SparkTaskContext}
import com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.spark.TaskContext
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.Row
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.connector.read.PartitionReader
import org.apache.spark.sql.types.StructType

// per spark task there will be one CosmosPartitionReader.
// This provides iterator to read from the assigned spark partition
// For now we are creating only one spark partition
private case class ItemsPartitionReader
(
  config: Map[String, String],
  feedRange: NormalizedRange,
  readSchema: StructType,
  cosmosQuery: CosmosParameterizedQuery,
  diagnosticsContext: DiagnosticsContext,
  cosmosClientStateHandle: Broadcast[CosmosClientMetadataCachesSnapshot],
  diagnosticsConfig: DiagnosticsConfig
)
  extends PartitionReader[InternalRow] {

  private lazy val log = LoggerHelper.getLogger(diagnosticsConfig, this.getClass)
  log.logInfo(s"Instantiated ${this.getClass.getSimpleName}")

  private val containerTargetConfig = CosmosContainerConfig.parseCosmosContainerConfig(config)
  log.logInfo(s"Reading from feed range $feedRange of " +
    s"container ${containerTargetConfig.database}.${containerTargetConfig.container} - " +
    s"correlationActivityId ${diagnosticsContext.correlationActivityId}, " +
    s"query: ${cosmosQuery.toString}")

  private val readConfig = CosmosReadConfig.parseCosmosReadConfig(config)
  private val clientCacheItem = CosmosClientCache(
    CosmosClientConfiguration(config, readConfig.forceEventualConsistency),
    Some(cosmosClientStateHandle),
    s"ItemsPartitionReader($feedRange, ${containerTargetConfig.database}.${containerTargetConfig.container})"
  )

  private val cosmosAsyncContainer = ThroughputControlHelper.getContainer(
    config, containerTargetConfig, clientCacheItem.client)
  SparkUtils.safeOpenConnectionInitCaches(cosmosAsyncContainer, log)

  private val queryOptions = ImplementationBridgeHelpers
    .CosmosQueryRequestOptionsHelper
    .getCosmosQueryRequestOptionsAccessor
    .disallowQueryPlanRetrieval(new CosmosQueryRequestOptions())

  private val cosmosSerializationConfig = CosmosSerializationConfig.parseSerializationConfig(config)
  private val cosmosRowConverter = CosmosRowConverter.get(cosmosSerializationConfig)

  private var operationContextAndListenerTuple: Option[OperationContextAndListenerTuple] = None

  initializeDiagnosticsIfConfigured()

  private def initializeDiagnosticsIfConfigured(): Unit = {
    if (diagnosticsConfig.mode.isDefined) {
      val taskContext = TaskContext.get
      assert(taskContext != null)

      val taskDiagnosticsContext = SparkTaskContext(
        diagnosticsContext.correlationActivityId,
        taskContext.stageId(),
        taskContext.partitionId(),
        taskContext.taskAttemptId(),
        feedRange.toString + " " + cosmosQuery.toString)

      val listener =
        DiagnosticsLoader.getDiagnosticsProvider(diagnosticsConfig).getLogger(this.getClass)

      operationContextAndListenerTuple =
        Some(new OperationContextAndListenerTuple(taskDiagnosticsContext, listener))
      ImplementationBridgeHelpers.CosmosQueryRequestOptionsHelper
        .getCosmosQueryRequestOptionsAccessor.setOperationContext(queryOptions, operationContextAndListenerTuple.get)
    }
  }

  queryOptions.setFeedRange(SparkBridgeImplementationInternal.toFeedRange(feedRange))

  private lazy val iterator = new TransientIOErrorsRetryingIterator(
    continuationToken => {

      if (!Strings.isNullOrWhiteSpace(continuationToken)) {
        ModelBridgeInternal.setQueryRequestOptionsContinuationTokenAndMaxItemCount(
          queryOptions, continuationToken, readConfig.maxItemCount)
      } else {
        // scalastyle:off null
        ModelBridgeInternal.setQueryRequestOptionsContinuationTokenAndMaxItemCount(
          queryOptions, null, readConfig.maxItemCount)
        // scalastyle:on null
      }

      queryOptions.setMaxBufferedItemCount(
        math.min(
          readConfig.maxItemCount * readConfig.prefetchBufferSize.toLong, // converting to long to avoid overflow when
                                                                          // multiplying to ints
          java.lang.Integer.MAX_VALUE
        ).toInt
      )

      ImplementationBridgeHelpers
        .CosmosQueryRequestOptionsHelper
        .getCosmosQueryRequestOptionsAccessor
        .setCorrelationActivityId(
          queryOptions,
          diagnosticsContext.correlationActivityId)

      cosmosAsyncContainer.queryItems(cosmosQuery.toSqlQuerySpec, queryOptions, classOf[ObjectNode])
    },
    readConfig.maxItemCount,
    readConfig.prefetchBufferSize,
    operationContextAndListenerTuple
  )

  private val rowSerializer: ExpressionEncoder.Serializer[Row] = RowSerializerPool.getOrCreateSerializer(readSchema)

  override def next(): Boolean = iterator.hasNext

  override def get(): InternalRow = {
    val objectNode = iterator.next()
    cosmosRowConverter.fromObjectNodeToInternalRow(
      readSchema,
      rowSerializer,
      objectNode,
      readConfig.schemaConversionMode)
  }

  override def close(): Unit = {
    this.iterator.close()
    RowSerializerPool.returnSerializerToPool(readSchema, rowSerializer)
    clientCacheItem.close()
  }
}
