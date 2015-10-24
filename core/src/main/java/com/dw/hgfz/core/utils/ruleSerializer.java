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
        object.add("交易代码", context.serialize(rule.getTradeCode()));
        object.add("入市系统", context.serialize(rule.getSystem()));
        object.add("市场行情", context.serialize(rule.getMarketQuotation()));
        object.add("入市价位", context.serialize(rule.getOrders()));
        object.add("退出价位", context.serialize(rule.getQuitOrder()));
        object.add("头寸规模", context.serialize(rule.getPosition()));
        return object;
    }

    public static String toJson(Rule rule) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Rule.class, new ruleSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(rule);
    }
}
