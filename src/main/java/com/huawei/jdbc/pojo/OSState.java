/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

/**
 * Operating system state.
 */
public class OSState {
    private String statId;

    private String statName;

    private String comments;

    private String cumulative;

    private String conId;

    private String value;

    public String getStatId() {
        return statId;
    }

    public OSState setStatId(String statId) {
        this.statId = statId;
        return this;
    }

    public String getStatName() {
        return statName;
    }

    public OSState setStatName(String statName) {
        this.statName = statName;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public OSState setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getCumulative() {
        return cumulative;
    }

    public OSState setCumulative(String cumulative) {
        this.cumulative = cumulative;
        return this;
    }

    public String getConId() {
        return conId;
    }

    public OSState setConId(String conId) {
        this.conId = conId;
        return this;
    }

    public String getValue() {
        return value;
    }

    public OSState setValue(String value) {
        this.value = value;
        return this;
    }
}
