package com.dw.hgfz.core.base;

import com.dw.hgfz.common.xmlparser.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dw on 10/21/2015.
 */
public class readConfigs {
    public static final Map<String, String> CONFIGS = new HashMap<>();

    public void loadConfigs() throws IOException {
        InputStream is = readConfigs.class.getClassLoader().getResourceAsStream("configs.xml");
        DOMParser parser = new DOMParser();
        Document document = parser.parse(is);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("config");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                CONFIGS.put(eElement.getAttribute("key"), eElement.getAttribute("value"));
            }
        }
    }

    public static String getConfig(String key) throws IOException {
        if (CONFIGS.size() == 0) {
            readConfigs configs = new readConfigs();
            configs.loadConfigs();
        }
        return CONFIGS.get(key);
    }
}
