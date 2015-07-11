package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class HoversPage extends BasePage<HoversPage> {

    @Visible
    @FindBy(css = "div.figure:nth-of-type(1)")
    private WebElement firstFigure;

    @FindBy(css = "div.figure:nth-of-type(1) div.figcaption")
    private WebElement firstFigureCaption;

    public String getFirstFigureCaption() {

        // Move mouse over the first figure to make caption visible
        (new Actions(driver)).moveToElement(firstFigure).perform();

        // Return text from the now-visible caption
        return firstFigureCaption.getText();
    }
}
