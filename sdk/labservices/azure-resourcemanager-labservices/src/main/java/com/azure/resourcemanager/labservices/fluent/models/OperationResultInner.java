// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.labservices.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.management.exception.ManagementError;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.labservices.models.OperationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** A long running operation result. */
@Fluent
public final class OperationResultInner {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(OperationResultInner.class);

    /*
     * Fully qualified resource ID for the resource. Ex -
     * /subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/{resourceProviderNamespace}/{resourceType}/{resourceName}
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /*
     * The name of the resource
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /*
     * The operation status
     */
    @JsonProperty(value = "status", required = true)
    private OperationStatus status;

    /*
     * Start time
     */
    @JsonProperty(value = "startTime")
    private OffsetDateTime startTime;

    /*
     * End time
     */
    @JsonProperty(value = "endTime")
    private OffsetDateTime endTime;

    /*
     * Percent completion
     */
    @JsonProperty(value = "percentComplete")
    private Float percentComplete;

    /*
     * The error for a failure if the operation failed.
     */
    @JsonProperty(value = "error")
    private ManagementError error;

    /**
     * Get the id property: Fully qualified resource ID for the resource. Ex -
     * /subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/{resourceProviderNamespace}/{resourceType}/{resourceName}.
     *
     * @return the id value.
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the name property: The name of the resource.
     *
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the status property: The operation status.
     *
     * @return the status value.
     */
    public OperationStatus status() {
        return this.status;
    }

    /**
     * Set the status property: The operation status.
     *
     * @param status the status value to set.
     * @return the OperationResultInner object itself.
     */
    public OperationResultInner withStatus(OperationStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Get the startTime property: Start time.
     *
     * @return the startTime value.
     */
    public OffsetDateTime startTime() {
        return this.startTime;
    }

    /**
     * Set the startTime property: Start time.
     *
     * @param startTime the startTime value to set.
     * @return the OperationResultInner object itself.
     */
    public OperationResultInner withStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get the endTime property: End time.
     *
     * @return the endTime value.
     */
    public OffsetDateTime endTime() {
        return this.endTime;
    }

    /**
     * Set the endTime property: End time.
     *
     * @param endTime the endTime value to set.
     * @return the OperationResultInner object itself.
     */
    public OperationResultInner withEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get the percentComplete property: Percent completion.
     *
     * @return the percentComplete value.
     */
    public Float percentComplete() {
        return this.percentComplete;
    }

    /**
     * Set the percentComplete property: Percent completion.
     *
     * @param percentComplete the percentComplete value to set.
     * @return the OperationResultInner object itself.
     */
    public OperationResultInner withPercentComplete(Float percentComplete) {
        this.percentComplete = percentComplete;
        return this;
    }

    /**
     * Get the error property: The error for a failure if the operation failed.
     *
     * @return the error value.
     */
    public ManagementError error() {
        return this.error;
    }

    /**
     * Set the error property: The error for a failure if the operation failed.
     *
     * @param error the error value to set.
     * @return the OperationResultInner object itself.
     */
    public OperationResultInner withError(ManagementError error) {
        this.error = error;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (status() == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException("Missing required property status in model OperationResultInner"));
        }
    }
}
