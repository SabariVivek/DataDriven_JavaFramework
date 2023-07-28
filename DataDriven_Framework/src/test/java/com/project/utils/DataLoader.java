package com.project.utils;

import java.util.Properties;

public class DataLoader {

    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertyLoader.propertyLoader("src/test/resources/properties/data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getURL() {
        String prop = properties.getProperty("url");
        if (prop != null)
            return prop;
        else throw new RuntimeException("Property \"URL\" is not specified in the config.properties file");
    }

    public String getBrowserType() {
        String prop = properties.getProperty("browser");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property \"Browser\" is not specified in the config.properties file");
    }
}