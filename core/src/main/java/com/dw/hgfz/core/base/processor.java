package com.dw.hgfz.core.base;

import com.dw.hgfz.common.httpclient.ApacheClient;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dw on 10/18/2015.
 */
public class processor {

    private static final String FUTUREDATAURIKEY = "futureDataURI";
    private static final String STOCKDATAURIKEY = "stockDataURI";

    private processor() {

    }

    public static String getRawResults(String code, boolean isFuture) throws Exception {
        if (isFuture) {
            return ApacheClient.executeGet(readConfigs.getConfig(FUTUREDATAURIKEY) + code);
        } else {
            return ApacheClient.executeGet(readConfigs.getConfig(STOCKDATAURIKEY) + code + setDateRange());
        }
    }

    public static List<TradeProduct> sort(List<TradeProduct> tradeProducts, String sort) {
        sortList<TradeProduct> sortList = new sortList<>();
        sortList.sort(tradeProducts, "getDate", sort);
        return tradeProducts;
    }

    public static String setDateRange() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dateNow = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateNow);
        cal.add(Calendar.DATE, -100);
        String begin_date = dateFormat.format(cal.getTime());
        String end_date = dateFormat.format(dateNow);
        return String.format("&begin_date=%s&end_date=%s", begin_date, end_date);
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
                tradeProduct.setVolume(Long.parseLong(eElement.getAttribute("v")));
                tradeProducts.add(tradeProduct);
            }
        }
        return tradeProducts;
    }

    public static String processFuture(String futureCode, String path, boolean writeToFile)
            throws Exception {
        String results = getRawResults(futureCode, true);
        Contract contract = readContracts.getContract(futureCode);
        List<TradeProduct> tradeProducts = parseFutureResults(results);
        tradeProducts = sort(tradeProducts, null);
        tradeProducts = calculator.calculateTR(tradeProducts);
        tradeProducts.remove(0);//first tr is always 0, cause an error in calculate atr
        tradeProducts = calculator.calculateATR(tradeProducts);
        tradeProducts = sort(tradeProducts, "desc");
        TradeContract tradeContract = calculator.calculateTradeContract(contract, tradeProducts.get(0));
        List<Rule> rules = calculator.generateRuleResult(contract, tradeProducts);
        System.out.println(futureCode + "���º�Լ��������");
        System.out.println(GsonHelper.gsonSerializer(tradeContract));
        System.out.println(futureCode + "����һ����������");
        System.out.println(GsonHelper.gsonSerializer(tradeProducts.get(0)));
        CommonHelper.printList(rules, futureCode + "���귨�������");
        if (writeToFile) {
            writeFile.writeToFile(tradeContract, tradeProducts.get(0), rules, futureCode, path);
            return null;
        } else {
            return writeMessage.writeDisplayMessage(tradeContract, tradeProducts.get(0), rules, futureCode);
        }
    }

    public static String processStock(String stockCode, String path, boolean writeToFile)
            throws Exception {
        String results = getRawResults(stockCode, false);
        Contract contract = new Contract();
        contract.setMasterContract(stockCode);
        contract.setMinPriceFluctuation(0.01);
        List<TradeProduct> tradeProducts = parseStockResults(results);
        tradeProducts = sort(tradeProducts, null);
        tradeProducts = calculator.calculateTR(tradeProducts);
        tradeProducts.remove(0);//first tr is always 0, cause an error in calculate atr
        tradeProducts = calculator.calculateATR(tradeProducts);
        tradeProducts = sort(tradeProducts, "desc");
        TradeContract tradeContract = calculator.calculateTradeContract(contract, tradeProducts.get(0));
        List<Rule> rules = calculator.generateRuleResult(contract, tradeProducts);
        System.out.println(stockCode + "����һ����������");
        System.out.println(GsonHelper.gsonSerializer(tradeProducts.get(0)));
        CommonHelper.printList(rules, stockCode + "���귨�������");
        if (writeToFile) {
            writeFile.writeToFile(tradeContract, tradeProducts.get(0), rules, stockCode, path);
            return null;
        } else {
            return writeMessage.writeDisplayMessage(tradeContract, tradeProducts.get(0), rules, stockCode);
        }
    }

    private static Integer TryParseTradeCode(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static String process(String code, String path, boolean writeToFile) throws Exception {
        if (code.length() == 8 && (code.startsWith("sz") || code.startsWith("sh"))) {
            return processStock(code, path, writeToFile);
        }
        int tradeCode = TryParseTradeCode(code);
        if (tradeCode < 0) {
            return processFuture(code.toUpperCase(), path, writeToFile);
        } else if (tradeCode > 600000) {
            return processStock("sh" + code, path, writeToFile);
        } else if (tradeCode > 000001) {
            return processStock("sz" + code, path, writeToFile);
        }
        throw new InputMismatchException();
    }
}
