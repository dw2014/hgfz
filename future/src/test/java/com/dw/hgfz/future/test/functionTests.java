package com.dw.hgfz.future.test;

import com.dw.hgfz.common.utils.CommonHelper;
import com.dw.hgfz.common.utils.RandomHelper;
import com.dw.hgfz.core.base.calculator;
import com.dw.hgfz.core.spec.Order;
import com.dw.hgfz.core.spec.TradeUnit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 9/24/2015.
 */
public class functionTests {

    @Test
    public void testCalculateSort() throws Exception {
        List<TradeUnit> tradeUnits = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TradeUnit tradeUnit = new TradeUnit();
            tradeUnit.setDate("2015-05-" + String.format("%02d", RandomHelper.randomInt(31)));
            tradeUnits.add(i, tradeUnit);
        }
        tradeUnits = calculator.sort(tradeUnits, null);
        for (int i = 0; i < 5; i++) {
            if (i == 4) continue;
            assert Long.parseLong(tradeUnits.get(i).getDate().replace("-", ""))
                    <= Long.parseLong(tradeUnits.get(i + 1).getDate().replace("-", ""));
        }
        CommonHelper.printList(tradeUnits, "asc order");
        tradeUnits = calculator.sort(tradeUnits, "desc");
        for (int i = 0; i < 5; i++) {
            if (i == 4) continue;
            assert Long.parseLong(tradeUnits.get(i).getDate().replace("-", ""))
                    >= Long.parseLong(tradeUnits.get(i + 1).getDate().replace("-", ""));
        }
        CommonHelper.printList(tradeUnits, "desc order");
    }

    @Test
    public void testCalculateTR() throws Exception {
        List<TradeUnit> tradeUnits = new ArrayList<>();
        TradeUnit yesterday = new TradeUnit();
        yesterday.setClose(Math.round(RandomHelper.randomDouble() * 100));
        tradeUnits.add(0, yesterday);
        TradeUnit today = new TradeUnit();
        today.setHigh(Math.round(RandomHelper.randomDouble() * 100));
        today.setLow(Math.round(RandomHelper.randomDouble() * 100));
        tradeUnits.add(1, today);
        tradeUnits = calculator.calculateTR(tradeUnits);
        assert tradeUnits.get(1).getTr() == Math.max(today.getHigh() - today.getLow(),
                Math.max(today.getHigh() - yesterday.getClose(), yesterday.getClose() - today.getLow()));
        CommonHelper.printList(tradeUnits, null);
    }

    @Test
    public void testCalculateATR() throws Exception {
        List<TradeUnit> tradeUnits = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            TradeUnit tradeUnit = new TradeUnit();
            tradeUnit.setHigh(Math.round(RandomHelper.randomDouble() * 100));
            tradeUnit.setLow(Math.round(RandomHelper.randomDouble() * 100));
            tradeUnit.setClose(Math.round(RandomHelper.randomDouble() * 100));
            tradeUnits.add(i, tradeUnit);
        }
        tradeUnits = calculator.calculateTR(tradeUnits);
        tradeUnits = calculator.calculateATR(tradeUnits);
        for (int i = 0; i < 20; i++) {
            assert tradeUnits.get(i).getAtr() == 0.0;
        }
        double first20TRSum = 0.0;
        for (int i = 0; i < 20; i++) {
            first20TRSum += tradeUnits.get(i).getTr();
        }
        assert tradeUnits.get(20).getAtr() == first20TRSum / 20;
        for (int i = 21; i < tradeUnits.size(); i++) {
            assert tradeUnits.get(i).getAtr() == (19 * tradeUnits.get(i - 1).getAtr() + tradeUnits.get(i).getTr()) / 20;
        }
        CommonHelper.printList(tradeUnits, "TR and ATR are calculated and validated.");
    }

    @Test
    public void testCalculateOrder() throws Exception {
        double openOrder = Math.round(RandomHelper.randomDouble() * 1000) % 1000;
        double latestATR = Math.round(RandomHelper.randomDouble() * 1000) % 100;
        System.out.println("openOrder: " + openOrder + "|latestATR: " + latestATR);

        List<Order> orders = calculator.calculateOrders(openOrder, latestATR, true);
        double tmpOpenOrder = openOrder;
        for (int i = 0; i < orders.size(); i++) {
            assert orders.get(i).getOrder() == tmpOpenOrder;
            assert orders.get(i).getStopOrder() == orders.get(i).getOrder() - latestATR * 2;
            tmpOpenOrder += latestATR / 2;
        }
        CommonHelper.printList(orders, "long order list");
        orders = calculator.calculateOrders(openOrder, latestATR, false);
        tmpOpenOrder = openOrder;
        for (int i = 0; i < orders.size(); i++) {
            assert orders.get(i).getOrder() == tmpOpenOrder;
            assert orders.get(i).getStopOrder() == orders.get(i).getOrder() + latestATR * 2;
            tmpOpenOrder -= latestATR / 2;
        }
        CommonHelper.printList(orders, "short order list");
    }

    @Test
    public void testCalculatePeakValue() throws Exception {
        List<TradeUnit> tradeUnits = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TradeUnit tradeUnit = new TradeUnit();
            tradeUnit.setHigh(Math.round(RandomHelper.randomDouble() * 1000) % 1000);
            tradeUnit.setLow(Math.round(RandomHelper.randomDouble() * 1000) % 1000);
            tradeUnits.add(i, tradeUnit);
        }
        CommonHelper.printList(tradeUnits, null);

        int days = RandomHelper.randomInt(tradeUnits.size());
        double highPeak = 0.0;
        for (int i = 0; i < days; i++) {
            if (i == 0) {
                highPeak = tradeUnits.get(i).getHigh();
                continue;
            }
            highPeak = Math.max(highPeak, tradeUnits.get(i).getHigh());
        }
        assert highPeak == calculator.calculatePeakValue(tradeUnits, days, true);
        System.out.println("high peak is " + highPeak + " in last " + days + " days.");

        double lowPeak = 0.0;
        for (int i = 0; i < days; i++) {
            if (i == 0) {
                lowPeak = tradeUnits.get(i).getLow();
                continue;
            }
            lowPeak = Math.min(lowPeak, tradeUnits.get(i).getLow());
        }
        assert lowPeak == calculator.calculatePeakValue(tradeUnits, days, false);
        System.out.println("low peak is " + lowPeak + " in last " + days + " days.");
    }
}
