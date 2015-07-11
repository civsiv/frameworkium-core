package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class JavaScriptAlertsPage extends BasePage<JavaScriptAlertsPage> {

    @Visible
    @FindBy(css = "button[onclick='jsAlert()']")
    private WebElement jsAlertButton;

    @Visible
    @FindBy(css = "button[onclick='jsConfirm()']")
    private WebElement jsConfirmButton;

    @Visible
    @FindBy(css = "button[onclick='jsPrompt()']")
    private WebElement jsPromptButton;

    @FindBy(css = "p#result")
    private WebElement resultArea;

    public JavaScriptAlertsPage clickAlertButtonAndAccept() {
        jsAlertButton.click();

        driver.switchTo().alert().accept();

        wait.until(ExpectedConditions.visibilityOf(resultArea));

        return this;
    }

    public JavaScriptAlertsPage clickAlertButtonAndDismiss() {
        jsAlertButton.click();

        driver.switchTo().alert().dismiss();

        wait.until(ExpectedConditions.visibilityOf(resultArea));

        return this;
    }

    public JavaScriptAlertsPage clickConfirmButtonAndAccept() {
        jsConfirmButton.click();

        driver.switchTo().alert().accept();

        wait.until(ExpectedConditions.visibilityOf(resultArea));

        return this;
    }

    public JavaScriptAlertsPage clickConfirmButtonAndDismiss() {
        jsConfirmButton.click();

        driver.switchTo().alert().dismiss();

        wait.until(ExpectedConditions.visibilityOf(resultArea));

        return this;
    }

    public JavaScriptAlertsPage clickPromptButtonAndEnterPrompt(String textToEnter) {
        jsPromptButton.click();

        driver.switchTo().alert().sendKeys(textToEnter);
        driver.switchTo().alert().accept();

        return this;
    }

    public String getResultText() {
        return resultArea.getText();
    }
}
