//Provides high-level navigation methods that can be reused across tests.
package com.petClinic.web.helpers;

import com.petClinic.web.pages.HomePage;
import com.petClinic.web.pages.owners.OwnersPage;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {

    private WebDriver driver;

    public NavigationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public OwnersPage navigateToOwnersPage() {
        HomePage homePage = new HomePage(driver);
        homePage.openPage();
        return homePage.goToOwnersPage();
    }
}
