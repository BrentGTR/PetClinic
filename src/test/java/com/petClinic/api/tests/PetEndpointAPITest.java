package com.petClinic.api.tests;

import com.petClinic.api.models.Owner;
import com.petClinic.api.models.Pet;
import com.petClinic.api.utils.JsonSchemaReader;
import com.petClinic.api.utils.SchemaValidator;
import com.petClinic.utils.ConfigManager;
import com.petClinic.api.utils.FakeDataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PetEndpointAPITest extends BaseApiTest {

    private static final Logger logger = LogManager.getLogger(PetEndpointAPITest.class);

    @BeforeSuite
    public static void setUp() {
        RestAssured.baseURI = ConfigManager.getProperty("baseUrl");
    }

    @Test
    public void testAddNewPet() {
        // ARRANGE
        // Create a new owner first
        Owner owner = FakeDataGenerator.generateOwner();
        Response ownerResponse = given()
                .contentType(ContentType.JSON)
                .body(owner)
                .when()
                .post("/owners");
        assertResponse(ownerResponse, 201);

        // Use the owner ID directly from the generated owner object
        int ownerId = owner.getId();

        Pet pet = FakeDataGenerator.generatePet();
        logger.info("Generated pet data: {}", pet);

        // ACT
        Response response = given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/owners/{ownerId}/pets", ownerId);

        // ASSERT
        assertResponse(response, 204);
    }

    private static void assertResponse(Response response, int statusCode) {
        response.then().statusCode(statusCode);
        logger.info("Response.code: {}", response.getStatusCode());

        String responseBody = response.getBody().asString();
        logger.info("Response.body: {}", responseBody);

        if (!responseBody.isEmpty()) {
            SchemaValidator.validateJsonAgainstSchema(responseBody, "schemas/petschema.json");
            logger.info("Schema validation successful");
        } else {
            logger.info("Response body is empty, skipping schema validation.");
        }
    }
}
