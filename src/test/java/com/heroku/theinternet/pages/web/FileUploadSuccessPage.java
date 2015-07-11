package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class FileUploadSuccessPage extends BasePage<FileUploadSuccessPage> {

    @Visible
    @FindBy(css = "div#uploaded-files")
    private WebElement uploadedFiles;

    public String getUploadedFiles() {
        return uploadedFiles.getText();
    }
}
