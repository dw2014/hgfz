package com.dw.hgfz.future.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 9/15/2015.
 */
public class Contract {
    @SerializedName("����Ʒ��")
    private String contractProduct;

    @SerializedName("���׵�λ")
    private long unitsPerContract;

    @SerializedName("���۵�λ")
    private String pricePerUnit;

    @SerializedName("��С�䶯��λ")
    private double minPriceFluctuation;

    @SerializedName("ÿ�����۸񲨶�")
    private double maxDailyPriceFluctuation;

    @SerializedName("��Լ�����·�")
    private String expirationMonths;

    @SerializedName("����ʱ��")
    private String tradingHours;

    @SerializedName("�������")
    private String lastTradingDay;

    @SerializedName("��������")
    private String settlementDate;

    @SerializedName("����Ʒ��")
    private String gradeAndQuality;

    @SerializedName("����ص�")
    private String settlementPlace;

    @SerializedName("��ͽ��ױ�֤��")
    private double lowestMargin;

    @SerializedName("���ʽ")
    private String settlementMethod;

    @SerializedName("���״���")
    private String tradeCode;

    @SerializedName("���н�����")
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
