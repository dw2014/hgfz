package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 9/21/2015.
 */
public class Contract {

    @SerializedName("交易代码")
    private String tradeCode;

    @SerializedName("交易产品")
    private String productName;

    @SerializedName("合约代码")
    private String contractCode;

    @SerializedName("交易单位")
    private long unitsPerContract;

    @SerializedName("最小变动价位")
    private double minPriceFluctuation;

    @SerializedName("涨停板价位比")
    private double dailyPriceFluctuation;

    @SerializedName("最低保证金")
    private double lowestMargin;

    @SerializedName("平日保证金")
    private double normalMargin;

    @SerializedName("平日交易时间")
    private String tradingTime;

    public String getTradeCode() {
        return this.tradeCode;
    }

    public void setTradeCode(String value) {
        this.tradeCode = value;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }

    public String getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(String value) {
        this.contractCode = value;
    }

    public long getUnitsPerContract() {
        return this.unitsPerContract;
    }

    public void setUnitsPerContract(long value) {
        this.unitsPerContract = value;
    }

    public double getMinPriceFluctuation() {
        return this.minPriceFluctuation;
    }

    public void setMinPriceFluctuation(double value) {
        this.minPriceFluctuation = value;
    }

    public double getDailyPriceFluctuation() {
        return this.dailyPriceFluctuation;
    }

    public void setDailyPriceFluctuation(double value) {
        this.dailyPriceFluctuation = value;
    }

    public double getLowestMargin() {
        return this.lowestMargin;
    }

    public void setLowestMargin(double value) {
        this.lowestMargin = value;
    }

    public double getNormalMargin() {
        return this.normalMargin;
    }

    public void setNormalMargin(double value) {
        this.normalMargin = value;
    }

    public String getTradingTime() { return this.tradingTime; }

    public void setTradingTime(String value) { this.tradeCode = value; }
}
