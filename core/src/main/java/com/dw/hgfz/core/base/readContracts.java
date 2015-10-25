package com.dw.hgfz.core.base;

import com.dw.hgfz.common.xmlparser.DOMParser;
import com.dw.hgfz.core.spec.Contract;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 9/28/2015.
 */
public final class readContracts {

    public static final List<Contract> CONTRACTS = new ArrayList<>();

    public void loadContracts() throws IOException {
        InputStream is = readContracts.class.getClassLoader().getResourceAsStream("contracts.xml");
        DOMParser parser = new DOMParser();
        Document document = parser.parse(is);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("contract");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Contract contract = new Contract();
                contract.setTradeCode(eElement.getAttribute("tradeCode"));
                contract.setProductName(eElement.getElementsByTagName("productName").item(0).getTextContent());
                contract.setUnitsPerContract(Long.parseLong(eElement.getElementsByTagName("unitsPerContract").item(0).getTextContent()));
                contract.setMinPriceFluctuation(Double.parseDouble(eElement.getElementsByTagName("minPriceFluctuation").item(0).getTextContent()));
                contract.setMaxDailyPriceFluctuation(Double.parseDouble(eElement.getElementsByTagName("maxDailyPriceFluctuation").item(0).getTextContent()));
                contract.setLowestMargin(Double.parseDouble(eElement.getElementsByTagName("lowestMargin").item(0).getTextContent()));
                contract.setNormalMargin(Double.parseDouble(eElement.getElementsByTagName("normalMargin").item(0).getTextContent()));
                CONTRACTS.add(contract);
            }
        }
    }

    public static Contract getContract(String tradeCode) throws IOException {
        if (CONTRACTS.size() == 0) {
            readContracts contracts = new readContracts();
            contracts.loadContracts();
        }
        Contract contract = new Contract();
        for (int i = 0; i < readContracts.CONTRACTS.size(); i++) {
            if (readContracts.CONTRACTS.get(i).getTradeCode().equalsIgnoreCase(tradeCode)) {
                contract = readContracts.CONTRACTS.get(i);
                break;
            }
        }
        return contract;
    }
}
