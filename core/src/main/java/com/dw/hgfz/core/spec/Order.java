package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dw on 9/8/2015.
 */
public class Order {

    @SerializedName("入市价格")
    private double order;
    @SerializedName("止损价格")
    private double stopOrder;

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double value) {
        this.order = value;
    }

    public double getStopOrder() {
        return this.stopOrder;
    }

    public void setStopOrder(double value) {
        this.stopOrder = value;
    }
}
