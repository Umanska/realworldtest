package org.realworld.ui.pages;

import org.openqa.selenium.WebDriver;
import org.realworld.ui.BaseUIObject;

public abstract class BasePage extends BaseUIObject {

    protected BasePage(WebDriver driver) {
        super(driver);
        waitUntilLoaded();
    }

    protected abstract void waitUntilLoaded();
}
