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
        contract.setContractProduct(new String("�ƽ�".getBytes(), "UTF-8"));
        contract.setUnitsPerContract(1000);
        contract.setPricePerUnit("Ԫ(�����)/��");
        contract.setMinPriceFluctuation(0.01);
        contract.setMaxDailyPriceFluctuation(0.05);
        contract.setExpirationMonths("1-12��");
        contract.setTradingHours("����9:00�D11:30 ����1:30�D3:00");
        contract.setLastTradingDay("��Լ�����·ݵ�15�գ�����������˳�ӣ�");
        contract.setSettlementDate("������պ��������������");
        contract.setGradeAndQuality("������С��99.95%�Ĺ����𶧼����������Ͽɵ��׶ؽ����г�Э�ᣨLBMA���϶��ĺϸ񹩻��̻����������ı�׼�𶧣����������涨����������");
        contract.setSettlementPlace("������ָ��������");
        contract.setLowestMargin(0.07);
        contract.setSettlementMethod("ʵ�ｻ��");
        contract.setTradeCode("AU");
        contract.setExchangeVendor("�Ϻ��ڻ�������");
        return contract;
    }
}
