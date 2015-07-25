package com.frameworkium.config;

import static com.frameworkium.config.SystemProperty.BROWSER_VERSION;
import static com.frameworkium.config.SystemProperty.DEVICE;
import static com.frameworkium.config.SystemProperty.GRID_URL;
import static com.frameworkium.config.SystemProperty.PLATFORM;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.frameworkium.capture.ScreenshotCapture;
import com.frameworkium.listeners.CaptureListener;
import com.frameworkium.listeners.EventListener;

public enum DriverType implements DriverSetup {

    FIREFOX {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            return DesiredCapabilities.firefox();
        }

        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new FirefoxDriver(capabilities);
        }
    },
    CHROME {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);

            // Use Chrome's built in device emulators
            // Specify browser=chrome, but also provide device name to use chrome's emulator
            if (DEVICE.isSpecified()) {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", DEVICE.getValue());

                Map<String, Object> chromeOptions = new HashMap<>();
                chromeOptions.put("mobileEmulation", mobileEmulation);

                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            }
            return capabilities;
        }

        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new ChromeDriver(capabilities);
        }
    },
    IE {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
            return capabilities;
        }

        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new InternetExplorerDriver(capabilities);
        }
    },
    PHANTOMJS {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setCapability("takesScreenshot", true);
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                    new String[]{"--webdriver-loglevel=NONE"});
            return capabilities;
        }

        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new PhantomJSDriver(capabilities);
        }
    },
    BROWSER {
        @Override
        public DesiredCapabilities getDesiredCapabilities() {
            return new DesiredCapabilities();
        }

        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities) {
            throw new IllegalArgumentException(
                    "seleniumGridURL or sauceUser and sauceKey must be specified when running on Android");
        }
    };

    public static final DriverType defaultDriverType = FIREFOX;
    public static final boolean useRemoteWebDriver = GRID_URL.isSpecified();

    private final static Logger logger = LogManager.getLogger(DriverType.class);

    public static DriverType determineEffectiveDriverType() {
        DriverType driverType = defaultDriverType;
        try {
            driverType = valueOf(SystemProperty.BROWSER.getValue().toUpperCase());
        } catch (IllegalArgumentException ignored) {
            logger.warn("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            logger.warn("No driver specified, defaulting to '" + driverType + "'...");
        }

        return driverType;
    }

    public static boolean isMobile() {
        return "ios".equalsIgnoreCase(PLATFORM.getValue()) ||
                "android".equalsIgnoreCase(PLATFORM.getValue());
    }

    public WebDriver instantiateWebDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = getDesiredCapabilities();

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(GRID_URL.getValue());

            // Otherwise, try setting just platform and browser version
            if (PLATFORM.isSpecified()) {
                desiredCapabilities.setPlatform(Platform.valueOf(PLATFORM.getValue().toUpperCase()));
            }

            if (BROWSER_VERSION.isSpecified()) {
                desiredCapabilities.setVersion(BROWSER_VERSION.getValue());
            }

            // Return non-mobile remote driver
            return new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        }
        // Otherwise, return the right kind of local driver
        else {
            if (isMobile()) {
                throw new IllegalArgumentException(
                        "seleniumGridURL, sauce, or browserstack must be specified when running via Appium.");
            } else {
                return getWebDriverObject(desiredCapabilities);
            }
        }
    }

    public WebDriverWrapper instantiate() {
        logger.info("Current Browser Selection: " + this);

        DesiredCapabilities caps = getDesiredCapabilities();
        logger.info("Caps: " + caps.toString());

        try {
            WebDriver driver = instantiateWebDriver();
            WebDriverWrapper eventFiringWD = new WebDriverWrapper(driver);
            eventFiringWD.register(new EventListener());
            if (ScreenshotCapture.isRequired()) {
                eventFiringWD.register(new CaptureListener());
            }
            return eventFiringWD;
        } catch (MalformedURLException urlIsInvalid) {
            throw new RuntimeException(urlIsInvalid);
        }
    }

}
