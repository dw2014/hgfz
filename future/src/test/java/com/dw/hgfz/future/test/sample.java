package com.dw.hgfz.future.test;

import com.dw.hgfz.common.httpclient.ApacheClient;
import com.dw.hgfz.common.utils.ConfigHelper;
import com.dw.hgfz.core.base.calculator;
import com.dw.hgfz.core.base.processor;
import com.dw.hgfz.core.spec.TradeProduct;
import org.junit.Test;

import java.util.List;

/**
 * Created by dw on 9/7/2015.
 */
public class sample {

    @Test
    public void sampleFutureCalculate() throws Exception {
        String[] contracts = ConfigHelper.getSetting("masterContracts").split(",");
        for (int i = 0; i < contracts.length; i++) {
            String contract = contracts[i];
            processor.processFuture(contract, ConfigHelper.getSetting("logPath"), true);
        }
    }

    @Test
    public void sampleCalculateSingleContractPeakValueWithStartDate() throws Exception {
        String contract = "AG1512";
        String results = ApacheClient.executeGet(ConfigHelper.getSetting("futureDataURI") + contract);
        List<TradeProduct> tradeProducts = processor.parseFutureResults(results);
        processor.sort(tradeProducts, "desc");
        System.out.println(calculator.calculatePeakValue(tradeProducts, 40, true));
        System.out.println(calculator.calculatePeakValueWithStartDate(tradeProducts, "2015-09-22", 20, true));
    }

    @Test
    public void sampleStockCalculate() throws Exception {
        //String results = ApacheClient.executeGet(ConfigHelper.getSetting("stockDataURI") + "sh601600" + processor.setDateRange());
        processor.processStock("sh601600", ConfigHelper.getSetting("logPath"), true);
    }
}
