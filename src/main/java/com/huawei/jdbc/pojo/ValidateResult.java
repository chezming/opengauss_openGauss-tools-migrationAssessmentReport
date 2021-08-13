package com.huawei.jdbc.pojo;

import java.util.Map;

import com.huawei.jdbc.DatabaseType;

public class ValidateResult {
    private String id;

    private String sql;

    public String getSqlType() {
        return sqlType;
    }

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
        return "ValidateResult{" + "id='" + id + '\'' + ", sql='" + sql + '\'' + ", sqlType='" + sqlType + '\''
                + ", sourceDatabaseType=" + sourceDatabaseType + ", targetDatabaseType=" + targetDatabaseType
                + ", target=" + target + ", targetError='" + targetError + '\'' + ", extension=" + extension
                + ", extensionError='" + extensionError + '\'' + '}';
    }

    public ValidateResult setId(String id) {
        this.id = id;
        return this;
    }

    public ValidateResult setSql(String sql) {
        this.sql = sql;
        return this;
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
