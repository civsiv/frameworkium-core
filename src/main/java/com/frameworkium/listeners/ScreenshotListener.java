package com.frameworkium.listeners;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.frameworkium.config.WebDriverWrapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import ru.yandex.qatools.allure.annotations.Attachment;

import com.frameworkium.config.SystemProperty;
import com.frameworkium.tests.internal.BaseTest;

import javax.imageio.ImageIO;

public class ScreenshotListener extends TestListenerAdapter {

    private static Logger logger = LogManager.getLogger(ScreenshotListener.class);

    private boolean createFile(File screenshot) {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                try {
                    fileCreated = screenshot.createNewFile();
                } catch (IOException e) {
                    logger.error("Error creating screenshot", e);
                }
            }
        }

        return fileCreated;
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] writeScreenshotToFile(File screenshot) {
        try {
            BufferedImage image = new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())
            );
            ImageIO.write(image, "jpg", screenshot);
        } catch (AWTException e) {
            logger.error("Unable to take screenshot: " + e.toString());
        } catch (IOException e) {
            logger.error("Unable to write screenshot to file:" + screenshot.getAbsolutePath());
        }
        return null;
    }

    private void takeScreenshot(String testName) {
        // Take a local screenshot if capture is not enabled
        if (!SystemProperty.CAPTURE_URL.isSpecified()) {
            try {
                String screenshotDirectory = System.getProperty("screenshotDirectory");
                if (null == screenshotDirectory) {
                    screenshotDirectory = "screenshots";
                }
                String absolutePath =
                        screenshotDirectory + File.separator + System.currentTimeMillis() + "_" + testName + ".png";
                File screenshot = new File(absolutePath);
                if (createFile(screenshot)) {
                    WebDriverWrapper driver = BaseTest.getDriver();
                    writeScreenshotToFile(screenshot);
                    logger.info("Written screenshot to " + absolutePath);
                } else {
                    logger.error("Unable to create " + absolutePath);
                }
            } catch (Exception e) {
                logger.error("Unable to take screenshot - " + e);
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult failingTest) {
        takeScreenshot(failingTest.getName());
    }

    @Override
    public void onTestSkipped(ITestResult skippedTest) {
        takeScreenshot(skippedTest.getName());
    }
}
