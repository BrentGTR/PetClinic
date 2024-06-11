
# PetClinic Automated Testing Suite

Welcome to the PetClinic Automated Testing Suite! This project aims to ensure the reliability and functionality of the PetClinic application through automated testing. With this suite, you can effortlessly run tests to validate the CRUD operations for pet owners, pet management, and veterinarian information retrieval.

## Features

- **Comprehensive Test Coverage**: Our suite covers a wide range of functionalities, including creating, updating, and deleting pet owners, managing pets and visits, and retrieving veterinarian details.
- **Extensive Automated Reporting**: Utilizing ExtentReports, our test suite generates detailed and visually appealing reports after each test run. Easily track test outcomes, view screenshots of failures, and gain insights into test execution.
- **Configuration Flexibility**: Seamlessly toggle headless execution for front-end tests to suit your testing environment. Additionally, easily manage Docker container setup based on your preferences.
- **Easy Setup and Execution**: With clear instructions provided in this README, setting up and running tests is a breeze. You'll be up and running in no time, ensuring the PetClinic application's reliability with minimal effort.

## Technology Stack

This project is built on a robust stack of modern testing technologies and methodologies:

- **Java**: The core programming language for writing test scripts.
- **TestNG**: Used for organizing and running the test cases.
- **Selenium**: Automates web browser interactions for web testing.
- **RestAssured**: Simplifies API testing and validation.
- **Extent Reports**: Provides comprehensive test reporting with detailed insights.
- **GitHub Actions**: Facilitates continuous integration and continuous deployment (CI/CD).
- **Arrange-Act-Assert Pattern**: Ensures clear and maintainable test structure.
- **Page Object Model (POM)**: Enhances the maintainability and reusability of web tests.
- **Request-Response Pattern**: Ensures clear and maintainable API test structure.
- **Docker-Compose**: Manages the application lifecycle with ease.
- **Headless Toggle**: Allows for optional headless execution of web tests.
- **JSON Test Data Management**: Simplifies test data handling.
- **TODO: Selenium-Grid**: Plans to distribute tests across multiple machines for faster execution.

## Getting Started

Follow these simple steps to set up and run the PetClinic Automated Testing Suite:

### Prerequisites

Ensure you have the following prerequisites installed on your system:

- Docker
- GitHub account

### Setup

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/BackbaseRecruitment/brentsingh.git
   ```

2. Navigate to the project directory:

   ```bash
   cd brentsingh
   ```

3. Run the PetClinic application using Docker:

   ```bash
   docker-compose up
   ```

### Running Tests

To run the automated tests, execute the following commands:

- For API tests:

  ```bash
  mvn test -Dtest=OwnersEndpointAPITest
  ```

- For Web tests:

  ```bash
  mvn test -Dtest=EditOwnerDetailsTest
  ```

### Accessing Reports

After running the tests, access the generated reports located in the `test-output/extent-reports` directory. Open the HTML report in your preferred browser to view detailed test results.

Here's an example of the Extent report:

![Extent Report Screenshot](/documents/screenshot/extent-report-screenshot.png)

## Code Snippets

### API Test Example

Here's an example of an API test that validates the creation of a new pet owner:

```java
@Test
public void testCreateOwner() {
    Owner owner = new Owner();
    owner.setFirstName("John");
    owner.setLastName("Doe");
    owner.setAddress("123 Main St");
    owner.setCity("Springfield");
    owner.setTelephone("1234567890");

    Response response = given()
        .contentType(ContentType.JSON)
        .body(owner)
        .when()
        .post("/owners")
        .then()
        .statusCode(201)
        .extract()
        .response();

    Owner createdOwner = response.getBody().as(Owner.class);
    assertEquals("John", createdOwner.getFirstName());
    assertEquals("Doe", createdOwner.getLastName());
}
```

### Web Test Example

Here's an example of a web test that validates the editing of an existing pet owner's details:

```java
@Test
public void testEditOwnerDetails() {
    driver.get("http://localhost:8080/owners/1/edit");
    
    WebElement firstNameField = driver.findElement(By.id("firstName"));
    firstNameField.clear();
    firstNameField.sendKeys("Jane");
    
    WebElement lastNameField = driver.findElement(By.id("lastName"));
    lastNameField.clear();
    lastNameField.sendKeys("Smith");
    
    WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
    submitButton.click();
    
    WebElement ownerName = driver.findElement(By.xpath("//table[@id='owners']/tbody/tr[1]/td/b"));
    assertEquals("Jane Smith", ownerName.getText());
}
```

## Documentation
A series of documents related to the PetClinic test automation can be found here:
```bash
/Documents
```
## Contributing

We welcome contributions from the community to enhance and expand the PetClinic Automated Testing Suite. If you'd like to contribute, please fork the repository, make your changes, and submit a pull request.

## Feedback

If you encounter any issues, have suggestions for improvements, or would like to provide feedback, please don't hesitate to open an issue on GitHub. We value your input and strive to make the PetClinic Automated Testing Suite even better with your help.

---
