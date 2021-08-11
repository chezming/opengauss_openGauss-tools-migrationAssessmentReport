package com.huawei.jdbc.pojo;

public class OSState {
    private String OSSTAT_ID;

    private String STAT_NAME;

    private String COMMENTS;

    private String CUMULATIVE;

    private String CON_ID;

    private String VALUE;

    public String getOSSTAT_ID() {
        return OSSTAT_ID;
    }

    public OSState setOSSTAT_ID(String OSSTAT_ID) {
        this.OSSTAT_ID = OSSTAT_ID;
        return this;
    }

    public String getSTAT_NAME() {
        return STAT_NAME;
    }

    public OSState setSTAT_NAME(String STAT_NAME) {
        this.STAT_NAME = STAT_NAME;
        return this;
    }

    public String getCOMMENTS() {
        return COMMENTS;
    }

    public OSState setCOMMENTS(String COMMENTS) {
        this.COMMENTS = COMMENTS;
        return this;
    }

    public String getCUMULATIVE() {
        return CUMULATIVE;
    }

    public OSState setCUMULATIVE(String CUMULATIVE) {
        this.CUMULATIVE = CUMULATIVE;
        return this;
    }

    public String getCON_ID() {
        return CON_ID;
    }

    public OSState setCON_ID(String CON_ID) {
        this.CON_ID = CON_ID;
        return this;
    }

    public String getVALUE() {
        return VALUE;
    }

    public OSState setVALUE(String VALUE) {
        this.VALUE = VALUE;
        return this;
    }
}
