/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

/**
 * Detail information of a SQL.
 */
public class SQLDetail {
    private long id;

    private String schema;

    private String objectName;

    private String definition;

    public long getId() {
        return id;
    }

    public SQLDetail setId(long id) {
        this.id = id;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public SQLDetail setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public SQLDetail setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public SQLDetail setDefinition(String definition) {
        this.definition = definition;
        return this;
    }
}
