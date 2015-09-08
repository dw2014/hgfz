package com.dw.hgfz.common.utils;

import junit.framework.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by dw on 9/7/2015.
 */
public final class ConfigHelper {
    private static final Properties properties = new Properties();

    static {
        try {
            ClassLoader loader = Test.class.getClassLoader();
            String profile = System.getProperty("profile", "default");
            System.out.println("use profile: " + profile);
            String configFile = profile + ".properties";
            System.out.println("use config: " + configFile);
            properties.load(loader.getResourceAsStream(configFile));
            loadAdditionalProperties();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getSetting(String key) {
        try {
            return properties.getProperty(key);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return null;
    }

    private static void loadAdditionalProperties() throws Exception {
        String filePathString = getSetting("additionalPropertiesPath");
        System.out.println("try load: " + filePathString);
        if (filePathString == null) return;
        //opening file for reading
        File f = new File(filePathString);
        if (f.exists() && !f.isDirectory()) {
            FileInputStream file = new FileInputStream(filePathString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            //reading file content line by line
            String line = null;
            while ((line = reader.readLine()) != null && line.trim().length() > 0) {
                properties.put(line.split(":")[0], line.substring(line.split(":")[0].length() + 1));
            }
            reader.close();
            System.out.println("try load: " + filePathString + " succeed.");
        } else {
            System.out.println("try load: " + filePathString + " non-exist.");
        }
    }
}
