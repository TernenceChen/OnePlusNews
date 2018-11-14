package com.example.oneplus.opnew.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class JsonUtils {

    private static Gson mGson = new Gson();

    public static <T> String serialize(T object){
        return mGson.toJson(object);
    }

    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException{
        return mGson.fromJson(json, clz);
    }

    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException{
        return mGson.fromJson(json,clz);
    }

    public static <T> T deserialize (String json, Type type) throws JsonSyntaxException{
        return mGson.fromJson(json, type);
    }

}
