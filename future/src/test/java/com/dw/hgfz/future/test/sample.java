package com.dw.hgfz.future.test;

import com.dw.hgfz.common.httpclient.ApacheClient;
import com.dw.hgfz.common.utils.ConfigHelper;
import com.dw.hgfz.core.base.calculator;
import com.dw.hgfz.core.base.readContracts;
import com.google.gson.JsonArray;
import org.junit.Test;

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
    public void tmp() throws Exception {
        System.out.println(readContracts.CONTRACTS.size());
    }
}
