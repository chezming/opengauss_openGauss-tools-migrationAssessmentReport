/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Some json utility interface
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static <T> List<T> jsonReaderReadList(FileReader reader, Class<T> objectClass) throws IOException {
        List<T> result = new ArrayList<>();
        JsonReader jsonReader = new JsonReader(reader);
        Gson gson = new GsonBuilder().create();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            T object = gson.fromJson(jsonReader, objectClass);
            result.add(object);
        }
        return result;
    }

    private static <T> Map<String, T> jsonReaderReadMap(FileReader reader, Class<T> objectClass) throws IOException {
        Map<String, T> result = new HashMap<>();
        JsonReader jsonReader = new JsonReader(reader);
        Gson gson = new GsonBuilder().create();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            result.put(key, gson.fromJson(jsonReader, objectClass));
        }
        return result;
    }

    public static void jsonSave(Object obj, String path) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(json);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static <T> Optional<List<T>> jsonLoadList(String path, Class<T> objectClass) {
        try (FileReader reader = new FileReader(path)) {
            return Optional.of(jsonReaderReadList(reader, objectClass));
        } catch (FileNotFoundException e) {
            LOGGER.error("file not found :{}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("io exception: {}", e.getMessage());
        }
        return Optional.empty();
    }

    public static <T> Optional<List<T>> jsonLoadList(String path, Class<T> objectClass, Callable<List<T>> callable) {
        if (!new File(path).exists()) {
            try {
                List<T> call = callable.call();
                jsonSave(call, path);
                return Optional.of(call);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return Optional.empty();
            }
        }
        return jsonLoadList(path, objectClass);
    }

    public static <T> Optional<Map<String, T>> jsonLoadMap(String path, Class<T> objectClass) {
        Map<String, T> result = new HashMap<>();
        try (FileReader reader = new FileReader(path)) {
            return Optional.of(jsonReaderReadMap(reader, objectClass));
        } catch (FileNotFoundException e) {
            LOGGER.error("file not found :{}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("io exception: {}", e.getMessage());
        }
        return Optional.empty();
    }

    public static <T> Optional<Map<String, T>> jsonLoadMap(String path, Class<T> objectClass,
            Callable<Map<String, T>> callable) {
        if (!new File(path).exists()) {
            try {
                Map<String, T> call = callable.call();
                jsonSave(call, path);
                return Optional.ofNullable(call);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return Optional.empty();
            }
        }
        return jsonLoadMap(path, objectClass);
    }
}