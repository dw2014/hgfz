package com.dw.hgfz.core.base;

import com.dw.hgfz.common.utils.GsonHelper;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeContract;
import com.dw.hgfz.core.spec.TradeProduct;

import java.util.List;

/**
 * Created by dw on 10/21/2015.
 */
public class writeMessage {

    private writeMessage() {

    }

    public static String writeDisplayMessage(
            TradeContract tradeContract, TradeProduct latestUnit, List<Rule> rules, String market)
            throws Exception {
        StringBuilder builder = new StringBuilder();
        if (market.length() < 7) {
            builder.append(market + " latest_contract_data").append("\n");
            builder.append(GsonHelper.gsonSerializer(tradeContract)).append("\n");
        }
        builder.append(market + " latest_trade_data").append("\n");
        builder.append(GsonHelper.gsonSerializer(latestUnit)).append("\n");
        builder.append(market + " rule_calculation_result").append("\n");
        for (int i = 0; i < rules.size(); i++) {
            if (market.length() > 6 && i % 2 != 0) continue;
            builder.append(GsonHelper.gsonSerializer(rules.get(i))).append("\n");
        }
        return builder.toString();
    }
}
