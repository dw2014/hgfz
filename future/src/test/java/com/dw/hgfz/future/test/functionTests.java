package com.dw.hgfz.future.test;

import com.dw.hgfz.common.utils.CommonHelper;
import com.dw.hgfz.common.utils.RandomHelper;
import com.dw.hgfz.core.base.calculator;
import com.dw.hgfz.core.base.processor;
import com.dw.hgfz.core.spec.Order;
import com.dw.hgfz.core.spec.TradeProduct;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 9/24/2015.
 */
public class functionTests {

    @Test
    public void testProcessorSort() throws Exception {
        List<TradeProduct> tradeProducts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TradeProduct tradeProduct = new TradeProduct();
            tradeProduct.setDate("2015-05-" + String.format("%02d", RandomHelper.randomInt(31)));
            tradeProducts.add(i, tradeProduct);
        }
        tradeProducts = processor.sort(tradeProducts, null);
        for (int i = 0; i < 5; i++) {
            if (i == 4) continue;
            assert Long.parseLong(tradeProducts.get(i).getDate().replace("-", ""))
                    <= Long.parseLong(tradeProducts.get(i + 1).getDate().replace("-", ""));
        }
        CommonHelper.printList(tradeProducts, "asc order");
        tradeProducts = processor.sort(tradeProducts, "desc");
        for (int i = 0; i < 5; i++) {
            if (i == 4) continue;
            assert Long.parseLong(tradeProducts.get(i).getDate().replace("-", ""))
                    >= Long.parseLong(tradeProducts.get(i + 1).getDate().replace("-", ""));
        }
        CommonHelper.printList(tradeProducts, "desc order");
    }

    @Test
    public void testCalculateTR() throws Exception {
        List<TradeProduct> tradeProducts = new ArrayList<>();
        TradeProduct yesterday = new TradeProduct();
        yesterday.setClose(Math.round(RandomHelper.randomDouble() * 100));
        tradeProducts.add(0, yesterday);
        TradeProduct today = new TradeProduct();
        today.setHigh(Math.round(RandomHelper.randomDouble() * 100));
        today.setLow(Math.round(RandomHelper.randomDouble() * 100));
        tradeProducts.add(1, today);
        tradeProducts = calculator.calculateTR(tradeProducts);
        assert tradeProducts.get(1).getTr() == Math.max(today.getHigh() - today.getLow(),
                Math.max(today.getHigh() - yesterday.getClose(), yesterday.getClose() - today.getLow()));
        CommonHelper.printList(tradeProducts, null);
    }

    @Test
    public void testCalculateATR() throws Exception {
        List<TradeProduct> tradeProducts = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            TradeProduct tradeProduct = new TradeProduct();
            tradeProduct.setHigh(Math.round(RandomHelper.randomDouble() * 100));
            tradeProduct.setLow(Math.round(RandomHelper.randomDouble() * 100));
            tradeProduct.setClose(Math.round(RandomHelper.randomDouble() * 100));
            tradeProducts.add(i, tradeProduct);
        }
        tradeProducts = calculator.calculateTR(tradeProducts);
        tradeProducts = calculator.calculateATR(tradeProducts);
        for (int i = 0; i < 20; i++) {
            assert tradeProducts.get(i).getAtr() == 0.0;
        }
        double first20TRSum = 0.0;
        for (int i = 0; i < 20; i++) {
            first20TRSum += tradeProducts.get(i).getTr();
        }
        assert tradeProducts.get(20).getAtr() == first20TRSum / 20;
        for (int i = 21; i < tradeProducts.size(); i++) {
            assert tradeProducts.get(i).getAtr() == (19 * tradeProducts.get(i - 1).getAtr() + tradeProducts.get(i).getTr()) / 20;
        }
        CommonHelper.printList(tradeProducts, "TR and ATR are calculated and validated.");
    }

    @Test
    public void testCalculateOrder() throws Exception {
        double openOrder = Math.round(RandomHelper.randomDouble() * 1000) % 1000;
        double latestATR = Math.round(RandomHelper.randomDouble() * 1000) % 100;
        System.out.println("openOrder: " + openOrder + "|latestATR: " + latestATR);

        List<Order> orders = calculator.calculateOrders(openOrder, latestATR, 0.0, true);
        double tmpOpenOrder = openOrder;
        for (int i = 0; i < orders.size(); i++) {
            assert orders.get(i).getOrder() == tmpOpenOrder;
            assert orders.get(i).getStopOrder() == orders.get(i).getOrder() - latestATR * 2;
            tmpOpenOrder += latestATR / 2;
        }
        CommonHelper.printList(orders, "long order list");
        orders = calculator.calculateOrders(openOrder, latestATR, 0.0, false);
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
        List<TradeProduct> tradeProducts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TradeProduct tradeProduct = new TradeProduct();
            tradeProduct.setHigh(Math.round(RandomHelper.randomDouble() * 1000) % 1000);
            tradeProduct.setLow(Math.round(RandomHelper.randomDouble() * 1000) % 1000);
            tradeProducts.add(i, tradeProduct);
        }
        CommonHelper.printList(tradeProducts, null);

        int days = RandomHelper.randomInt(tradeProducts.size());
        double highPeak = 0.0;
        for (int i = 0; i < days; i++) {
            if (i == 0) {
                highPeak = tradeProducts.get(i).getHigh();
                continue;
            }
            highPeak = Math.max(highPeak, tradeProducts.get(i).getHigh());
        }
        assert highPeak == calculator.calculatePeakValue(tradeProducts, days, true);
        System.out.println("high peak is " + highPeak + " in last " + days + " days.");

        double lowPeak = 0.0;
        for (int i = 0; i < days; i++) {
            if (i == 0) {
                lowPeak = tradeProducts.get(i).getLow();
                continue;
            }
            lowPeak = Math.min(lowPeak, tradeProducts.get(i).getLow());
        }
        assert lowPeak == calculator.calculatePeakValue(tradeProducts, days, false);
        System.out.println("low peak is " + lowPeak + " in last " + days + " days.");
    }

    @Test
    public void testCalculatePriceFluctuation() throws Exception {
        assert calculator.calculatePriceFluctuation(new BigDecimal("100.01"), 0.01).doubleValue() ==
                new BigDecimal("100.01").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("100.01"), 1).doubleValue() ==
                new BigDecimal("100").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("102.01"), 2).doubleValue() ==
                new BigDecimal("102").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("103.00"), 2).doubleValue() ==
                new BigDecimal("104").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("103.66"), 2).doubleValue() ==
                new BigDecimal("104").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("102.01"), 5).doubleValue() ==
                new BigDecimal("100").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("102.50"), 5).doubleValue() ==
                new BigDecimal("105").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("103.66"), 5).doubleValue() ==
                new BigDecimal("105").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("103.66"), 10).doubleValue() ==
                new BigDecimal("100").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("105.00"), 10).doubleValue() ==
                new BigDecimal("110").doubleValue();
        assert calculator.calculatePriceFluctuation(new BigDecimal("106.66"), 10).doubleValue() ==
                new BigDecimal("110").doubleValue();
    }
}
