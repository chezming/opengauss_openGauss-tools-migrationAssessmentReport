/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

/**
 * Database info.
 */
public class DbInfo {
    private Long dbId;

    private String switchoverStatus;

    private String instanceName;

    public Long getDbId() {
        return dbId;
    }

    public DbInfo setDbId(Long dbId) {
        this.dbId = dbId;
        return this;
    }

    public String getSwitchoverStatus() {
        return switchoverStatus;
    }

    public DbInfo setSwitchoverStatus(String switchoverStatus) {
        this.switchoverStatus = switchoverStatus;
        return this;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public DbInfo setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }
}