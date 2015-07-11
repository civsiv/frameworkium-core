package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage extends BasePage<DropdownPage> {

    @Visible
    @FindBy(id = "dropdown")
    private WebElement dropdown;

    public DropdownPage selectFromDropdown(String option) {
        getSelect().selectByVisibleText(option);
        return this;
    }

    public String getSelectedOptionText() {
        return getSelect().getFirstSelectedOption().getText();
    }

    private Select getSelect() {
        return new Select(dropdown);
    }
}
