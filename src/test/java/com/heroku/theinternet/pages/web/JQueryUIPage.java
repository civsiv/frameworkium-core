package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class JQueryUIPage extends BasePage<JQueryUIPage> {

    @Visible
    @FindBy(linkText = "Menu")
    private WebElement menuWebElement;

    public JQueryUIMenuPage clickMenuLink() {
        menuWebElement.click();

        return PageFactory.newInstance(JQueryUIMenuPage.class);
    }
}
