package com.dw.hgfz.core.base;

import com.dw.hgfz.common.utils.GsonHelper;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeContract;
import com.dw.hgfz.core.spec.TradeProduct;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dw on 9/25/2015.
 */
public class writeFile {

    private writeFile() {

    }

    public static void writeToFile(
            TradeContract tradeContract, TradeProduct latestUnit, List<Rule> rules,
            String market, String path) throws Exception {
        path = path + "\\" + (market.length() < 7 ? "future" : "stock") +
                new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis())) + ".txt ";
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
        if (market.length() < 7) {
            writer.println(market + "最新合约交易数据");
            writer.println(GsonHelper.gsonSerializer(tradeContract));
        }
        writer.println(market + "最近一交易日数据");
        writer.println(GsonHelper.gsonSerializer(latestUnit));
        writer.println(market + "海龟法则计算结果");
        for (int i = 0; i < rules.size(); i++) {
            if (market.length() > 6 && i % 2 != 0) continue;
            writer.println(GsonHelper.gsonSerializer(rules.get(i)));
        }
        writer.println();
        writer.flush();
        writer.close();
    }
}
