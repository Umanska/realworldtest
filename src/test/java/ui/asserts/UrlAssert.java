package ui.asserts;

import org.openqa.selenium.WebDriver;
import org.realworld.ui.pages.HomePage;
import org.testng.asserts.IAssert;

import static org.realworld.api.Constants.UI_BASE_URL;

public class UrlAssert implements IAssert<String> {

    private String expectedUrlSubPath;
    private WebDriver webDriver;

    public UrlAssert(WebDriver webDriver, String expectedUrlSubPath) {
        this.webDriver = webDriver;
        this.expectedUrlSubPath = expectedUrlSubPath;
    }

    @Override
    public String getMessage() {
        return "Current url is not equal to expected url:" + System.lineSeparator()
                + "Current: " + getActual() + System.lineSeparator()
                + "Expected: " + getExpected();
    }

    @Override
    public void doAssert() {
        if (getActual().compareTo(getExpected()) != 0) {
            org.testng.Assert.fail(getMessage());
        }
    }

    @Override
    public String getActual() {
        return webDriver.getCurrentUrl();
    }

    @Override
    public String getExpected() {
        return UI_BASE_URL + expectedUrlSubPath;
    }
}
