package com.petClinic.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.testng.Assert;

import java.io.IOException;

public class SchemaValidator {

    public static void validateJsonAgainstSchema(String responseJson, String schemaPath) {
        try {
            String schemaContent = JsonSchemaReader.readSchemaFile(schemaPath);

            if (schemaContent == null) {
                Assert.fail("Failed to read schema file: " + schemaPath);
                return;
            }

            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(new ObjectMapper().readTree(schemaContent));

            schema.validate(new ObjectMapper().readTree(responseJson));
        } catch (IOException | ProcessingException e) {
            Assert.fail("Schema validation failed: " + e.getMessage());
        }
    }
}
