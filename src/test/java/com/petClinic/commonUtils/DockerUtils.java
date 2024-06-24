package com.petClinic.commonUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DockerUtils {
    private static final Logger logger = LogManager.getLogger(DockerUtils.class);
    private static final int MAX_WAIT_TIME = 120; // Maximum wait time in seconds
    private static boolean manageDocker;

    public static void runCommand(String command) {
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

    public static void waitForContainerToBeReady(String HEALTH_CHECK_URL) {
        int waitTime = 0;
        boolean isReady = false;

        while (waitTime < MAX_WAIT_TIME) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(HEALTH_CHECK_URL).openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    logger.info("Container is ready.");
                    isReady = true;
                    break;
                } else {
                    logger.info("Waiting for container to be ready... Response code: {}", responseCode);
                }
            } catch (IOException e) {
                logger.info("Waiting for container to be ready... Exception: {}", e.getMessage());
            }

            try {
                Thread.sleep(1000); // Sleep for 1 second before retrying
            } catch (InterruptedException e) {
                logger.error("Exception while waiting for container: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }

            waitTime++;
        }

        if (!isReady) {
            logger.error("Container was not ready within the maximum wait time of {} seconds", MAX_WAIT_TIME);
            throw new RuntimeException("Container not ready within the maximum wait time.");
        }
    }

    public static void setUpDocker() {
        logger.info("Setting up the test suite...");
        manageDocker = Boolean.parseBoolean(ConfigManager.getProperty("manageDocker"));

        if (manageDocker) {
            DockerUtils.runCommand("docker-compose up -d");
            logger.info("Starting docker...");
            DockerUtils.waitForContainerToBeReady(ConfigManager.getProperty("healthCheckUrl"));
        } else {
            logger.info("Skipping docker setup.");
        }
    }

    public static void tearDownDocker() {
        manageDocker = Boolean.parseBoolean(ConfigManager.getProperty("manageDocker"));
        if (manageDocker) {
            DockerUtils.runCommand("docker-compose down");
            logger.info("... Stopped docker...");
        }
    }
}
