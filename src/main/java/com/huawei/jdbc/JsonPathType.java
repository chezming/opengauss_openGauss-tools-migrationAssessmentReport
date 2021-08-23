/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc;

import java.io.File;

import com.huawei.jdbc.pojo.PathConst;

/**
 * Json file path
 */
public enum JsonPathType {
    VALIDATE_RESULT("validate_result.json"),

    STATISTIC("detail" + File.separator + "TABLE_DETAIL.json"),

    OBJECT_SQL("object_sql.json"),

    SUMMARY("summary.json");

    private String path;

    JsonPathType(String path) {
        this.path = path;
    }

    public String getPath() {
        return PathConst.OUT_DIR + File.separator + path;
    }
}
