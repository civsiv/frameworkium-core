package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class FormAuthenticationPage extends BasePage<FormAuthenticationPage> {

    @Visible
    @FindBy(css = "input#username")
    private WebElement usernameField;

    @Visible
    @FindBy(css = "input#password")
    private WebElement passwordField;

    @Visible
    @FindBy(xpath = "//button[contains(.,'Login')]")
    private WebElement loginButton;

    public FormAuthenticationSuccessPage validLogin(String username, String password) {

        usernameField.clear();
        usernameField.sendKeys(username);

        passwordField.clear();
        passwordField.sendKeys(password);

        loginButton.click();

        return PageFactory.newInstance(FormAuthenticationSuccessPage.class);
    }
}
