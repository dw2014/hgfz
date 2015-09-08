package com.dw.hgfz.core.base;


import com.dw.hgfz.core.spec.Order;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class calculator {

    private calculator() {

    }

    private static final String Day20Breaks = "Day 20 Breaks";
    private static final String Day55Breaks = "Day 55 Breaks";

    public static List<TradeUnit> sort(List<TradeUnit> tradeUnits, String sort) {
        sortTradeUnits<TradeUnit> sortList = new sortTradeUnits<>();
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

    public static List<Rule> calculateRule(List<TradeUnit> tradeUnits, String market, String method) {
        assert method != null;
        assert tradeUnits.size() > 20;
        List<Rule> results = new ArrayList<>();
        if (method.equals("long")) {
            double breakPoint = 0;
            for (int i = 0; i < 20; i++) {
                if (i == 0) {
                    breakPoint = tradeUnits.get(i).getHigh();
                    continue;
                }
                breakPoint = Math.max(breakPoint, tradeUnits.get(i).getHigh());
            }
            double openOrder = breakPoint;
            Rule day20Rule = new Rule();
            day20Rule.setSystem(Day20Breaks);
            day20Rule.setMarket(market);
            List<Order> day20Orders = new ArrayList<>();
            double latestATR = tradeUnits.get(0).getAtr();
            for (int j = 0; j < 4; j++) {
                Order order = new Order();
                order.setOrder(openOrder);
                order.setStopOrder(openOrder - 2 * latestATR);
                day20Orders.add(j, order);
                openOrder += latestATR / 2;
            }
            day20Rule.setOrders(day20Orders);
            results.add(day20Rule);

            if (tradeUnits.size() < 55) return results;

            breakPoint = 0;
            for (int i = 0; i < 55; i++) {
                if (i == 0) {
                    breakPoint = tradeUnits.get(i).getHigh();
                    continue;
                }
                breakPoint = Math.max(breakPoint, tradeUnits.get(i).getHigh());
            }
            openOrder = breakPoint;
            Rule day55Rule = new Rule();
            day55Rule.setSystem(Day55Breaks);
            day55Rule.setMarket(market);
            List<Order> day55Orders = new ArrayList<>();
            latestATR = tradeUnits.get(0).getAtr();
            for (int j = 0; j < 4; j++) {
                Order order = new Order();
                order.setOrder(openOrder);
                order.setStopOrder(openOrder - 2 * latestATR);
                day55Orders.add(j, order);
                openOrder += latestATR / 2;
            }
            day55Rule.setOrders(day55Orders);
            results.add(day55Rule);
            return results;
        } else if (method.equals("short")) {
            double breakPoint = 0;
            for (int i = 0; i < 20; i++) {
                if (i == 0) {
                    breakPoint = tradeUnits.get(i).getLow();
                    continue;
                }
                breakPoint = Math.min(breakPoint, tradeUnits.get(i).getLow());
            }
            double openOrder = breakPoint;
            Rule day20Rule = new Rule();
            day20Rule.setSystem(Day20Breaks);
            day20Rule.setMarket(market);
            List<Order> day20Orders = new ArrayList<>();
            double latestATR = tradeUnits.get(0).getAtr();
            for (int j = 0; j < 4; j++) {
                Order order = new Order();
                order.setOrder(openOrder);
                order.setStopOrder(openOrder + 2 * latestATR);
                day20Orders.add(j, order);
                openOrder -= latestATR / 2;
            }
            day20Rule.setOrders(day20Orders);
            results.add(day20Rule);

            if (tradeUnits.size() < 55) return results;

            breakPoint = 0;
            for (int i = 0; i < 55; i++) {
                if (i == 0) {
                    breakPoint = tradeUnits.get(i).getLow();
                    continue;
                }
                breakPoint = Math.min(breakPoint, tradeUnits.get(i).getLow());
            }
            openOrder = breakPoint;
            Rule day55Rule = new Rule();
            day55Rule.setSystem(Day55Breaks);
            day55Rule.setMarket(market);
            List<Order> day55Orders = new ArrayList<>();
            latestATR = tradeUnits.get(0).getAtr();
            for (int j = 0; j < 4; j++) {
                Order order = new Order();
                order.setOrder(openOrder);
                order.setStopOrder(openOrder + 2 * latestATR);
                day55Orders.add(j, order);
                openOrder -= latestATR / 2;
            }
            day55Rule.setOrders(day55Orders);
            results.add(day55Rule);
            return results;
        }
        return null;
    }
}
