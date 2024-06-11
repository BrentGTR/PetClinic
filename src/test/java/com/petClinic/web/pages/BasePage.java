package com.petClinic.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        logger.info("Waiting for element to be visible: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        logger.info("Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void customClick(WebElement element) {
        logger.info("Clicking on element: {}", element);
        waitForElementToBeClickable(element).click();
    }

    protected void type(WebElement element, String text) {
        logger.info("Typing into element: {} text: {}", element, text);
        WebElement inputElement = waitForElementToBeVisible(element);
        inputElement.clear();
        inputElement.sendKeys(text);
    }

    protected String getText(WebElement element) {
        logger.info("Getting text from element: {}", element);
        return waitForElementToBeVisible(element).getText();
    }
}
