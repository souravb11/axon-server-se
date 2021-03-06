/*
 * Copyright (c) 2017-2019 AxonIQ B.V. and/or licensed to AxonIQ B.V.
 * under one or more contributor license agreements.
 *
 *  Licensed under the AxonIQ Open Source License Agreement v1.0;
 *  you may not use this file except in compliance with the license.
 *
 */

package io.axoniq.axonserver.config;

/**
 * Configuration properties related to Axon Server Access Control.
 *
 * @author Marc Gathier
 */
public class AccessControlConfiguration {

    /**
     * Indicates that access control is enabled for the server.
     */
    private boolean enabled;
    /**
     * Timeout for authenticated tokens.
     */
    private long cacheTtl = 30000;
    /**
     * Token used to authenticate Axon Server instances in a cluster (only used by Enterprise Edition)
     */
    private String internalToken;
    /**
     * Token to be used by client applications connecting to Axon Server (only used by Standard Edition)
     */
    private String token;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getCacheTtl() {
        return cacheTtl;
    }

    public void setCacheTtl(long cacheTtl) {
        this.cacheTtl = cacheTtl;
    }

    public String getInternalToken() {
        return internalToken;
    }

    public void setInternalToken(String internalToken) {
        this.internalToken = internalToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
