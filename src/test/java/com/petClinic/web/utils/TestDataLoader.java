package com.petClinic.web.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class TestDataLoader {
    public static Map<String, String> getOwnerData() {
        try (FileReader reader = new FileReader("src/test/resources/data/ownerdata.json")) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
