package com.petClinic.api.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.petClinic.commonUtils.ConfigManager;
import com.petClinic.commonUtils.DockerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;

public class BaseApiTest extends DockerUtils {

    protected static ExtentReports extent;
    protected static final Logger logger = LogManager.getLogger(BaseApiTest.class);
    protected ExtentTest test;

    static {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/extent-reports/extent-report.html");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("API Automation Test Report");
        sparkReporter.config().setReportName("API Test Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", ConfigManager.getProperty("env"));
        logger.info("Extent report initialized.");
    }

    @BeforeSuite
    public void setUpSuite() {
        setUpDocker();
    }

    @AfterSuite
    public void tearDownSuite() {
        logger.info("Tearing down the test suite...");
        tearDownDocker();
    }

    @BeforeMethod
    public void setUpTest(Method method) {
        test = extent.createTest(method.getName());
        logger.info("Starting test: {}", method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed is " + result.getName());
            test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
            logger.error("Test Case Failed: {}", result.getName(), result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed is " + result.getName());
            logger.info("Test Case Passed: {}", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case Skipped is " + result.getName());
            logger.warn("Test Case Skipped: {}", result.getName());
        }

        // Flush the report after each test
        extent.flush();
    }

}
