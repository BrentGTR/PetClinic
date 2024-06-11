package com.petClinic.web.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ClearScreenshotsFolder {
    public static void clearScreenshotsFolder() {
        try {
            File screenshotsDir = new File("screenshots");
            if (screenshotsDir.exists()) {
                FileUtils.cleanDirectory(screenshotsDir); // Apache Commons IO
            }
        } catch (IOException e) {
            System.err.println("Failed to clear screenshots folder: " + e.getMessage());
        }
    }
}
