package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class BasicAuthSuccessPage extends BasePage<BasicAuthSuccessPage> {

    @Visible
    @FindBy(css = "div.example h3")
    private WebElement headerText;

    public String getHeaderText() {
        return headerText.getText();
    }
}
