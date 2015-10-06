package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 10/6/2015.
 */
public class TradeContract {

    @SerializedName("日期")
    private String date;

    @SerializedName("商品名称")
    private String productName;

    @SerializedName("合约价格/手")
    private double price;

    @SerializedName("保证金价格/手")
    private double marginPrice;

    @SerializedName("价格/交易单位")
    private double pricePerUnit;

    @SerializedName("交易单位/手")
    private long unitsPerContract;

    @SerializedName("价格/最小变动价位")
    private double pricePerMinPriceFluctuation;

    public String getDate() {
        return this.date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double value) {
        this.price = value;
    }

    public double getMarginPrice() {
        return this.marginPrice;
    }

    public void setMarginPrice(double value) {
        this.marginPrice = value;
    }

    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    public void setPricePerUnit(double value) {
        this.pricePerUnit = value;
    }

    public long getUnitsPerContract() {
        return this.unitsPerContract;
    }

    public void setUnitsPerContract(long value) {
        this.unitsPerContract = value;
    }

    public double getPricePerMinPriceFluctuation() {
        return this.pricePerMinPriceFluctuation;
    }

    public void setPricePerMinPriceFluctuation(double value) {
        this.pricePerMinPriceFluctuation = value;
    }


}
