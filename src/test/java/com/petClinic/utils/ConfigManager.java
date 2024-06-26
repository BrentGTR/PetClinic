package com.petClinic.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        try{
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config/config.properties");
            properties.load(fileInputStream);
            logger.info("Configuration properties loaded.");
            fileInputStream.close();

            // Set the baseUrl dynamically based on useSeleniumGrid property
            boolean useSeleniumGrid = Boolean.parseBoolean(properties.getProperty("useSeleniumGrid"));
            String baseUrl = useSeleniumGrid ? "http://spring-petclinic:8080" : "http://localhost:8080";
            properties.setProperty("baseUrl", baseUrl);
        } catch (IOException ex) {
            logger.error("IOException: ", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}