package com.lib.sampleimagelist.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by souvik on 7/11/2016.
 */
public final class JSONParser {

    private static Gson mGson;

    private static synchronized void init() {
        if (mGson == null) {
            synchronized (JSONParser.class) {
                if (mGson == null) {
                    GsonBuilder builder = new GsonBuilder();
                    mGson = builder.enableComplexMapKeySerialization()
                            .create();
                }
            }
        }
    }

    public static <T> T parse(String jsonString, Class<T> clazz) {
        init();
        jsonString = jsonString.trim();
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        reader.setLenient(true);
        return mGson.fromJson(reader, clazz);
    }

}
