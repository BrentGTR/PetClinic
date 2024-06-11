package com.petClinic.web.pages.owners;

import com.petClinic.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditOwnersPage extends BasePage {

    @FindBy(xpath = "//label[text()='First name']/following-sibling::input")
    WebElement firstNameField;

    @FindBy(xpath = "//label[text()='Last name']/following-sibling::input")
    WebElement lastNameField;

    @FindBy(xpath = "//label[text()='Address']/following-sibling::input")
    WebElement addressField;

    @FindBy(xpath = "//label[text()='City']/following-sibling::input")
    WebElement cityField;

    @FindBy(xpath = "//label[text()='Telephone']/following-sibling::input")
    WebElement telephoneField;

    @FindBy(xpath = "//button[text()='Submit']")
    WebElement submitButton;

    public EditOwnersPage(WebDriver driver) {
        super(driver);
    }

    // Methods to interact with the fields
    public void setFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    public void setLastName(String lastName) {
        type(lastNameField, lastName);
    }

    public void setAddress(String address) {
        type(addressField, address);
    }

    public void setCity(String city) {
        type(cityField, city);
    }

    public void setTelephone(String telephone) {
        type(telephoneField, telephone);
    }

    public void submitForm() {
        customClick(submitButton);
    }
}
