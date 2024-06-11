package com.petClinic.web.tests;

import com.petClinic.web.helpers.NavigationHelper;
import com.petClinic.web.pages.owners.OwnersInformationPage;
import com.petClinic.web.pages.owners.OwnersPage;
import com.petClinic.web.pages.veterinarianVisits.VisitsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class AddNewVeterinaryVisitTest extends BaseTest {

    private NavigationHelper navigationHelper;
    private OwnersPage ownersPage;
    private OwnersInformationPage ownersInformationPage;

    @BeforeMethod
    public void setup(Method method) {
        test = extent.createTest(method.getName());
    }

    @Test
    public void testAddNewVeterinaryVisit() {
        // Arrange
        logger.info("Starting test: Add New Veterinary Visit");

        ownersPage = new OwnersPage(driver);
        ownersInformationPage = new OwnersInformationPage(driver);
        navigationHelper = new NavigationHelper(driver);

        navigationHelper.navigateToOwnersPage();
        logger.info("Navigated to owners page");

        ownersPage.clickOnOwnerListItem1();
        logger.info("Clicked on first owner");

        // Act
        VisitsPage visitsPage = ownersInformationPage.clickAddVisitButton();
        visitsPage.setDate("30-06-2024");
        visitsPage.setDescription("Regular check-up");
        visitsPage.addNewVisit();

        String actualVisitDescription = ownersInformationPage.getVisitDescription();
        logger.info("Visit description retrieved: {}", actualVisitDescription);

        // Assert
        assert actualVisitDescription.equals("Regular check-up") : "Visit description does not match!";
        logger.info("Test passed: Visit description matches");
    }
}
