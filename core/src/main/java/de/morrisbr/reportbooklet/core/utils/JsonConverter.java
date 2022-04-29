package de.morrisbr.reportbooklet.core.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonConverter {

    public static Object jsonStringToObject(String jsonString, Class object) {
        return new Gson().fromJson(jsonString, object);
    }

    public static Object jsonFileToObject(String path, Class object) {
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(reader, object);
    }

    public static String readJSONFromFile(String filePath) {
        Gson gson = new Gson();
        Object json = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            JsonReader reader = new JsonReader(fileReader);
            json = gson.fromJson(fileReader, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static void objectToJsonFile(String path, Object object) {
        Gson gson = new Gson();
        String json = objectToJsonString(object);
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String objectToJsonString(Object object) {
        return new Gson().toJson(object);
    }
}
