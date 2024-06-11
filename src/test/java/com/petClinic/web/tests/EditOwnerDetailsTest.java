package com.petClinic.web.tests;

import com.petClinic.web.helpers.NavigationHelper;
import com.petClinic.web.pages.owners.EditOwnersPage;
import com.petClinic.web.pages.owners.OwnersInformationPage;
import com.petClinic.web.pages.owners.OwnersPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class EditOwnerDetailsTest extends BaseTest {

    private NavigationHelper navigationHelper;
    private OwnersPage ownersPage;
    private OwnersInformationPage ownersInformationPage;
    private EditOwnersPage editOwnersPage;

    @BeforeMethod
    public void setup(Method method) {
        test = extent.createTest(method.getName());
    }

    @Test
    public void testEditOwnerDetails() {
        // Arrange
        logger.info("Starting test: Edit Owner Details");

        ownersPage = new OwnersPage(driver);
        ownersInformationPage = new OwnersInformationPage(driver);
        navigationHelper = new NavigationHelper(driver);
        editOwnersPage = new EditOwnersPage(driver);

        navigationHelper.navigateToOwnersPage();
        logger.info("Navigated to owners page");

        ownersPage.clickOnOwnerListItem2();
        logger.info("Clicked on second owner");

        // Act
        ownersInformationPage.clickEditButton();
        editOwnersPage.setFirstName("UpdatedFirstName");
        editOwnersPage.setLastName("UpdatedLastName");
        editOwnersPage.setAddress("123 Updated Address");
        editOwnersPage.setCity("Updated City");
        editOwnersPage.setTelephone("1234567890");
        editOwnersPage.submitForm();

        String actualOwnerName = ownersInformationPage.getOwnerName();
        logger.info("Owner name retrieved: {}", actualOwnerName);

        // Assert
        assert actualOwnerName.equals("UpdatedFirstName UpdatedLastName") : "Owner name does not match!";
        logger.info("Test passed: Owner name matches");
    }
}
