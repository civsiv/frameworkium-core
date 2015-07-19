package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class IFramePage extends BasePage<IFramePage> {

    @Visible
    @FindBy(css = "iframe#mce_0_ifr")
    private WebElement wysiwygIFrame;

    @Visible
    @FindBy(css = "div[aria-label='Bold'] button")
    private WebElement boldButton;

    // This is within the iframe so while it'll be visible
    // when the page loads, it WILL NOT be visible within the
    // driver context until we switchTo it - see below
    @FindBy(id = "tinymce")
    private WebElement wysiwygTextBox;

    public void clearTextInEditor() {

        // Switch driver context to the iframe
        driver.switchTo().frame(wysiwygIFrame);

        // Perform actions within the iframe
        wysiwygTextBox.clear();

        // Switch back to the main page before returning
        driver.switchTo().parentFrame();
    }

    public void enterTextInEditor(String text) {

        // Switch driver context to the iframe
        driver.switchTo().frame(wysiwygIFrame);

        // Perform actions within the iframe
        wysiwygTextBox.sendKeys(text);

        // Switch back to the main page before returning
        driver.switchTo().parentFrame();
    }

    public String getTextInEditor() {

        // Switch driver context to the iframe
        driver.switchTo().frame(wysiwygIFrame);

        // Perform actions within the iframe
        String text = wysiwygTextBox.getText();

        // Switch back to the main page before returning
        driver.switchTo().parentFrame();
        return text;
    }

    public void enterBoldTextInEditor(String text) {

        // Click bold button (As it's NOT in the iframe)
        boldButton.click();
        // Switch driver context to the iframe
        driver.switchTo().frame(wysiwygIFrame);

        // Perform actions within the iframe
        wysiwygTextBox.sendKeys(text);

        // Switch back to the main page before returning
        driver.switchTo().parentFrame();
        // Unclick bold button (still not in the iframe!)
        boldButton.click();
    }

}
