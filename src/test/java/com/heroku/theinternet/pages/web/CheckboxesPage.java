package com.heroku.theinternet.pages.web;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class CheckboxesPage extends BasePage<CheckboxesPage> {

    @Visible
    @FindBy(css = "form input[type='checkbox']")
    private List<WebElement> allCheckboxes;

    public CheckboxesPage checkAllCheckboxes() {

        allCheckboxes.stream()
                .filter(checkbox -> !checkbox.isSelected())
                .forEach(WebElement::click);

        return this;
    }

    public List<Boolean> getAllCheckboxCheckedStatus() {

        return allCheckboxes.stream()
                .map(WebElement::isSelected)
                .collect(Collectors.toList());
    }
}
