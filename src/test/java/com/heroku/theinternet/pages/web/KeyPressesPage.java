package com.heroku.theinternet.pages.web;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class KeyPressesPage extends BasePage<KeyPressesPage> {

    @Visible
    @FindBy(css = "div.example")
    private WebElement container;

    @FindBy(css = "p#result")
    private WebElement result;

    public KeyPressesPage enterKeyPress(Keys key) {

        // Press a key
        (new Actions(driver)).sendKeys(key).perform();

        // We're still on this page, so return this
        return this;
    }

    public String getResultText() {
        return result.getText();
    }

}
