package com.petClinic.web.tests;

import com.petClinic.web.helpers.NavigationHelper;
import com.petClinic.web.pages.owners.OwnersInformationPage;
import com.petClinic.web.pages.owners.OwnersPage;
import com.petClinic.web.utils.TestDataLoader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;

public class ViewOwnerDetailsTest extends BaseTest {

    private NavigationHelper navigationHelper;
    private OwnersPage ownersPage;
    private OwnersInformationPage ownersInformationPage;

    @BeforeMethod
    public void setup(Method method) {
        test = extent.createTest(method.getName());
    }

    @Test
    public void testViewOwnerDetails() {
        // Arrange
        logger.info("Starting test: View Owner Details");
        navigationHelper = new NavigationHelper(driver);
        ownersPage = new OwnersPage(driver);
        TestDataLoader testDataLoader = new TestDataLoader();
        Map<String, String> ownerData = testDataLoader.getOwnerData();
        assert ownerData != null;
        String expectedOwnerName = ownerData.get("OwnerName");

        // Act
        navigationHelper.navigateToOwnersPage();

        ownersInformationPage = new OwnersInformationPage(driver);
        logger.info("Navigated to owners page");

        ownersPage.clickOnOwnerListItem1();
        logger.info("Clicked on first owner");

        String actualOwnerName = ownersInformationPage.getOwnerName();
        logger.info("Owner name retrieved: {}", actualOwnerName);

        // Assert
        assert actualOwnerName.equals(expectedOwnerName) : "Owner name does not match!";
        logger.info("Test passed: Owner name matches");
    }
}
