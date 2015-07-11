package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class SecureFileDownloadPage extends BasePage<SecureFileDownloadPage> {

    @Visible
    @FindBy(css = "div.example h3")
    private WebElement heading;

    @Visible
    @FindBy(css = "div.example a:first-of-type")
    private WebElement firstDownloadWebElement;

    public String getHeadingText() {
        return heading.getText();
    }
}
