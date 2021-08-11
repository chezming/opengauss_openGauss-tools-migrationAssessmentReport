package com.huawei.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class JsonUtil {
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
            e.printStackTrace();
        }
    }

    public static <T> Optional<List<T>> jsonLoadList(String path, Class<T> objectClass) {
        try (FileReader reader = new FileReader(path)) {
            return Optional.of(jsonReaderReadList(reader, objectClass));
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("io exception");
            e.printStackTrace();
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
                e.printStackTrace();
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
            System.out.println("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("io exception");
            e.printStackTrace();
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
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return jsonLoadMap(path, objectClass);
    }
}