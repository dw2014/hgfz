package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 9/7/2015.
 */
public class TradeUnit {

    @SerializedName("日期")
    private String date;
    @SerializedName("开盘")
    private double open;
    @SerializedName("最高")
    private double high;
    @SerializedName("最低")
    private double low;
    @SerializedName("收盘")
    private double close;
    @SerializedName("成交")
    private long volume;
    @SerializedName("真实波动幅度")
    private double tr;
    @SerializedName("真实波动幅度20日均值")
    private double atr;

    public String getDate() {
        return this.date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public double getOpen() {
        return this.open;
    }

    public void setOpen(double value) {
        this.open = value;
    }

    public double getHigh() {
        return this.high;
    }

    public void setHigh(double value) {
        this.high = value;
    }

    public double getLow() {
        return this.low;
    }

    public void setLow(double value) {
        this.low = value;
    }

    public double getClose() {
        return this.close;
    }

    public void setClose(double value) {
        this.close = value;
    }

    public long getVolume() {
        return this.volume;
    }

    public void setVolume(long value) {
        this.volume = value;
    }

    public double getTr() {
        return this.tr;
    }

    public void setTr(double value) {
        this.tr = value;
    }

    public double getAtr() {
        return this.atr;
    }

    public void setAtr(double value) {
        this.atr = value;
    }
}
