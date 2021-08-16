/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Object types in current database
 */
public enum ObjectType {
    FUNCTION("FUNCTION"),
    INDEX("INDEX"),
    PACKAGE("PACKAGE"),
    PACKAGE_BODY("PACKAGE BODY"),
    SEQUENCE("SEQUENCE"),
    PROCEDURE("PROCEDURE"),
    TABLE("TABLE"),
    TRIGGER("TRIGGER"),
    TYPE("TYPE"),
    TYPE_BODY("TYPE BODY"),
    VIEW("VIEW"),
    OTHER(""),
    TOTAL("");

    ObjectType(String outerTagName) {
        this.outerTagName = outerTagName;
    }

    public String getOuterTagName() {
        return outerTagName;
    }

    private final String outerTagName;

    private static final Map<String, ObjectType> map;

    static {
        map = Arrays.stream(ObjectType.values())
                .limit(ObjectType.values().length - 2)
                .collect(Collectors.toMap(ObjectType::getOuterTagName, Function.identity()));
    }

    public static ObjectType getObjectType(String name) {
        return Optional.ofNullable(map.get(name)).orElse(OTHER);
    }

    public String getName() {
        return this.name();
    }
}
