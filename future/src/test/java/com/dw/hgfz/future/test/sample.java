package com.dw.hgfz.future.test;

import com.dw.hgfz.common.httpclient.ApacheCommonClient;
import com.dw.hgfz.common.utils.ConfigHelper;
import com.dw.hgfz.core.base.calculator;
import com.google.gson.JsonArray;
import org.junit.Test;

/**
 * Created by dw on 9/7/2015.
 */
public class sample {

    @Test
    public void sampleCalculate() throws Exception {
        String contract = "AU1512";
        JsonArray results = ApacheCommonClient.executeGet(ConfigHelper.getSetting("futureDataURI") + contract);
        calculator.process(results, contract);
    }
}
