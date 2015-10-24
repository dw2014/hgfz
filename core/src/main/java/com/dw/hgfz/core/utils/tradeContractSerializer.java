package com.dw.hgfz.core.utils;

import com.dw.hgfz.core.spec.TradeContract;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by dw on 10/24/2015.
 */
public class tradeContractSerializer implements JsonSerializer<TradeContract> {

    @Override
    public JsonElement serialize(TradeContract tradeContract, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("����", context.serialize(tradeContract.getDate()));
        object.add("��Ʒ����", context.serialize(tradeContract.getProductName()));
        object.add("��Լ�۸�/��", context.serialize(tradeContract.getPrice()));
        object.add("��֤��۸�/��", context.serialize(tradeContract.getMarginPrice()));
        object.add("�۸�/���׵�λ", context.serialize(tradeContract.getPricePerUnit()));
        object.add("���׵�λ/��", context.serialize(tradeContract.getUnitsPerContract()));
        object.add("�۸�/��С�䶯��λ", context.serialize(tradeContract.getPricePerMinPriceFluctuation()));
        return object;
    }

    public static String toJson(TradeContract tradeContract) {
        Gson gson = new GsonBuilder().registerTypeAdapter(TradeContract.class, new tradeContractSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(tradeContract);
    }
}
