package org.realworld.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BaseUIObject {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseUIObject(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebDriver getDriver() {
        return driver;
    }

    @Step("Checking the element: [0] is active step...")
    public static boolean isActive(WebElement element) {
        return element.getAttribute("class").contains("active");
    }

    protected WebElement waitVisibilityAndGet(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Step("Wait visibility of element step...")
    protected void waitVisibility(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Step("Get element from list by text: [1] step...")
    public static WebElement getElementByText(List<WebElement> webElements, String text) {
        for (WebElement element : webElements) {
            if (element.getText().contains(text)) {
                return element;
            }
        }
        return null;
    }
}
