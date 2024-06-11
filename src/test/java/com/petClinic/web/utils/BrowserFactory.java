package com.petClinic.web.utils;

import com.petClinic.utils.ConfigManager;
import com.petClinic.utils.DockerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserFactory {

    private static final String HEALTH_CHECK_URL = "http://localhost:4444/status";
    private static final Logger logger = LogManager.getLogger(BrowserFactory.class);

    public static WebDriver getDriver(String browserType, boolean isHeadless) {
        WebDriver driver = null;
        boolean useSeleniumGrid = ConfigManager.getBooleanProperty("useSeleniumGrid");

        if (useSeleniumGrid) {
            DockerUtils.waitForContainerToBeReady(HEALTH_CHECK_URL);
            logger.info("Using Selenium Grid for browser: {}", browserType);
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                switch (browserType.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (isHeadless) {
                            chromeOptions.addArguments("--headless");
                            chromeOptions.addArguments("--disable-gpu");
                            chromeOptions.addArguments("--window-size=1920,1080");
                            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                        }
                        capabilities.setBrowserName("chrome");
                        break;
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (isHeadless) {
                            firefoxOptions.addArguments("--headless");
                            firefoxOptions.addArguments("--disable-gpu");
                            firefoxOptions.addArguments("--window-size=1920,1080");
                            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                        }
                        capabilities.setBrowserName("firefox");
                        break;
                    default:
                        throw new IllegalArgumentException("Browser type not supported: " + browserType);
                }
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);
            } catch (MalformedURLException e) {
                logger.error("Failed to create driver", e);
            }
        } else {
            switch (browserType.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless");
                        chromeOptions.addArguments("--disable-gpu");
                        chromeOptions.addArguments("--window-size=1920,1080");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        firefoxOptions.addArguments("--headless");
                        firefoxOptions.addArguments("--disable-gpu");
                        firefoxOptions.addArguments("--window-size=1920,1080");
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    driver.manage().window().maximize();
                    break;
                default:
                    throw new IllegalArgumentException("Browser type not supported: " + browserType);
            }
        }

        return driver;
    }
}
