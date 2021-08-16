/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

import java.util.Map;

/**
 * A Key-value pair
 * @param <T> key
 * @param <R> value
 */
public class Pair<T, R> implements Map.Entry<T, R> {
    private T key;

    private R value;

    public Pair(T key, R value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public R getValue() {
        return value;
    }

    @Override
    public R setValue(R r) {
        this.value = r;
        return this.value;
    }
}
