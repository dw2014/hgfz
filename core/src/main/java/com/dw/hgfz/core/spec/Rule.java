package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class Rule {

    @SerializedName("入市系统")
    private String system;
    @SerializedName("市场")
    private String market;
    @SerializedName("头寸")
    private int position;
    @SerializedName("入市点")
    private List<Order> orders;
    @SerializedName("退出点")
    private double quitOrder;

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String value) {
        this.system = value;
    }

    public String getMarket() {
        return this.market;
    }

    public void setMarket(String value) {
        this.market = value;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int value) {
        this.position = value;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> valus) {
        this.orders = valus;
    }

    public double getQuitOrder() {
        return this.quitOrder;
    }

    public void setQuitOrder(double value) {
        this.quitOrder = value;
    }
}
