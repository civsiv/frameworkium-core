package com.heroku.theinternet.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class CheckboxesPage extends BasePage<CheckboxesPage> {

    @Visible
    @FindBy(css = "form input[type='checkbox']")
    private List<WebElement> allCheckboxes;

    public CheckboxesPage checkAllCheckboxes() {

        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }

        return this;
    }

    public List<Boolean> getAllCheckboxCheckedStatus() {

        List<Boolean> checkedStates = new ArrayList<Boolean>();

        for (WebElement checkbox : allCheckboxes) {
            checkedStates.add(checkbox.isSelected());
        }

        return checkedStates;
    }
}
