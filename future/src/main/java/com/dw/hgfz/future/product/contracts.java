package com.dw.hgfz.future.product;

import com.dw.hgfz.future.spec.Contract;

/**
 * Created by dw on 9/15/2015.
 */
public class contracts {

    private contracts() {

    }

    public static Contract auContract() throws Exception {
        Contract contract = new Contract();
        contract.setContractProduct(new String("黄金".getBytes(), "UTF-8"));
        contract.setUnitsPerContract(1000);
        contract.setPricePerUnit("元(人民币)/克");
        contract.setMinPriceFluctuation(0.01);
        contract.setMaxDailyPriceFluctuation(0.05);
        contract.setExpirationMonths("1-12月");
        contract.setTradingHours("上午9:00―11:30 下午1:30―3:00");
        contract.setLastTradingDay("合约交割月份的15日（遇法定假日顺延）");
        contract.setSettlementDate("最后交易日后连续五个工作日");
        contract.setGradeAndQuality("金含量不小于99.95%的国产金锭及经交易所认可的伦敦金银市场协会（LBMA）认定的合格供货商或精炼厂生产的标准金锭（具体质量规定见附件）。");
        contract.setSettlementPlace("交易所指定交割金库");
        contract.setLowestMargin(0.07);
        contract.setSettlementMethod("实物交割");
        contract.setTradeCode("AU");
        contract.setExchangeVendor("上海期货交易所");
        return contract;
    }
}
