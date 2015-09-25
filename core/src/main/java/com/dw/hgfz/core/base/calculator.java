package com.dw.hgfz.core.base;


import com.dw.hgfz.common.utils.CommonHelper;
import com.dw.hgfz.common.utils.GsonHelper;
import com.dw.hgfz.core.spec.Order;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeUnit;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class calculator {

    private calculator() {

    }

    private static final String Day20Breaks = "20日突破";
    private static final String Day55Breaks = "55日突破";

    public static List<TradeUnit> sort(List<TradeUnit> tradeUnits, String sort) {
        sortList<TradeUnit> sortList = new sortList<>();
        sortList.sort(tradeUnits, "getDate", sort);
        return tradeUnits;
    }

    public static List<TradeUnit> calculateTR(List<TradeUnit> tradeUnits) {
        assert tradeUnits.size() > 1;
        for (int i = 0; i < tradeUnits.size(); i++) {
            if (i == 0) continue;
            TradeUnit tmpUnit = tradeUnits.get(i);
            tmpUnit.setTr(Math.max(tmpUnit.getHigh() - tmpUnit.getLow(),
                    Math.max(tmpUnit.getHigh() - tradeUnits.get(i - 1).getClose(),
                            tradeUnits.get(i - 1).getClose() - tmpUnit.getLow())));
            tradeUnits.set(i, tmpUnit);
        }
        return tradeUnits;
    }

    public static List<TradeUnit> calculateATR(List<TradeUnit> tradeUnits) {
        assert tradeUnits.size() > 20;
        double tmpATR = 0;
        TradeUnit tmpUnit = tradeUnits.get(20);
        for (int i = 0; i < 20; i++) {
            tmpATR += tradeUnits.get(i).getTr();
        }
        tmpUnit.setAtr(tmpATR / 20);
        tradeUnits.set(20, tmpUnit);
        for (int i = 20; i < tradeUnits.size(); i++) {
            if (i == 20) continue;
            TradeUnit unit = tradeUnits.get(i);
            unit.setAtr((19 * tradeUnits.get(i - 1).getAtr() + unit.getTr()) / 20);
            tradeUnits.set(i, unit);
        }
        return tradeUnits;
    }

    public static List<Order> calculateOrders(double openOrder, double latestATR, boolean isLong) {
        List<Order> orders = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            Order order = new Order();
            order.setOrder(openOrder);
            order.setStopOrder(openOrder + 2 * latestATR * (isLong ? -1 : 1));
            orders.add(j, order);
            openOrder += latestATR / 2 * (isLong ? 1 : -1);
        }
        return orders;
    }

    // with this logic background, tradeUnits list needs in desc order
    public static double calculatePeakValue(List<TradeUnit> tradeUnits, int days, boolean calculateHigh) {
        assert tradeUnits.size() >= days;
        double peakValue = 0.0;
        for (int i = 0; i < days; i++) {
            if (calculateHigh) {
                if (i == 0) {
                    peakValue = tradeUnits.get(i).getHigh();
                    continue;
                }
                peakValue = Math.max(peakValue, tradeUnits.get(i).getHigh());
            } else {
                if (i == 0) {
                    peakValue = tradeUnits.get(i).getLow();
                    continue;
                }
                peakValue = Math.min(peakValue, tradeUnits.get(i).getLow());
            }
        }
        return peakValue;
    }

    public static List<Rule> generateRuleResult(List<TradeUnit> tradeUnits, String market) {
        double highPeak20, highPeak55, lowPeak20, lowPeak55;
        List<Rule> rules = new ArrayList<>();
        if (tradeUnits.size() > 20) {
            highPeak20 = calculatePeakValue(tradeUnits, 20, true);
            Rule rule20long = new Rule();
            rule20long.setSystem(Day20Breaks);
            rule20long.setMarket(market);
            rule20long.setPosition(0);//to be implemented
            rule20long.setIsLong(true);
            rule20long.setOrders(calculateOrders(highPeak20, tradeUnits.get(0).getAtr(), true));
            rule20long.setQuitOrder(calculatePeakValue(tradeUnits, 10, false));
            rules.add(rule20long);

            lowPeak20 = calculatePeakValue(tradeUnits, 20, false);
            Rule rule20short = new Rule();
            rule20short.setSystem(Day20Breaks);
            rule20short.setMarket(market);
            rule20short.setPosition(0);//to be implemented
            rule20short.setIsLong(false);
            rule20short.setOrders(calculateOrders(lowPeak20, tradeUnits.get(0).getAtr(), false));
            rule20short.setQuitOrder(calculatePeakValue(tradeUnits, 10, true));
            rules.add(rule20short);
        }
        if (tradeUnits.size() > 55) {
            highPeak55 = calculatePeakValue(tradeUnits, 55, true);
            Rule rule55long = new Rule();
            rule55long.setSystem(Day55Breaks);
            rule55long.setMarket(market);
            rule55long.setPosition(0);//to be implemented
            rule55long.setIsLong(true);
            rule55long.setOrders(calculateOrders(highPeak55, tradeUnits.get(0).getAtr(), true));
            rule55long.setQuitOrder(calculatePeakValue(tradeUnits, 20, false));
            rules.add(rule55long);

            lowPeak55 = calculatePeakValue(tradeUnits, 55, false);
            Rule rule55short = new Rule();
            rule55short.setSystem(Day55Breaks);
            rule55short.setMarket(market);
            rule55short.setPosition(0);//to be implemented
            rule55short.setIsLong(false);
            rule55short.setOrders(calculateOrders(lowPeak55, tradeUnits.get(0).getAtr(), false));
            rule55short.setQuitOrder(calculatePeakValue(tradeUnits, 20, true));
            rules.add(rule55short);
        }
        return rules;
    }

    public static void process(JsonArray results, String market, String path) throws Exception {
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
        tradeUnits = sort(tradeUnits, null);
        tradeUnits = calculateTR(tradeUnits);
        tradeUnits = calculateATR(tradeUnits);
        tradeUnits = sort(tradeUnits, "desc");
        List<Rule> rules = generateRuleResult(tradeUnits, market);
        System.out.println(market + "最近一交易日数据");
        System.out.println(GsonHelper.gsonSerializer(tradeUnits.get(0)));
        CommonHelper.printList(rules, market + "海龟法则计算结果");
        writeFile.writeToFile(tradeUnits.get(0), rules, market, path);
    }
}
