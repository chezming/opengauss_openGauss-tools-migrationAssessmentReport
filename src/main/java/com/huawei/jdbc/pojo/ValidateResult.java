/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

import java.util.Map;

import com.huawei.jdbc.DatabaseType;

/**
 * The validate result of a SQL
 */
public class ValidateResult {
    private String id;

    private String sql;

    private String sqlType;

    private DatabaseType sourceDatabaseType;

    private DatabaseType targetDatabaseType;

    private boolean target;

    private String targetError;

    private boolean extension;

    private String extensionError;

    public String getId() {
        return id;
    }

    public String getSql() {
        return sql;
    }

    public DatabaseType getSourceDatabaseType() {
        return sourceDatabaseType;
    }

    public DatabaseType getTargetDatabaseType() {
        return targetDatabaseType;
    }

    public boolean isTarget() {
        return target;
    }

    public boolean isExtension() {
        return extension;
    }

    public String getTargetError() {
        return targetError;
    }

    public String getExtensionError() {
        return extensionError;
    }

    @Override
    public String toString() {
        return String.format("ValidateResult{id='%s', sql='%s', sqlType='%s', sourceDatabaseType='%s', " +
            "targetDatabaseType='%s', target='%s', targetError='%s', extension='%s', extensionError='%s'}",
            id, sql, sqlType, sourceDatabaseType, targetDatabaseType, target, targetError, extension, extensionError);
    }

    public ValidateResult setId(String id) {
        this.id = id;
        return this;
    }

    public ValidateResult setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public String getSqlType() {
        return sqlType;
    }

    public ValidateResult setSqlType(String sqlType) {
        this.sqlType = sqlType;
        return this;
    }

    public ValidateResult setSourceDatabaseType(DatabaseType sourceDatabaseType) {
        this.sourceDatabaseType = sourceDatabaseType;
        return this;
    }

    public ValidateResult setTargetDatabaseType(DatabaseType targetDatabaseType) {
        this.targetDatabaseType = targetDatabaseType;
        return this;
    }

    public ValidateResult setTarget(boolean target) {
        this.target = target;
        return this;
    }

    public ValidateResult setTargetError(String targetError) {
        this.targetError = targetError;
        return this;
    }

    public ValidateResult setExtension(boolean extension) {
        this.extension = extension;
        return this;
    }

    public ValidateResult setExtensionError(String extensionError) {
        this.extensionError = extensionError;
        return this;
    }
}
