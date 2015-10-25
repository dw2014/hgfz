package com.dw.hgfz.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by dw on 9/7/2015.
 */
public class GsonHelper {

    private GsonHelper() {

    }

    public static <T> T gsonDeserializer(String jsonString, Class<T> cls) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, cls);
    }

    public static String gsonSerializer(Object obj) throws Exception {
        return gsonSerializer(obj, true);
    }

    public static String gsonSerializer(Object obj, boolean prettyPrint) throws Exception {
        if (prettyPrint) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(obj);
        } else {
            Gson gson = new Gson();
            return gson.toJson(obj);
        }
    }

}
