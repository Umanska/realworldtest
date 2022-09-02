package org.realworld.ui.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumUtils {

    public static WebElement getElementWithWait(WebDriver driver, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public static boolean isActive(WebElement element) {
        return element.getAttribute("class").contains("active");
    }

    public static Boolean isPresent(WebDriver driver, String xpath) {
        WebElement element = getElementWithWait(driver, xpath);
        return element != null;
    }

    public static WebElement getElementByText(List<WebElement> webElements, String text) {
        for (WebElement element : webElements) {
            if (element.getText().contains(text)) {
                return element;
            }
        }
        return null;
    }
}
