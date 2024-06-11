package com.petClinic.web.pages.owners;

import com.petClinic.web.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OwnersPage extends BasePage {

    @FindBy(xpath = "//table[@class='table table-striped']//tbody/tr[1]/td[1]/a")
    private WebElement ownerListItem1Link;
    @FindBy(xpath = "//table[@class='table table-striped']//tbody/tr[2]/td[1]/a")
    private WebElement ownerListItem2Link;

    public OwnersPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnOwnerListItem1() {
        customClick(ownerListItem1Link);
    }

    public void clickOnOwnerListItem2() {
        customClick(ownerListItem2Link);
    }
}
