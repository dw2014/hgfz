package com.dw.hgfz.future;

import com.dw.hgfz.common.httpclient.ApacheCommonClient;
import com.dw.hgfz.common.utils.ConfigHelper;
import com.dw.hgfz.common.utils.GsonHelper;
import com.dw.hgfz.core.base.calculator;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeUnit;
import com.dw.hgfz.future.product.contracts;
import com.google.gson.JsonArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class sample {

    @Test
    public void sampleCalculate() throws Exception {
        String contract = "AU1512";
        JsonArray results = ApacheCommonClient.executeGet(ConfigHelper.getSetting("futureDataURI") + contract);
        List<TradeUnit> tradeUnits = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            TradeUnit tmp = new TradeUnit();
            tmp.setDate(results.get(i).getAsJsonArray().get(0).getAsString());
            tmp.setOpen(results.get(i).getAsJsonArray().get(1).getAsDouble());
            tmp.setHigh(results.get(i).getAsJsonArray().get(2).getAsDouble());
            tmp.setLow(results.get(i).getAsJsonArray().get(3).getAsDouble());
            tmp.setClose(results.get(i).getAsJsonArray().get(4).getAsDouble());
            tmp.setVolume(results.get(i).getAsJsonArray().get(5).getAsLong());
            tradeUnits.add(i, tmp);
        }
        tradeUnits = calculator.sort(tradeUnits, null);
        tradeUnits = calculator.calculateTR(tradeUnits);
        tradeUnits = calculator.calculateATR(tradeUnits);
        tradeUnits = calculator.sort(tradeUnits, "desc");
        System.out.println(GsonHelper.gsonSerializer(tradeUnits.get(0)));
        List<Rule> rules = calculator.calculateRule(tradeUnits, contract, "long");
        System.out.println(GsonHelper.gsonSerializer(rules));
        rules = calculator.calculateRule(tradeUnits, contract, "short");
        System.out.println(GsonHelper.gsonSerializer(rules));

        System.out.println(contracts.auContract().getContractProduct().getBytes());
    }
}
