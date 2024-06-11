package com.petClinic.api.tests;

import com.petClinic.api.models.Owner;
import com.petClinic.api.utils.JsonSchemaReader;
import com.petClinic.api.utils.SchemaValidator;
import com.petClinic.utils.ConfigManager;
import com.petClinic.api.utils.FakeDataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class OwnersEndpointAPITest extends BaseApiTest {

    private static final Logger logger = LogManager.getLogger(OwnersEndpointAPITest.class);

    @BeforeSuite
    public static void setUp() {
        RestAssured.baseURI = ConfigManager.getProperty("baseUrl");
    }

    @Test
    public void testCreateOwner() {
        // ARRANGE
        Owner owner = FakeDataGenerator.generateOwner();

        // ACT
        Response response = given()
                .contentType(ContentType.JSON)
                .body(owner)
                .when()
                .post("/owners");

        // ASSERT
        assertResponse(response, 201);

        // Cleanup
        String locationHeader = response.getHeader("Location");
        if (locationHeader != null) {
            String[] parts = locationHeader.split("/");
            String ownerId = parts[parts.length - 1];
            given()
                    .pathParam("ownerId", ownerId)
                    .when()
                    .delete("/owners/{ownerId}");

            logger.info("Owner with ID {} deleted successfully", ownerId);
        }
    }

    @Test
    public void testGetOwnerDetailsByID() {
        // ACT
        Response response = given()
                .pathParam("ownerId", 1) // Assuming owner ID 1 exists
                .when()
                .get("/owners/{ownerId}");

        // ASSERT
        assertResponse(response, 200);
    }

    private static void assertResponse(Response response, int statusCode) {
        response.then().statusCode(statusCode);
        String responseBody = response.getBody().asString();
        logger.info("Response.body: {}", responseBody);
        logger.info("Response.code: {}", response.getStatusCode());

        if (responseBody.isEmpty()) {
            logger.info("Response body is empty, skipping schema validation.");
        } else {
            SchemaValidator.validateJsonAgainstSchema(responseBody, "schemas/ownerschema.json");
            logger.info("Schema validation successful");
        }
    }
}
