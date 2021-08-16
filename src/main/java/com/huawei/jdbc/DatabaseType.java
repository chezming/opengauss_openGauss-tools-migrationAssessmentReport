/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc;

/**
 * Database type supported by current tool
 */
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
