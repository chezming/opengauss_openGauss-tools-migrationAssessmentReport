package com.huawei.jdbc.pojo;


import com.huawei.jdbc.ObjectType;

public class Compatibility {
    private ObjectType type;

    private int total = 0;

    private int originError = 0;

    private int targetSupport = 0;

    private int extensionSupport = 0;

    private String conversionRate = "0.00%";

    public Compatibility(ObjectType type) {
        this.type = type;
    }


    public ObjectType getType() {
        return type;
    }

    public Compatibility setType(ObjectType type) {
        this.type = type;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public Compatibility setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getOriginError() {
        return originError;
    }

    public Compatibility setOriginError(int originError) {
        this.originError = originError;
        return this;
    }

    public int getTargetSupport() {
        return targetSupport;
    }

    public Compatibility setTargetSupport(int targetSupport) {
        this.targetSupport = targetSupport;
        return this;
    }

    public int getExtensionSupport() {
        return extensionSupport;
    }

    public Compatibility setExtensionSupport(int extensionSupport) {
        this.extensionSupport = extensionSupport;
        return this;
    }

    public String getConversionRate() {
        return conversionRate;
    }

    public Compatibility setConversionRate() {
        final double v = this.extensionSupport / (double) this.total * 100;
        this.conversionRate = this.total == 0 ? "0.00%" : String.format("%.2f", v) + "%";
        return this;
    }
}
