package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 9/21/2015.
 */
public class Contract {

    @SerializedName("交易代码")
    private String contractCode;

    @SerializedName("交易产品")
    private String productName;

    @SerializedName("交易单位")
    private long unitsPerContract;

    @SerializedName("最小变动价位")
    private double minPriceFluctuation;

    @SerializedName("每日最大价格波动")
    private double maxDailyPriceFluctuation;

    @SerializedName("最低交易保证金")
    private double lowestMargin;

    @SerializedName("平日交易保证金")
    private double normalMargin;

    public String getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(String value) {
        this.contractCode = value;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String value) {
        this.productName = value;
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

    public double getMaxDailyPriceFluctuation() {
        return this.maxDailyPriceFluctuation;
    }

    public void setMaxDailyPriceFluctuation(double value) {
        this.maxDailyPriceFluctuation = value;
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
}
