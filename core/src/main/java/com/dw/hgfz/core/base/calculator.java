package com.dw.hgfz.core.base;

import com.dw.hgfz.core.spec.*;

import java.math.BigDecimal;
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
    private static final String BuyLong = "做多";
    private static final String BuyShort = "做空";

    public static List<TradeProduct> calculateTR(List<TradeProduct> tradeProducts) {
        assert tradeProducts.size() > 1;
        for (int i = 0; i < tradeProducts.size(); i++) {
            if (i == 0) continue;
            TradeProduct tmpUnit = tradeProducts.get(i);
            tmpUnit.setTr(Math.max(tmpUnit.getHigh() - tmpUnit.getLow(),
                    Math.max(tmpUnit.getHigh() - tradeProducts.get(i - 1).getClose(),
                            tradeProducts.get(i - 1).getClose() - tmpUnit.getLow())));
            tradeProducts.set(i, tmpUnit);
        }
        return tradeProducts;
    }

    public static List<TradeProduct> calculateATR(List<TradeProduct> tradeProducts) {
        assert tradeProducts.size() > 20;
        double tmpATR = 0;
        TradeProduct tmpUnit = tradeProducts.get(19);
        for (int i = 0; i < 20; i++) {
            tmpATR += tradeProducts.get(i).getTr();
        }
        tmpUnit.setAtr(tmpATR / 20);
        tradeProducts.set(19, tmpUnit);
        for (int i = 20; i < tradeProducts.size(); i++) {
            TradeProduct unit = tradeProducts.get(i);
            unit.setAtr((19 * tradeProducts.get(i - 1).getAtr() + unit.getTr()) / 20);
            tradeProducts.set(i, unit);
        }
        return tradeProducts;
    }

    public static List<Order> calculateOrders(
            double openOrder, double latestATR, double minPriceFluctuation, boolean isLong) {
        List<Order> orders = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            BigDecimal tmp;
            Order order = new Order();
            tmp = calculatePriceFluctuation(new BigDecimal(String.valueOf(openOrder)), minPriceFluctuation);
            order.setOrder(tmp.doubleValue());
            tmp = calculatePriceFluctuation(new BigDecimal(String.valueOf(openOrder + 2 * latestATR * (isLong ? -1 : 1))), minPriceFluctuation);
            order.setStopOrder(tmp.doubleValue());
            orders.add(j, order);
            openOrder += latestATR / 2 * (isLong ? 1 : -1);
        }
        return orders;
    }

    public static BigDecimal calculatePriceFluctuation(BigDecimal b, double priceFluctuation) {
        if (priceFluctuation < 1) return b.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (priceFluctuation == 1) return b.setScale(0, BigDecimal.ROUND_HALF_UP);
        b = b.setScale(0, BigDecimal.ROUND_HALF_UP);
        long tmp = b.longValueExact() / (long) priceFluctuation;
        if (b.longValueExact() % (long) priceFluctuation / priceFluctuation >= 0.5) {
            return new BigDecimal(String.valueOf(tmp * priceFluctuation + priceFluctuation));
        } else {
            return new BigDecimal(String.valueOf(tmp * priceFluctuation));
        }
    }

    // with this logic background, tradeProducts list needs in desc order
    public static double calculatePeakValue(List<TradeProduct> tradeProducts, int days, boolean calculateHigh) {
        assert tradeProducts.size() >= days;
        double peakValue = 0.0;
        for (int i = 0; i < days; i++) {
            if (calculateHigh) {
                if (i == 0) {
                    peakValue = tradeProducts.get(i).getHigh();
                    continue;
                }
                peakValue = Math.max(peakValue, tradeProducts.get(i).getHigh());
            } else {
                if (i == 0) {
                    peakValue = tradeProducts.get(i).getLow();
                    continue;
                }
                peakValue = Math.min(peakValue, tradeProducts.get(i).getLow());
            }
        }
        return peakValue;
    }

    public static double calculatePeakValueWithStartDate(
            List<TradeProduct> tradeProducts, String startDate, int days, boolean calculateHigh) {
        assert startDate != null;
        List<TradeProduct> products = new ArrayList<>();
        for (int j = 0; j < tradeProducts.size(); j++) {
            if (startDate.equalsIgnoreCase(tradeProducts.get(j).getDate())) {
                for (int i = j; i < tradeProducts.size(); i++) {
                    products.add(tradeProducts.get(i));
                }
            }
        }
        return calculatePeakValue(products, days, calculateHigh);
    }

    public static TradeContract calculateTradeContract(Contract contract, TradeProduct tradeProduct) {
        TradeContract tradeContract = new TradeContract();
        tradeContract.setDate(tradeProduct.getDate());
        tradeContract.setProductName(contract.getProductName());
        tradeContract.setPrice(tradeProduct.getClose() * contract.getUnitsPerContract());
        tradeContract.setMarginPrice(tradeContract.getPrice() * contract.getNormalMargin());
        tradeContract.setPricePerUnit(tradeProduct.getClose());
        tradeContract.setUnitsPerContract(contract.getUnitsPerContract());
        tradeContract.setPricePerMinPriceFluctuation(contract.getMinPriceFluctuation() * contract.getUnitsPerContract());
        return tradeContract;
    }

    public static List<Rule> generateRuleResult(Contract contract, List<TradeProduct> tradeProducts) {
        double highPeak20, highPeak55, lowPeak20, lowPeak55;
        List<Rule> rules = new ArrayList<>();
        if (tradeProducts.size() > 20) {
            highPeak20 = calculatePeakValue(tradeProducts, 20, true);
            Rule rule20long = new Rule();
            rule20long.setSystem(Day20Breaks);
            rule20long.setMarket(contract.getMasterContract());
            rule20long.setPosition(0);//to be implemented
            rule20long.setShortOrLong(BuyLong);
            rule20long.setOrders(calculateOrders(
                    highPeak20, tradeProducts.get(0).getAtr(), contract.getMinPriceFluctuation(), true));
            rule20long.setQuitOrder(calculatePeakValue(tradeProducts, 10, false));
            rules.add(rule20long);

            lowPeak20 = calculatePeakValue(tradeProducts, 20, false);
            Rule rule20short = new Rule();
            rule20short.setSystem(Day20Breaks);
            rule20short.setMarket(contract.getMasterContract());
            rule20short.setPosition(0);//to be implemented
            rule20short.setShortOrLong(BuyShort);
            rule20short.setOrders(calculateOrders(
                    lowPeak20, tradeProducts.get(0).getAtr(), contract.getMinPriceFluctuation(), false));
            rule20short.setQuitOrder(calculatePeakValue(tradeProducts, 10, true));
            rules.add(rule20short);
        }
        if (tradeProducts.size() > 55) {
            highPeak55 = calculatePeakValue(tradeProducts, 55, true);
            Rule rule55long = new Rule();
            rule55long.setSystem(Day55Breaks);
            rule55long.setMarket(contract.getMasterContract());
            rule55long.setPosition(0);//to be implemented
            rule55long.setShortOrLong(BuyLong);
            rule55long.setOrders(calculateOrders(
                    highPeak55, tradeProducts.get(0).getAtr(), contract.getMinPriceFluctuation(), true));
            rule55long.setQuitOrder(calculatePeakValue(tradeProducts, 20, false));
            rules.add(rule55long);

            lowPeak55 = calculatePeakValue(tradeProducts, 55, false);
            Rule rule55short = new Rule();
            rule55short.setSystem(Day55Breaks);
            rule55short.setMarket(contract.getMasterContract());
            rule55short.setPosition(0);//to be implemented
            rule55short.setShortOrLong(BuyShort);
            rule55short.setOrders(calculateOrders(
                    lowPeak55, tradeProducts.get(0).getAtr(), contract.getMinPriceFluctuation(), false));
            rule55short.setQuitOrder(calculatePeakValue(tradeProducts, 20, true));
            rules.add(rule55short);
        }
        return rules;
    }
}
