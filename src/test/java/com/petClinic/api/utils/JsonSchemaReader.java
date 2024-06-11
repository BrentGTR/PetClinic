package com.petClinic.api.utils;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonSchemaReader {

    private static final Logger logger = LogManager.getLogger(JsonSchemaReader.class);

    public static String readSchemaFile(String filePath) {
        try {
            InputStream inputStream = JsonSchemaReader.class.getClassLoader().getResourceAsStream(filePath);

            if (inputStream == null) {
                logger.error("Schema file '{}' not found in classpath", filePath);
                return null;
            }

            String schemaContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            inputStream.close();

            logger.info("Successfully read schema file '{}'", filePath);
            return schemaContent;
        } catch (IOException e) {
            logger.error("Error reading schema file '{}': {}", filePath, e.getMessage());
            return null;
        }
    }
}
