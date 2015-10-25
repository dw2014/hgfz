package com.dw.hgfz.core.spec;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class Rule {

    @SerializedName("入市系统")
    private String system;
    @SerializedName("合约代码")
    private String contractCode;
    @SerializedName("头寸规模")
    private int position;
    @SerializedName("市场行情")
    private String marketQuotation;
    @SerializedName("入市价位")
    private List<Order> orders;
    @SerializedName("退出价位")
    private double quitOrder;

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String value) {
        this.system = value;
    }

    public String getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(String value) {
        this.contractCode = value;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int value) {
        this.position = value;
    }

    public String getMarketQuotation() {
        return this.marketQuotation;
    }

    public void setMarketQuotation(String value) {
        this.marketQuotation = value;
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
