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
        object.add("日期", context.serialize(tradeContract.getDate()));
        object.add("商品名称", context.serialize(tradeContract.getProductName()));
        object.add("合约价格/手", context.serialize(tradeContract.getPrice()));
        object.add("保证金价格/手", context.serialize(tradeContract.getMarginPrice()));
        object.add("价格/交易单位", context.serialize(tradeContract.getPricePerUnit()));
        object.add("交易单位/手", context.serialize(tradeContract.getUnitsPerContract()));
        object.add("价格/最小变动价位", context.serialize(tradeContract.getPricePerMinPriceFluctuation()));
        return object;
    }

    public static String toJson(TradeContract tradeContract) {
        Gson gson = new GsonBuilder().registerTypeAdapter(TradeContract.class, new tradeContractSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(tradeContract);
    }
}
