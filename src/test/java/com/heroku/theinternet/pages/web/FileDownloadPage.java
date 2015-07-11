package com.heroku.theinternet.pages.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.jayway.restassured.RestAssured;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class FileDownloadPage extends BasePage<FileDownloadPage> {

    @Visible
    @FindBy(css = "div.example a")
    private List<WebElement> allDownloadWebElements;

    @Visible
    @FindBy(css = "div.example a:first-of-type")
    private WebElement firstDownloadWebElement;

    public long getSizeOfFirstFile() {
        return getSizeOfFileAtURL(firstDownloadWebElement.getAttribute("href"));
    }

    public List<String> getDownloadableFileLinkNames() {

        return allDownloadWebElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public long getSizeOfFile(String WebElementText) {
        return getSizeOfFileAtURL(getURLOfFile(WebElementText));
    }

    public String getURLOfFile(String WebElementText) {
        WebElement link = findWebElementByText(WebElementText);
        return (link == null) ? "" : link.getAttribute("href");
    }

    private WebElement findWebElementByText(String WebElementText) {

        for (WebElement WebElement : allDownloadWebElements) {
            if (WebElement.getText().equals(WebElementText)) {
                return WebElement;
            }
        }
        return null;
    }

    private long getSizeOfFileAtURL(String downloadURL) {

        InputStream inputStream = RestAssured.get(downloadURL).asInputStream();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream, byteArrayOutputStream);
        } catch (IOException e) {
            logger.error("Failed to get size of the file: " + downloadURL, e);
        }
        IOUtils.closeQuietly(byteArrayOutputStream);
        IOUtils.closeQuietly(inputStream);

        return byteArrayOutputStream.size();
    }
}
