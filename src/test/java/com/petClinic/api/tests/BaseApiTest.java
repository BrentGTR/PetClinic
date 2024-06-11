package com.petClinic.api.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.petClinic.utils.ConfigManager;
import com.petClinic.utils.DockerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class BaseApiTest {

    protected static ExtentReports extent;
    protected static final Logger logger = LogManager.getLogger(BaseApiTest.class);
    protected ExtentTest test;
    private static boolean manageDocker;
    private static final String HEALTH_CHECK_URL = "http://localhost:8080/#!/owners";

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
        manageDocker = Boolean.parseBoolean(ConfigManager.getProperty("manageDocker"));

        if (manageDocker) {
            DockerUtils.runCommand("docker-compose up -d");
            logger.info("Starting docker...");
            DockerUtils.waitForContainerToBeReady(HEALTH_CHECK_URL);
        } else {
            logger.info("Skipping docker setup.");
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (manageDocker) {
            DockerUtils.runCommand("docker-compose down");
            logger.info("... Stopped docker.");
        }
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

    private void runCommand(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("sh", "-c", command);
        }

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                StringBuilder errorMsg = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    errorMsg.append(line).append("\n");
                }
                logger.error("Command '{}' failed with error:\n{}", command, errorMsg.toString());
            } else {
                logger.info("Command '{}' executed successfully.", command);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Exception occurred while executing command '{}':", command, e);
        }
    }
}
