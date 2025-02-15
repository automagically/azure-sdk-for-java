// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.securityinsights.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.securityinsights.models.ClientInfo;
import com.azure.resourcemanager.securityinsights.models.ResourceWithEtag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** Represents an incident comment. */
@Fluent
public final class IncidentCommentInner extends ResourceWithEtag {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(IncidentCommentInner.class);

    /*
     * Incident comment properties
     */
    @JsonProperty(value = "properties")
    private IncidentCommentProperties innerProperties;

    /**
     * Get the innerProperties property: Incident comment properties.
     *
     * @return the innerProperties value.
     */
    private IncidentCommentProperties innerProperties() {
        return this.innerProperties;
    }

    /** {@inheritDoc} */
    @Override
    public IncidentCommentInner withEtag(String etag) {
        super.withEtag(etag);
        return this;
    }

    /**
     * Get the createdTimeUtc property: The time the comment was created.
     *
     * @return the createdTimeUtc value.
     */
    public OffsetDateTime createdTimeUtc() {
        return this.innerProperties() == null ? null : this.innerProperties().createdTimeUtc();
    }

    /**
     * Get the lastModifiedTimeUtc property: The time the comment was updated.
     *
     * @return the lastModifiedTimeUtc value.
     */
    public OffsetDateTime lastModifiedTimeUtc() {
        return this.innerProperties() == null ? null : this.innerProperties().lastModifiedTimeUtc();
    }

    /**
     * Get the message property: The comment message.
     *
     * @return the message value.
     */
    public String message() {
        return this.innerProperties() == null ? null : this.innerProperties().message();
    }

    /**
     * Set the message property: The comment message.
     *
     * @param message the message value to set.
     * @return the IncidentCommentInner object itself.
     */
    public IncidentCommentInner withMessage(String message) {
        if (this.innerProperties() == null) {
            this.innerProperties = new IncidentCommentProperties();
        }
        this.innerProperties().withMessage(message);
        return this;
    }

    /**
     * Get the author property: Describes the client that created the comment.
     *
     * @return the author value.
     */
    public ClientInfo author() {
        return this.innerProperties() == null ? null : this.innerProperties().author();
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    @Override
    public void validate() {
        super.validate();
        if (innerProperties() != null) {
            innerProperties().validate();
        }
    }
}
