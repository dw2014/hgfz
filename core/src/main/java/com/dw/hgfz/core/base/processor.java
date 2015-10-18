package com.dw.hgfz.core.base;

import com.dw.hgfz.common.utils.CommonHelper;
import com.dw.hgfz.common.utils.GsonHelper;
import com.dw.hgfz.common.xmlparser.DOMParser;
import com.dw.hgfz.core.spec.Contract;
import com.dw.hgfz.core.spec.Rule;
import com.dw.hgfz.core.spec.TradeContract;
import com.dw.hgfz.core.spec.TradeProduct;
import com.google.gson.JsonArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dw on 10/18/2015.
 */
public class processor {

    private processor() {

    }

    public static List<TradeProduct> sort(List<TradeProduct> tradeProducts, String sort) {
        sortList<TradeProduct> sortList = new sortList<>();
        sortList.sort(tradeProducts, "getDate", sort);
        return tradeProducts;
    }

    public static List<TradeProduct> parseFutureResults(String results) throws Exception {
        List<TradeProduct> tradeProducts = new ArrayList<>();
        JsonArray jsonArray = GsonHelper.gsonDeserializer(results, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            TradeProduct tmp = new TradeProduct();
            tmp.setDate(jsonArray.get(i).getAsJsonArray().get(0).getAsString());
            tmp.setOpen(jsonArray.get(i).getAsJsonArray().get(1).getAsDouble());
            tmp.setHigh(jsonArray.get(i).getAsJsonArray().get(2).getAsDouble());
            tmp.setLow(jsonArray.get(i).getAsJsonArray().get(3).getAsDouble());
            tmp.setClose(jsonArray.get(i).getAsJsonArray().get(4).getAsDouble());
            tmp.setVolume(jsonArray.get(i).getAsJsonArray().get(5).getAsLong());
            tradeProducts.add(i, tmp);
        }
        return tradeProducts;
    }

    public static List<TradeProduct> parseStockResults(String results) throws Exception {
        List<TradeProduct> tradeProducts = new ArrayList<>();
        InputStream is = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
        DOMParser parser = new DOMParser();
        Document document = parser.parse(is);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("content");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                TradeProduct tradeProduct = new TradeProduct();
                tradeProduct.setDate(eElement.getAttribute("d"));
                tradeProduct.setOpen(Double.parseDouble(eElement.getAttribute("o")));
                tradeProduct.setHigh(Double.parseDouble(eElement.getAttribute("h")));
                tradeProduct.setClose(Double.parseDouble(eElement.getAttribute("c")));
                tradeProduct.setLow(Double.parseDouble(eElement.getAttribute("l")));
                tradeProducts.add(tradeProduct);
            }
        }
        return tradeProducts;
    }

    public static void processFuture(String results, String masterContract, String path) throws Exception {
        Contract contract = readContracts.getContract(masterContract);
        List<TradeProduct> tradeProducts = parseFutureResults(results);
        tradeProducts = sort(tradeProducts, null);
        tradeProducts = calculator.calculateTR(tradeProducts);
        tradeProducts = calculator.calculateATR(tradeProducts);
        tradeProducts = sort(tradeProducts, "desc");
        TradeContract tradeContract = calculator.calculateTradeContract(contract, tradeProducts.get(0));
        List<Rule> rules = calculator.generateRuleResult(contract, tradeProducts);
        System.out.println(masterContract + "最新合约交易数据");
        System.out.println(GsonHelper.gsonSerializer(tradeContract));
        System.out.println(masterContract + "最新一交易日数据");
        System.out.println(GsonHelper.gsonSerializer(tradeProducts.get(0)));
        CommonHelper.printList(rules, masterContract + "海龟法则计算结果");
        writeFile.writeToFile(tradeContract, tradeProducts.get(0), rules, masterContract, path);
    }

    public static void processStock(String results, String stockCode, String path) throws Exception {
        Contract contract = new Contract();
        contract.setMasterContract(stockCode);
        contract.setMinPriceFluctuation(0.01);
        List<TradeProduct> tradeProducts = parseStockResults(results);
        tradeProducts = sort(tradeProducts, null);
        tradeProducts = calculator.calculateTR(tradeProducts);
        tradeProducts = calculator.calculateATR(tradeProducts);
        tradeProducts = sort(tradeProducts, "desc");
        TradeContract tradeContract = calculator.calculateTradeContract(contract, tradeProducts.get(0));
        List<Rule> rules = calculator.generateRuleResult(contract, tradeProducts);
        System.out.println(stockCode + "最新一交易日数据");
        System.out.println(GsonHelper.gsonSerializer(tradeProducts.get(0)));
        CommonHelper.printList(rules, stockCode + "海龟法则计算结果");
        writeFile.writeToFile(tradeContract, tradeProducts.get(0), rules, stockCode, path);
    }
}
