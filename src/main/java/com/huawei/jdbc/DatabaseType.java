package com.huawei.jdbc;

public enum DatabaseType {
    OPENGAUSS("opengauss"),

    ORACLE("oracle");

    private String str;

    DatabaseType(String database) {
        this.str = database;
    }

    public String getStr() {
        return str;
    }
}
