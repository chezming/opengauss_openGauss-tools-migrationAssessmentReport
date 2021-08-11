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

    public ValidateResult(String id, String sql, String sqlType, DatabaseType sourceDatabaseType, DatabaseType targetDatabaseType,
            Map.Entry<Boolean, String> target, Map.Entry<Boolean, String> extension) {
        this.id = id;
        this.sql = sql;
        this.sqlType = sqlType;
        this.sourceDatabaseType = sourceDatabaseType;
        this.targetDatabaseType = targetDatabaseType;
        this.target = target.getKey();
        this.targetError = target.getValue();
        this.extension = extension.getKey();
        this.extensionError = extension.getValue();
    }

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
}
