package com.dw.hgfz.core.utils;

import com.dw.hgfz.core.spec.Rule;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by dw on 10/24/2015.
 */
public class ruleSerializer implements JsonSerializer<Rule> {

    @Override
    public JsonElement serialize(Rule rule, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("���״���", context.serialize(rule.getTradeCode()));
        object.add("����ϵͳ", context.serialize(rule.getSystem()));
        object.add("�г�����", context.serialize(rule.getMarketQuotation()));
        object.add("���м�λ", context.serialize(rule.getOrders()));
        object.add("�˳���λ", context.serialize(rule.getQuitOrder()));
        object.add("ͷ���ģ", context.serialize(rule.getPosition()));
        return object;
    }

    public static String toJson(Rule rule) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Rule.class, new ruleSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(rule);
    }
}
