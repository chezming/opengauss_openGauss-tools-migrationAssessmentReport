package com.huawei.util;

import com.huawei.jdbc.pojo.SystemDetail;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ParseOsState {
    private static final Map<String, BiConsumer<SystemDetail, String>> parseMap = new ConcurrentHashMap<>();

    private static final BiConsumer<SystemDetail, String> parseVisualMem = (systemDetail, value) -> {
        final long valueInGb = Long.parseLong(value) / 1024 / 1024 / 1024;
        systemDetail.setVisualMem(valueInGb + "GB");
    };

    private static final BiConsumer<SystemDetail, String> parsePhysicalMem = (systemDetail, value) -> {
        final long valueInGb = Long.parseLong(value) / 1024 / 1024 / 1024;
        systemDetail.setPhysicalMem(valueInGb + "GB");
    };

    static {
        parseMap.put("NUM_CPUS", SystemDetail::setNumOfCpus);
        parseMap.put("NUM_CPU_CORES", SystemDetail::setNumOfCpuCores);
        parseMap.put("PHYSICAL_MEMORY_BYTES", parsePhysicalMem);
        parseMap.put("INACTIVE_MEMORY_BYTES", parseVisualMem);
        parseMap.put("LOAD", SystemDetail::setLoad);
        parseMap.put("IDLE_TIME", SystemDetail::setIdleTime);
        parseMap.put("BUSY_TIME", SystemDetail::setBusyTime);
    }

    public static Optional<BiConsumer<SystemDetail, String>> call(String key) {
        return Optional.ofNullable(key).map(parseMap::get);
    }
}