package com.petClinic.web.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.petClinic.commonUtils.DockerUtils;
import com.petClinic.web.utils.BrowserFactory;
import com.petClinic.commonUtils.ConfigManager;
import com.petClinic.web.utils.ClearScreenshotsFolder;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest extends DockerUtils {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    static {
        // Initialize ExtentReports in a static block
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/extent-reports/extent-report.html");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", ConfigManager.getProperty("browser"));

        ClearScreenshotsFolder.clearScreenshotsFolder();
    }

    protected static ExtentTest test;

    @BeforeSuite
    public void setUpSuite() {
        setUpDocker();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional String browser) {
        if (browser == null) {
            browser = ConfigManager.getProperty("browser");
        }
        logger.info("Setting up the browser: {}", browser);
        boolean isHeadless = Boolean.parseBoolean(ConfigManager.getProperty("headless"));
        driver = BrowserFactory.getDriver(browser, isHeadless);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed is " + result.getName());
            test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
            logger.error("Test Case Failed: {}", result.getName(), result.getThrowable());

            String screenshotPath = captureScreenshot(result.getName());
            if (screenshotPath != null) {
                try {
                    test.fail("Screenshot of failure",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    logger.error("Failed to attach screenshot to report: {}", e.getMessage());
                }
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed is " + result.getName());
            logger.info("Test Case Passed: {}", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case Skipped is " + result.getName());
            logger.warn("Test Case Skipped: {}", result.getName());
        }
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }

    private String captureScreenshot(String testName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destFilePath = System.getProperty("user.dir") + "test-output/screenshots/" + testName + "_" + timestamp + ".png";
            File destFile = new File(destFilePath);

            FileUtils.copyFile(srcFile, destFile);
            logger.info("Screenshot captured: {}", destFile.getAbsolutePath());

            return destFilePath;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        logger.info("Tearing down the test suite...");
        tearDownDocker();
        extent.flush();
    }
}
