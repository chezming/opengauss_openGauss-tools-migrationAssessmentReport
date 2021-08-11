package com.huawei.jdbc.pojo;

public class DataFiles {
    private String fileName;

    private String tablespaceName;

    private String bytes;

    public String getFileName() {
        return fileName;
    }

    public DataFiles setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getTablespaceName() {
        return tablespaceName;
    }

    public DataFiles setTablespaceName(String tablespaceName) {
        this.tablespaceName = tablespaceName;
        return this;
    }

    public String getBytes() {
        return bytes;
    }

    public DataFiles setBytes(String bytes) {
        this.bytes = bytes;
        return this;
    }
}
