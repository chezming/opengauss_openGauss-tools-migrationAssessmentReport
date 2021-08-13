package com.huawei.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static Optional<String> WriteFile(String filePath, String content) {
        try (final FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.of(e.getMessage());
        }
        return Optional.empty();

    }
}
