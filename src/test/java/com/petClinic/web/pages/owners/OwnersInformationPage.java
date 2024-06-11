package com.petClinic.web.pages.owners;

import com.petClinic.web.pages.BasePage;
import com.petClinic.web.pages.veterinarianVisits.VisitsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OwnersInformationPage extends BasePage {

    @FindBy(xpath = "//a[text()='Edit Owner']")
    public WebElement editOwnerButton;

    @FindBy(xpath = "//th[text()='Name']/following-sibling::td/b")
    public WebElement ownerName;

    @FindBy(xpath = "//th[text()='Address']/following-sibling::td")
    public WebElement ownerAddress;

    @FindBy(xpath = "//th[text()='City']/following-sibling::td")
    public WebElement ownerCity;

    @FindBy(xpath = "//th[text()='Telephone']/following-sibling::td")
    public WebElement ownerTelephone;

    @FindBy(xpath = "//a[text()='Add New Pet']")
    public WebElement addNewPetButton;

    @FindBy(xpath = "//dt[text()='Name']/following-sibling::dd/a")
    public WebElement petName;

    @FindBy(xpath = "//a[text()='Edit Pet']")
    public WebElement editPetButton;

    @FindBy(xpath = "//dt[text()='Birth Date']/following-sibling::dd")
    public WebElement petBirthDate;

    @FindBy(xpath = "//dt[text()='Type']/following-sibling::dd")
    public WebElement petType;

    @FindBy(xpath = "//a[text()='Add Visit']")
    public WebElement addVisitButton;

    @FindBy(xpath = "//th[text()='Description']/following::tbody/tr[1]/td[2]")
    public WebElement visitDescription;

    public OwnersInformationPage(WebDriver driver) {
        super(driver);
    }


    // Methods to interact with the fields
    public String getOwnerName() {
        waitForElementToBeVisible(ownerName);
        return ownerName.getText();
    }

    public void clickEditButton() {
        customClick(editOwnerButton);
    }

    public VisitsPage clickAddVisitButton() {
        customClick(addVisitButton);
        return new VisitsPage(driver);
    }

    public String getVisitDescription() {
        return getText(visitDescription);
    }
}
