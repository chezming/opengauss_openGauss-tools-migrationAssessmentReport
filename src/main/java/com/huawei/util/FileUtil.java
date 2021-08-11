package com.huawei.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class FileUtil {
    public static Optional<String> WriteFile(String filePath, String content) {
        try (final FileWriter fileWriter = new FileWriter(new File(filePath))) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.of(e.getMessage());
        }
        return Optional.empty();

    }
}
