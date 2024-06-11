//Contains methods to navigate to different sections of the application.
package com.petClinic.web.pages;

import com.petClinic.web.pages.owners.OwnersPage;
import com.petClinic.utils.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[contains(@class, 'dropdown-toggle')]")
    private WebElement ownersMenuLink;

   @FindBy(xpath = "//a[contains(@ui-sref, 'owners')]")
    private WebElement ownersLink;

    @FindBy(xpath = "//a[contains(@ui-sref, 'vets')]")
    private WebElement veterinariansLink;

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public OwnersPage goToOwnersPage() {
        customClick(ownersMenuLink);
        customClick(ownersLink);
        return new OwnersPage(driver);
    }

    public HomePage goToHomePage() {
        homeLink.click();
        return this;
    }

    public void openPage() {
        driver.get(ConfigManager.getProperty("baseUrl"));
    }
}
