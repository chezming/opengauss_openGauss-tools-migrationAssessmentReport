package com.huawei.jdbc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ObjectType {
    FUNCTION("FUNCTION"),
    INDEX("INDEX"),
    PACKAGE("PACKAGE"),
    PACKAGE_BODY("PACKAGE_BODY"),
    SEQUENCE("SEQUENCE"),
    PROCEDURE("PROCEDURE"),
    TABLE("TABLE"),
    TRIGGER("TRIGGER"),
    TYPE("TYPE"),
    TYPE_BODY("TYPE_BODY"),
    VIEW("VIEW"),
    OTHER("OTHER"),
    TOTAL("TOTAL");

    private static Map<String, ObjectType> map = new HashMap<>();

    static {
        map.put("FUNCTION", FUNCTION);
        map.put("INDEX", INDEX);
        map.put("PACKAGE", PACKAGE);
        map.put("PACKAGE BODY", PACKAGE_BODY);
        map.put("SEQUENCE", SEQUENCE);
        map.put("PROCEDURE", PROCEDURE);
        map.put("TABLE", TABLE);
        map.put("TRIGGER", TRIGGER);
        map.put("TYPE", TYPE);
        map.put("TYPE BODY", TYPE_BODY);
        map.put("VIEW", VIEW);
    }

    public static ObjectType getObjectType(String name) {
        return Optional.ofNullable(map.get(name)).orElse(OTHER);
    }
    private String name;

    ObjectType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
