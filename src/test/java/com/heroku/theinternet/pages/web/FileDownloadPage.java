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
        return allDownloadWebElements.stream()
                .filter(w -> w.getText().equals(WebElementText))
                .findFirst()
                .orElse(null);
    }

    private long getSizeOfFileAtURL(String downloadURL) {

        try {
            return IOUtils.toByteArray(RestAssured.get(downloadURL).asInputStream()).length;
        } catch (IOException e) {
            logger.error("Failed to get size of the file: " + downloadURL, e);
        } catch (NullPointerException e) {
            return 0;  // most likely an empty file
        }

        return -1;
    }
}
