package com.frameworkium.tests.internal;

import static com.frameworkium.config.DriverType.determineEffectiveDriverType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.frameworkium.annotations.Issue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.frameworkium.capture.ScreenshotCapture;
import com.frameworkium.config.DriverType;
import com.frameworkium.config.WebDriverWrapper;
import com.frameworkium.listeners.CaptureListener;
import com.frameworkium.listeners.MethodInterceptor;
import com.frameworkium.listeners.ResultLoggerListener;
import com.frameworkium.listeners.ScreenshotListener;
import com.frameworkium.listeners.TestListener;

@Listeners({CaptureListener.class, ScreenshotListener.class, MethodInterceptor.class,
        TestListener.class, ResultLoggerListener.class})
public abstract class BaseTest {

    private static List<WebDriverWrapper> activeDrivers = Collections
            .synchronizedList(new ArrayList<WebDriverWrapper>());
    private static ThreadLocal<WebDriverWrapper> driver;
    private static DriverType desiredDriverType = determineEffectiveDriverType();
    private static ThreadLocal<Boolean> requiresReset;
    private static ThreadLocal<ScreenshotCapture> capture;

    private static Logger logger = LogManager.getLogger(BaseTest.class);

    public static String userAgent;

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driver = new ThreadLocal<WebDriverWrapper>() {
            @Override
            protected WebDriverWrapper initialValue() {
                WebDriverWrapper webDriver = desiredDriverType.instantiate();
                activeDrivers.add(webDriver);
                return webDriver;
            }
        };
        requiresReset = new ThreadLocal<Boolean>() {
            @Override
            protected Boolean initialValue() {
                return false;
            }
        };
        capture = new ThreadLocal<ScreenshotCapture>() {
            @Override
            protected ScreenshotCapture initialValue() {
                return null;
            }
        };
    }

    public static WebDriverWrapper getDriver() {
        return driver.get();
    }

    @BeforeMethod(alwaysRun = true)
    public static void clearSession() {
        // Reset browser
        if (requiresReset.get()) {
            try {
                getDriver().manage().deleteAllCookies();
            } catch (SessionNotFoundException e) {
                logger.error("Session quit unexpectedly.", e);
            }
        } else {
            requiresReset.set(true);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public static void setUserAgent() {
        userAgent = getUserAgent();
    }

    @BeforeMethod(alwaysRun = true)
    public void initialiseNewScreenshotCapture(Method testMethod) {
        if (ScreenshotCapture.isRequired()) {
            String testID = "n/a";
            try {
                testID = testMethod.getAnnotation(Issue.class).value();
            } catch (NullPointerException e) {}
            
            capture.set(new ScreenshotCapture(testID, driver.get()));
        }
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObject() {
        for (WebDriver driver : activeDrivers) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.warn("Session quit unexpectedly.", e);
            }
        }
    }

    public static String getUserAgent() {
        String ua;
        try {
            ua = (String) getDriver().executeScript("return navigator.userAgent;");
        } catch (Exception e) {
            ua = "Unable to fetch UserAgent";
        }
        logger.debug("User agent is: '" + ua + "'");
        return ua;
    }

    public static ScreenshotCapture getCapture() {
        return capture.get();
    }
}
