package com.dw.hgfz.future.test;

import com.dw.hgfz.common.httpclient.ApacheClient;
import com.dw.hgfz.common.utils.ConfigHelper;
import com.dw.hgfz.core.base.calculator;
import com.dw.hgfz.core.base.readContracts;
import com.dw.hgfz.core.spec.TradeProduct;
import com.google.gson.JsonArray;
import org.junit.Test;

import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class sample {

    @Test
    public void sampleCalculate() throws Exception {
        for (int i = 0; i < readContracts.CONTRACTS.size(); i++) {
            String contract = readContracts.CONTRACTS.get(i).getMasterContract();
            JsonArray results = ApacheClient.executeGet(ConfigHelper.getSetting("futureDataURI") + contract);
            calculator.process(results, contract, ConfigHelper.getSetting("logPath"));
        }
    }

    @Test
    public void sampleCalculateSingleContractPeakValueWithStartDate() throws Exception {
        String contract = "AG1512";
        JsonArray results = ApacheClient.executeGet(ConfigHelper.getSetting("futureDataURI") + contract);
        List<TradeProduct> tradeProducts = calculator.parseRestResults(results);
        calculator.sort(tradeProducts, "desc");
        System.out.println(calculator.calculatePeakValue(tradeProducts, 40, true));
        System.out.println(calculator.calculatePeakValueWithStartDate(tradeProducts, "2015-09-22", 20, true));
    }
}
