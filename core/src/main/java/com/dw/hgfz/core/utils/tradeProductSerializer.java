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
        object.add("����", context.serialize(tradeProduct.getDate()));
        object.add("����", context.serialize(tradeProduct.getOpen()));
        object.add("���", context.serialize(tradeProduct.getHigh()));
        object.add("���", context.serialize(tradeProduct.getLow()));
        object.add("����", context.serialize(tradeProduct.getClose()));
        object.add("�ɽ�", context.serialize(tradeProduct.getVolume()));
        object.add("��ʵ��������", context.serialize(tradeProduct.getTr()));
        object.add("��ʵ��������20�վ�ֵ", context.serialize(tradeProduct.getAtr()));
        return object;
    }

    public static String toJson(TradeProduct tradeProduct) {
        Gson gson = new GsonBuilder().registerTypeAdapter(TradeProduct.class, new tradeProductSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(tradeProduct);
    }
}
