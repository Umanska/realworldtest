package org.realworld.ui.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class WebDriverService {

    public enum Browsers {CHROME, FIREFOX, SAFARI}

    public WebDriverService() {
    }

    public WebDriver createDriver(String browser) {

        if (browser.equalsIgnoreCase(Browsers.CHROME.toString())) {
            return WebDriverManager.chromedriver().create();
        }
        if (browser.equalsIgnoreCase(Browsers.FIREFOX.toString())) {
            return WebDriverManager.firefoxdriver().create();
        }
        if (browser.equalsIgnoreCase(Browsers.SAFARI.toString())) {
            return WebDriverManager.safaridriver().create();
        }
        throw new IllegalStateException("Provided browser name is not supported");
    }
}
