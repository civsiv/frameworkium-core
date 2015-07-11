package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class FormAuthenticationSuccessPage extends BasePage<FormAuthenticationSuccessPage> {

    @Visible
    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    public void logout() {
        logoutButton.click();
    }
}
