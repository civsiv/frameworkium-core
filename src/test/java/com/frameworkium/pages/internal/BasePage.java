package com.frameworkium.pages.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.frameworkium.capture.model.Command;
import com.frameworkium.config.SystemProperty;
import com.frameworkium.tests.internal.BaseTest;
import com.google.inject.Inject;

public abstract class BasePage<T extends BasePage<T>> {

    @Inject
    protected WebDriver driver;

    @Inject
    protected WebDriverWait wait;

    protected final Logger logger = LogManager.getLogger(this);

    /**
     * @return Returns the current page object.
     * Useful for e.g. MyPage.get().then().doSomething();
     */
    @SuppressWarnings("unchecked")
    public T then() {
        return (T) this;
    }

    /**
     * @return Returns the current page object.
     * Useful for e.g. MyPage.get().then().with().aComponent().clickHome();
     */
    @SuppressWarnings("unchecked")
    public T with() {
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T get() {

        // TODO: make initElements work for helper classes e.g. Select, Table
        PageFactory.initElements(driver, this);
        try {
            waitForExpectedVisibleElements(this);
            try{
                if (SystemProperty.CAPTURE_URL.isSpecified()) {
                    BaseTest.getCapture().takeAndSendScreenshot(
                        new Command("load", null, this.getClass().getName()), driver, null);
                }
            } catch (Exception e){
                logger.error("Error logging page load, but loaded successfully");
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error("Error while waiting for page to load", e);
        }
        return (T) this;
    }

    public T get(String url) {
        driver.get(url);
        return get();
    }

    private void waitForExpectedVisibleElements(Object pageObject)
            throws IllegalArgumentException, IllegalAccessException
    {
        waitForExpectedVisibleElements(pageObject, StringUtils.EMPTY);
    }
    
    private void waitForExpectedVisibleElements(Object pageObject, String visibleGroupName)
            throws IllegalArgumentException, IllegalAccessException
    {
        for (Field field : pageObject.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof Visible) {
                    // If the group name matches, then check for visibility
                    String currentVisibleGroupName = ((Visible) annotation).value();
                    if (currentVisibleGroupName.equalsIgnoreCase(visibleGroupName)) {
                        field.setAccessible(true);
                        checkForVisibility(field.get(pageObject));
                    }
                }
            }
        }
    }

    private void checkForVisibility(Object obj) throws IllegalAccessException {
        // This handles Lists of WebElements e.g. List<WebElement>
        if (obj instanceof List) {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElements((List<WebElement>) obj));
            } catch (StaleElementReferenceException serex) {
                logger.info("Caught StaleElementReferenceException");
                tryToEnsureWeHaveUnloadedOldPageAndNewPageIsReady();
                wait.until(ExpectedConditions.visibilityOfAllElements((List<WebElement>) obj));
            } catch (ClassCastException ccex) {
                logger.debug("Caught ClassCastException - will try to get the first object in the List instead");
                obj = ((List<Object>) obj).get(0);
                waitForObjectToBeVisible(obj);
            }
        }
        // Otherwise, it's a single object - WebElement
        else {
            waitForObjectToBeVisible(obj);
        }
    }

    private void waitForObjectToBeVisible(Object obj) 
        throws IllegalArgumentException, IllegalAccessException 
    {
        WebElement element;
        if (obj == null) {
            throw new IllegalArgumentException("WebElement is null");
        } else if (obj instanceof WebElement) {
            element = (WebElement) obj;
        } else {
            throw new IllegalArgumentException(
                    "Only elements of type WebElement or List<WebElement> " +
                            "are supported by @Visible.");
        }

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (StaleElementReferenceException serex) {
            logger.info("Caught StaleElementReferenceException");
            // Retries the wait when an element is looked up before
            // the previous page has unloaded or before document ready
            tryToEnsureWeHaveUnloadedOldPageAndNewPageIsReady();
            // If the below throws a StaleElementReferenceException try
            // increasing the sleep time or count in:
            // tryToEnsureWeHaveUnloadedOldPageAndNewPageIsReady()
            wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    /**
     * Will wait up to ~10 seconds for the document to be ready.
     */
    private void tryToEnsureWeHaveUnloadedOldPageAndNewPageIsReady() {
        int notReadyCount = 0;
        int readyCount = 0;
        while (notReadyCount < 20 && readyCount < 3) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            boolean docReady = (boolean) executeJS(
                    "return document.readyState == 'complete'");
            if (docReady) {
                readyCount++;
                logger.info(String.format(
                        "Document ready. Not ready %d times, ready %d times.",
                        notReadyCount, readyCount));
            } else {
                notReadyCount++;
                logger.warn(String.format(
                        "Document not ready. Not ready %d times, ready %d times.",
                        notReadyCount, readyCount));
            }
        }
    }

    /**
     * @param javascript the Javascript to execute on the current page
     * @return Returns an Object returned by the Javascript provided
     */
    protected Object executeJS(String javascript) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javascript);
    }

    /** @return Returns the title of the web page */
    public String getTitle() {
        return driver.getTitle();
    }

    /** @return Returns the source code of the current page */
    public String getSource() {
        return driver.getPageSource();
    }
}
