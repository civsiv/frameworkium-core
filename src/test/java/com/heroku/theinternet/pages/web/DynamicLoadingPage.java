package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class DynamicLoadingPage extends BasePage<DynamicLoadingPage> {

    @Visible
    @FindBy(linkText = "Example 1: Element on page that is hidden")
    private WebElement example1Link;

    @FindBy(linkText = "Example 2: Element rendered after the fact")
    private WebElement example2Link;

    public DynamicLoadingExamplePage clickExample1() {
        example1Link.click();
        return PageFactory.newInstance(DynamicLoadingExamplePage.class);
    }

    public DynamicLoadingExamplePage clickExample2() {
        example2Link.click();
        return PageFactory.newInstance(DynamicLoadingExamplePage.class);
    }
}
