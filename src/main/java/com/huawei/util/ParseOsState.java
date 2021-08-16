/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.util;

import com.huawei.jdbc.pojo.SystemDetail;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * Parse the OS state
 */
public class ParseOsState {
    private static final Map<String, BiConsumer<SystemDetail, String>> PARSE_MAP = new ConcurrentHashMap<>();
    private static final Long GB_SIZE = 1024L * 1024 * 1024;

    private static final BiConsumer<SystemDetail, String> parseVisualMem = (systemDetail, value) -> {
        final long valueInGb = Long.parseLong(value) / GB_SIZE;
        systemDetail.setVisualMem(valueInGb + "GB");
    };

    private static final BiConsumer<SystemDetail, String> parsePhysicalMem = (systemDetail, value) -> {
        final long valueInGb = Long.parseLong(value) / GB_SIZE;
        systemDetail.setPhysicalMem(valueInGb + "GB");
    };

    static {
        PARSE_MAP.put("NUM_CPUS", SystemDetail::setNumOfCpus);
        PARSE_MAP.put("NUM_CPU_CORES", SystemDetail::setNumOfCpuCores);
        PARSE_MAP.put("PHYSICAL_MEMORY_BYTES", parsePhysicalMem);
        PARSE_MAP.put("INACTIVE_MEMORY_BYTES", parseVisualMem);
        PARSE_MAP.put("LOAD", SystemDetail::setLoad);
        PARSE_MAP.put("IDLE_TIME", SystemDetail::setIdleTime);
        PARSE_MAP.put("BUSY_TIME", SystemDetail::setBusyTime);
    }

    public static Optional<BiConsumer<SystemDetail, String>> call(String key) {
        return Optional.ofNullable(key).map(PARSE_MAP::get);
    }
}