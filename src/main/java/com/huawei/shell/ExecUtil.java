/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.jdbc.DatabaseType;
import com.huawei.jdbc.pojo.Pair;

import static com.huawei.jdbc.pojo.PathConst.BINARY_DIR;

/**
 * Get information by shell command
 */
public class ExecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecUtil.class);

    private static String SYSTEM_VERSION = null;

    private static String MACHINE_ARCHITECTURE = null;

    private static final char LINE_SEPARATOR = '\n';

    public static String getMachineArchitecture() {
        String systemVersion = getSystemVersion();
        if (systemVersion.toLowerCase().startsWith("windows")) {
            throw new RuntimeException(String.format("unsupported system: %s", systemVersion));
        }
        if (MACHINE_ARCHITECTURE == null) {
            synchronized (ExecUtil.class) {
                if (MACHINE_ARCHITECTURE == null) {
                    try {
                        // check OS type
                        Process arch = Runtime.getRuntime().exec("arch");
                        try (BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(arch.getInputStream()))) {
                            String machineArchitecture = bufferedReader.readLine();
                            if (machineArchitecture.equalsIgnoreCase("aarch64") || machineArchitecture.equalsIgnoreCase(
                                    "x86_64")) {
                                MACHINE_ARCHITECTURE = machineArchitecture;
                            } else {
                                throw new RuntimeException(
                                        String.format("unsupported machine architecture: %s", machineArchitecture));
                            }
                        }
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }
        return MACHINE_ARCHITECTURE;
    }

    private static String sqlValidateCommand(DatabaseType databaseType) {
        String binName = String.format("%s_%s", databaseType.getStr(), getSystemVersion().toLowerCase());
        return BINARY_DIR + File.separator + getMachineArchitecture() + File.separator + binName;
    }

    private static String sqlConvertCommand(DatabaseType databaseType) {
        String binName = String.format("%s_%s_converter", databaseType.getStr(), getSystemVersion().toLowerCase());
        return BINARY_DIR + File.separator + getMachineArchitecture() + File.separator + binName;
    }

    public static Map.Entry<Boolean, String> sqlValidate(String sql, DatabaseType databaseType) {
        return sqlCommandExec(() -> sqlValidateCommand(databaseType), databaseType, sql);
    }

    private static Map.Entry<Boolean, String> sqlCommandExec(Supplier<String> commandSupplier,
            DatabaseType databaseType, String sql) {
        String command = commandSupplier.get();
        StringBuilder failedResult = new StringBuilder();
        boolean success;
        try {
            Process exec = Runtime.getRuntime().exec(new String[] {command, sql});
            exec.waitFor();
            success = true;
            String line;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()))) {
                while ((line = bufferedReader.readLine()) != null) {
                    failedResult.append(line).append(LINE_SEPARATOR);
                    success = false;
                }
            }
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream()))) {
                while ((line = bufferedReader.readLine()) != null) {
                    failedResult.append(line).append(LINE_SEPARATOR);
                    success = false;
                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
            success = false;
        }
        if (!success) {
            LOGGER.error("validate failed, databaseType: {}, sql: \"{}\", error reason: \"{}\"", databaseType.getStr(),
                    sql.trim().replace(LINE_SEPARATOR, ' ').replace('\t', ' ').replaceAll(" +", " "),
                    failedResult.toString().trim().replace(LINE_SEPARATOR, ' '));
        }
        return new Pair<>(success, failedResult.toString().trim());
    }

    public static Map.Entry<Boolean, String> sqlConvert(String sql, DatabaseType databaseType) {
        String command = sqlConvertCommand(databaseType);
        StringBuilder failedResult = new StringBuilder();
        boolean success = true;
        try {
            Process exec = Runtime.getRuntime().exec(new String[] {command, sql});
            exec.waitFor();
            int result = exec.exitValue();
            if (result != 0) {
                success = false;
            }
            String line;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()))) {
                while ((line = bufferedReader.readLine()) != null) {
                    failedResult.append(line).append(LINE_SEPARATOR);
                }
            }
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream()))) {
                while ((line = bufferedReader.readLine()) != null) {
                    failedResult.append(line).append(LINE_SEPARATOR);
                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
            success = false;
        }
        if (!success) {
            LOGGER.error("validate failed, databaseType: {}, sql: \"{}\", error reason: \"{}\"", databaseType.getStr(),
                    sql.trim().replace(LINE_SEPARATOR, ' ').replace('\t', ' ').replaceAll(" +", " "),
                    failedResult.toString().trim().replace(LINE_SEPARATOR, ' '));
        }
        return new Pair<>(success, failedResult.toString().trim());
    }

    public static String getSystemVersion() {
        if (SYSTEM_VERSION == null) {
            synchronized (ExecUtil.class) {
                if (SYSTEM_VERSION == null) {
                    SYSTEM_VERSION = System.getProperty("os.name");
                }
            }
        }
        return SYSTEM_VERSION;
    }
}
