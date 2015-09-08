package com.dw.hgfz.common.utils;

import com.google.gson.Gson;

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
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
