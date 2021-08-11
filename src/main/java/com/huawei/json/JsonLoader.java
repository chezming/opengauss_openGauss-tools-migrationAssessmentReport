package com.huawei.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonLoader<T> {
    private static Gson gson = new GsonBuilder().create();

    List<T> objects = new ArrayList<>();

    public JsonLoader(String path, Class<T> objectClass) {
        System.out.println(new File("").getAbsoluteFile());
        try (FileReader reader = new FileReader(path)) {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                T object = gson.fromJson(reader, objectClass);
                objects.add(object);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("io exception");
            e.printStackTrace();
        }
    }
}
