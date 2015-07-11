package com.heroku.theinternet.pages.web;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class DynamicLoadingExamplePage extends BasePage<DynamicLoadingExamplePage> {

    @Visible
    @FindBy(css = "#start button")
    private WebElement startButton;

    @FindBy(id = "finish")
    private WebElement hiddenElement;

    public DynamicLoadingExamplePage clickStart() {
        startButton.click();
        wait.until(ExpectedConditions.visibilityOf(hiddenElement));
        return this;
    }

    public DynamicLoadingExamplePage waitForElementToBeDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(hiddenElement));
        return this;
    }

    public boolean isElementDisplayed() {
        return hiddenElement.isDisplayed();
    }

    public boolean isElementPresent() {
        try {
            hiddenElement.getTagName();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
