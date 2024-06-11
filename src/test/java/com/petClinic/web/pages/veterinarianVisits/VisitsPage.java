package com.petClinic.web.pages.veterinarianVisits;

import com.petClinic.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VisitsPage extends BasePage {

    @FindBy(xpath = "//label[text()='Date']/following-sibling::input")
    WebElement dateField;

    @FindBy(xpath = "//label[text()='Description']/following-sibling::textarea")
    WebElement descriptionField;

    @FindBy(xpath = "//button[text()='Add New Visit']")
    WebElement addNewVisitButton;

    public VisitsPage(WebDriver driver) {
        super(driver);
    }

    // Methods to interact with the fields
    public void setDate(String date) {
        type(dateField, date);
    }

    public void setDescription(String description) {
        descriptionField.clear();
        type(descriptionField, description);
    }

    public void addNewVisit() {
        customClick(addNewVisitButton);
    }
}
