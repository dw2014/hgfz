package com.dw.hgfz.core.spec;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by dw on 9/21/2015.
 */

@XmlRootElement(name = "Contract")
@XmlType(propOrder = {"contractCode", "unitsPerContract", "minPriceFluctuation", "maxDailyPriceFluctuation", "lowestMargin", "normalMargin"})
public class Contract {

    @XmlElement(required = true, name = "交易代码")
    private String contractCode;

    @XmlElement(required = true, name = "交易单位")
    private long unitsPerContract;

    @XmlElement(required = true, name = "最小变动价位")
    private double minPriceFluctuation;

    @XmlElement(required = true, name = "每日最大价格波动")
    private double maxDailyPriceFluctuation;

    @XmlElement(required = true, name = "最低交易保证金")
    private double lowestMargin;

    @XmlElement(required = true, name = "平日交易保证金")
    private double normalMargin;
}
