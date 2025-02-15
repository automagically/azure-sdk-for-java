// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.securityinsights.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** Describes external reference. */
@Fluent
public final class ThreatIntelligenceExternalReference {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(ThreatIntelligenceExternalReference.class);

    /*
     * External reference description
     */
    @JsonProperty(value = "description")
    private String description;

    /*
     * External reference ID
     */
    @JsonProperty(value = "externalId")
    private String externalId;

    /*
     * External reference source name
     */
    @JsonProperty(value = "sourceName")
    private String sourceName;

    /*
     * External reference URL
     */
    @JsonProperty(value = "url")
    private String url;

    /*
     * External reference hashes
     */
    @JsonProperty(value = "hashes")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
    private Map<String, String> hashes;

    /**
     * Get the description property: External reference description.
     *
     * @return the description value.
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description property: External reference description.
     *
     * @param description the description value to set.
     * @return the ThreatIntelligenceExternalReference object itself.
     */
    public ThreatIntelligenceExternalReference withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the externalId property: External reference ID.
     *
     * @return the externalId value.
     */
    public String externalId() {
        return this.externalId;
    }

    /**
     * Set the externalId property: External reference ID.
     *
     * @param externalId the externalId value to set.
     * @return the ThreatIntelligenceExternalReference object itself.
     */
    public ThreatIntelligenceExternalReference withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * Get the sourceName property: External reference source name.
     *
     * @return the sourceName value.
     */
    public String sourceName() {
        return this.sourceName;
    }

    /**
     * Set the sourceName property: External reference source name.
     *
     * @param sourceName the sourceName value to set.
     * @return the ThreatIntelligenceExternalReference object itself.
     */
    public ThreatIntelligenceExternalReference withSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    /**
     * Get the url property: External reference URL.
     *
     * @return the url value.
     */
    public String url() {
        return this.url;
    }

    /**
     * Set the url property: External reference URL.
     *
     * @param url the url value to set.
     * @return the ThreatIntelligenceExternalReference object itself.
     */
    public ThreatIntelligenceExternalReference withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Get the hashes property: External reference hashes.
     *
     * @return the hashes value.
     */
    public Map<String, String> hashes() {
        return this.hashes;
    }

    /**
     * Set the hashes property: External reference hashes.
     *
     * @param hashes the hashes value to set.
     * @return the ThreatIntelligenceExternalReference object itself.
     */
    public ThreatIntelligenceExternalReference withHashes(Map<String, String> hashes) {
        this.hashes = hashes;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
