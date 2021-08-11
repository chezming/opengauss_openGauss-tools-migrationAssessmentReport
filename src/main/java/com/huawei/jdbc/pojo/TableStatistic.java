package com.huawei.jdbc.pojo;

public class TableStatistic {
    private String type;

    private String schema;

    private String objectName;

    private long numRows;

    public String getType() {
        return type;
    }

    public TableStatistic setType(String type) {
        this.type = type;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public TableStatistic setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public TableStatistic setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public long getNumRows() {
        return numRows;
    }

    public TableStatistic setNumRows(long numRows) {
        this.numRows = numRows;
        return this;
    }
}
