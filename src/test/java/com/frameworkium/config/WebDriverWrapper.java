package com.frameworkium.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class WebDriverWrapper extends EventFiringWebDriver {

    private WebDriver driver;

    public WebDriverWrapper(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return driver;
    }

    public RemoteWebDriver getWrappedRemoteWebDriver() {
        WebDriver wd = getWrappedDriver();
        if (wd instanceof RemoteWebDriver) {
            return (RemoteWebDriver) wd;
        } else {
            throw new RuntimeException(wd + " is not an instance of RemoteWebDriver");
        }
    }
}
