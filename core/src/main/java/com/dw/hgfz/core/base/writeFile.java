package com.dw.hgfz.core.base;

import com.dw.hgfz.common.utils.GsonHelper;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeUnit;

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

    public static void writeToFile(TradeUnit latestUnit, List<Rule> rules, String market, String path) throws Exception {
        path = path + "\\" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt ";
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
        writer.println(market + "最近一交易日数据");
        writer.println(GsonHelper.gsonSerializer(latestUnit));
        writer.println(market + "海龟法则计算结果");
        for (int i = 0; i < rules.size(); i++) {
            writer.println(GsonHelper.gsonSerializer(rules.get(i)));
        }
        writer.println();
        writer.flush();
        writer.close();
    }
}