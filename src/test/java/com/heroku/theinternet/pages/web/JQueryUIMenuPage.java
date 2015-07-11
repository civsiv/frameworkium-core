package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class JQueryUIMenuPage extends BasePage<JQueryUIMenuPage> {

    @Visible
    @FindBy(linkText = "Enabled")
    private WebElement enabledMenuItem;

    @FindBy(linkText = "Back to JQuery UI")
    private WebElement backToJQueryUIMenuItem;

    @FindBy(linkText = "Downloads")
    private WebElement downloadsMenuItem;

    @FindBy(linkText = "Excel")
    private WebElement excelFileMenuItem;

    public JQueryUIPage clickBackToUI() {

        // Move mouse over the first figure to make caption visible
        (new Actions(driver)).moveToElement(enabledMenuItem).perform();

        backToJQueryUIMenuItem.click();

        // returns us a new page
        return PageFactory.newInstance(JQueryUIPage.class);
    }

    public String getExcelFileURLAsString() {

        // Move mouse over the first figure to make caption visible
        (new Actions(driver)).moveToElement(enabledMenuItem).perform();

        // Move mouse over the first figure to make caption visible
        (new Actions(driver)).moveToElement(downloadsMenuItem).perform();

        // Return link url (href) from the now-visible item
        return excelFileMenuItem.getAttribute("href");
    }

}
