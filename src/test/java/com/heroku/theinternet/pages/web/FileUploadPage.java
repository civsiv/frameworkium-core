package com.heroku.theinternet.pages.web;

import java.io.File;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class FileUploadPage extends BasePage<FileUploadPage> {

    @Visible
    @FindBy(css = "input#file-upload")
    private WebElement chooseFilesButton;

    @Visible
    @FindBy(css = "input#file-submit")
    private WebElement uploadButton;

    public FileUploadSuccessPage uploadFile(File filename) {
        chooseFilesButton.sendKeys(filename.getAbsolutePath());
        uploadButton.click();
        return PageFactory.newInstance(FileUploadSuccessPage.class);
    }
}
