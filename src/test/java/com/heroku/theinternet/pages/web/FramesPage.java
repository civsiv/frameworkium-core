package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class FramesPage extends BasePage<FramesPage> {

    @Visible
    @FindBy(linkText = "iFrame")
    private WebElement iFrameLink;

    public IFramePage clickIFrameLink() {
        iFrameLink.click();
        return PageFactory.newInstance(IFramePage.class);
    }

}
