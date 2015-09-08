package com.dw.hgfz.core.spec;

/**
 * Created by dw on 9/7/2015.
 */
public class TradeUnit {

    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private double tr;
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
