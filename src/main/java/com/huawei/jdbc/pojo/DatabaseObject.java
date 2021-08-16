/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

/**
 * Database objects, index, tables, views, sequences, roles, users, etc...
 */
public class DatabaseObject {
    private long id;

    private String schema;

    private String objectName;

    private String type;

    private String definition;

    private ValidateResult validateResult;

    public ValidateResult getValidateResult() {
        return validateResult;
    }

    public DatabaseObject setValidateResult(ValidateResult validateResult) {
        this.validateResult = validateResult;
        return this;
    }

    public long getId() {
        return id;
    }

    public DatabaseObject setId(long id) {
        this.id = id;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public DatabaseObject setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public DatabaseObject setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getType() {
        return type;
    }

    public DatabaseObject setType(String type) {
        this.type = type;
        return this;
    }

    public String getDefinition() {
        return definition;
    }

    public DatabaseObject setDefinition(String definition) {
        this.definition = definition;
        return this;
    }
}
