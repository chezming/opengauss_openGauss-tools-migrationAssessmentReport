package com.huawei.jdbc.pojo;

import java.io.File;

import com.huawei.jdbc.ObjectType;

import static java.io.File.separator;

public class PathConst {
    public static final String PROJECT_DIR = new File("").getAbsolutePath();
    public static final String OUT_DIR = PROJECT_DIR + separator + "out";
    public static final String DDL_DIR = OUT_DIR + separator + "schema";
    public static final String BINARY_DIR = PROJECT_DIR + separator + "bin";
    public static final String OBJECT_DETAILS = OUT_DIR + separator + "detail" +separator + "%s_DETAIL.json" ;

}
