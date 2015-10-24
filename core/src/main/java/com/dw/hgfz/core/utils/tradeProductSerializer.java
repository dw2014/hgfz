package com.dw.hgfz.core.utils;

import com.dw.hgfz.core.spec.TradeProduct;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by dw on 10/24/2015.
 */
public class tradeProductSerializer implements JsonSerializer<TradeProduct> {

    @Override
    public JsonElement serialize(TradeProduct tradeProduct, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("日期", context.serialize(tradeProduct.getDate()));
        object.add("开盘", context.serialize(tradeProduct.getOpen()));
        object.add("最高", context.serialize(tradeProduct.getHigh()));
        object.add("最低", context.serialize(tradeProduct.getLow()));
        object.add("收盘", context.serialize(tradeProduct.getClose()));
        object.add("成交", context.serialize(tradeProduct.getVolume()));
        object.add("真实波动幅度", context.serialize(tradeProduct.getTr()));
        object.add("真实波动幅度20日均值", context.serialize(tradeProduct.getAtr()));
        return object;
    }

    public static String toJson(TradeProduct tradeProduct) {
        Gson gson = new GsonBuilder().registerTypeAdapter(TradeProduct.class, new tradeProductSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(tradeProduct);
    }
}
