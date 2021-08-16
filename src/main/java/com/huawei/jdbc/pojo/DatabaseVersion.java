/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

/**
 * Database version.
 */
public class DatabaseVersion {
    private String product;
    private String version;
    private String fullVersion;
    private String status;

    public String getProduct() {
        return product;
    }

    public DatabaseVersion setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public DatabaseVersion setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getFullVersion() {
        return fullVersion;
    }

    public DatabaseVersion setFullVersion(String fullVersion) {
        this.fullVersion = fullVersion;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DatabaseVersion setStatus(String status) {
        this.status = status;
        return this;
    }
}
