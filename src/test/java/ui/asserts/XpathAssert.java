package ui.asserts;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.IAssert;

import java.time.Duration;

public class XpathAssert implements IAssert<String> {

    private String elementXpath;
    private WebDriver webDriver;

    public XpathAssert(WebDriver webDriver, String xpathExpression) {
        this.webDriver = webDriver;
        this.elementXpath = xpathExpression;
    }

    @Override
    public String getMessage() {
        return "Element by elementXpath: '" + getExpected() + "' is missing";
    }

    @Override
    public void doAssert() {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
        } catch (TimeoutException e) {
            org.testng.Assert.fail(getMessage());
        }
    }

    @Override
    public String getActual() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getExpected() {
        return elementXpath;
    }
}
