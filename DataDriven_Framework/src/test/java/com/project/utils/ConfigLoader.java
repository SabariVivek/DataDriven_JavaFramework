package com.project.utils;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyLoader.propertyLoader("src/test/resources/properties/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public int getRetryCount() {
        int prop = Integer.parseInt(properties.getProperty("retryCount"));
        if (properties.getProperty("retryCount") != null) return prop;
        else throw new RuntimeException("Property \"Retry Count\" is not specified in the config.properties file");
    }

    public String getAutomaticIssueCreation_ONorOFF() {
        String prop = properties.getProperty("automatic_Issue_Creation_In_JIRA");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Automatic issue creation in jira (ON or OFF)\" is not specified in the config.properties file");
    }

    public String getJiraURL() {
        String prop = properties.getProperty("jiraURL");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Jira - URL\" is not specified in the config.properties file");
    }

    public String getJiraUserName() {
        String prop = properties.getProperty("jiraUserName");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Jira - Username\" is not specified in the config.properties file");
    }

    public String getJiraSecretKey() {
        String prop = properties.getProperty("jiraSecretKey");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Jira - Secret Key\" is not specified in the config.properties file");
    }
}