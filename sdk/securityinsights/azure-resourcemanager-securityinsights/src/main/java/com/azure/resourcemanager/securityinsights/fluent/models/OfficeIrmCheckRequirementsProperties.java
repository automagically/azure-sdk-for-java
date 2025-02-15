// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.securityinsights.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.securityinsights.models.DataConnectorTenantId;
import com.fasterxml.jackson.annotation.JsonIgnore;

/** OfficeIRM (Microsoft Insider Risk Management) requirements check properties. */
@Fluent
public final class OfficeIrmCheckRequirementsProperties extends DataConnectorTenantId {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(OfficeIrmCheckRequirementsProperties.class);

    /** {@inheritDoc} */
    @Override
    public OfficeIrmCheckRequirementsProperties withTenantId(String tenantId) {
        super.withTenantId(tenantId);
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    @Override
    public void validate() {
        super.validate();
    }
}
