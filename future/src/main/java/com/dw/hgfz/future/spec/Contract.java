package com.dw.hgfz.future.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 9/15/2015.
 */
public class Contract {
    @SerializedName("交易品种")
    private String contractProduct;

    @SerializedName("交易单位")
    private long unitsPerContract;

    @SerializedName("报价单位")
    private String pricePerUnit;

    @SerializedName("最小变动价位")
    private double minPriceFluctuation;

    @SerializedName("每日最大价格波动")
    private double maxDailyPriceFluctuation;

    @SerializedName("合约交割月份")
    private String expirationMonths;

    @SerializedName("交易时间")
    private String tradingHours;

    @SerializedName("最后交易日")
    private String lastTradingDay;

    @SerializedName("交割日期")
    private String settlementDate;

    @SerializedName("交个品级")
    private String gradeAndQuality;

    @SerializedName("交割地点")
    private String settlementPlace;

    @SerializedName("最低交易保证金")
    private double lowestMargin;

    @SerializedName("交割方式")
    private String settlementMethod;

    @SerializedName("交易代码")
    private String tradeCode;

    @SerializedName("上市交易所")
    private String exchangeVendor;

    public String getContractProduct() {
        return this.contractProduct;
    }

    public void setContractProduct(String value) {
        this.contractProduct = value;
    }

    public long getUnitsPerContract() {
        return this.unitsPerContract;
    }

    public void setUnitsPerContract(long value) {
        this.unitsPerContract = value;
    }

    public String getPricePerUnit() {
        return this.pricePerUnit;
    }

    public void setPricePerUnit(String value) {
        this.pricePerUnit = value;
    }

    public double getMinPriceFluctuation() {
        return this.minPriceFluctuation;
    }

    public void setMinPriceFluctuation(double value) {
        this.minPriceFluctuation = value;
    }

    public double getMaxDailyPriceFluctuation() {
        return this.maxDailyPriceFluctuation;
    }

    public void setMaxDailyPriceFluctuation(double value) {
        this.maxDailyPriceFluctuation = value;
    }

    public String getExpirationMonths() {
        return this.expirationMonths;
    }

    public void setExpirationMonths(String value) {
        this.expirationMonths = value;
    }

    public String getTradingHours() {
        return this.tradingHours;
    }

    public void setTradingHours(String value) {
        this.tradingHours = value;
    }

    public String getLastTradingDay() {
        return this.lastTradingDay;
    }

    public void setLastTradingDay(String value) {
        this.lastTradingDay = value;
    }

    public String getSettlementDate() {
        return this.settlementDate;
    }

    public void setSettlementDate(String value) {
        this.settlementDate = value;
    }

    public String getGradeAndQuality() {
        return this.gradeAndQuality;
    }

    public void setGradeAndQuality(String value) {
        this.gradeAndQuality = value;
    }

    public String getSettlementPlace() {
        return this.settlementPlace;
    }

    public void setSettlementPlace(String value) {
        this.settlementPlace = value;
    }

    public double getLowestMargin() {
        return this.lowestMargin;
    }

    public void setLowestMargin(double value) {
        this.lowestMargin = value;
    }

    public String getSettlementMethod() {
        return this.settlementMethod;
    }

    public void setSettlementMethod(String value) {
        this.settlementMethod = value;
    }

    public String getTradeCode() {
        return this.tradeCode;
    }

    public void setTradeCode(String value) {
        this.tradeCode = value;
    }

    public String getExchangeVendor() {
        return this.exchangeVendor;
    }

    public void setExchangeVendor(String value) {
        this.exchangeVendor = value;
    }
}
