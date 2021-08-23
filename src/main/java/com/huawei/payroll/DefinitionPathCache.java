/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.payroll;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The original DDL statement
 */
public class DefinitionPathCache {
    public static final DefinitionPathCache INSTANCE = new DefinitionPathCache();

    private final Map<String, String> map = new HashMap<>();

    private DefinitionPathCache() {
    }

    public void putDDLPath(String id, String path) {
        this.map.put(id, path);
    }

    public Optional<String> getDDLPath(String id) {
        return Optional.ofNullable(this.map.get(id));
    }
}
