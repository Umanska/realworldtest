package ui.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class WebSoftAssert extends SoftAssert {

    private WebDriver webDriver;

    public WebSoftAssert(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void assertElementExist(String xpathExpression) {
        doAssert(new XpathAssert(webDriver, xpathExpression));
    }

    /**
     * Check current url equal to expected url
     *
     * @param expectedUrlPath part of the url path to identify page, best option is
     *                        using URL_PATH constant from a page class.
     *                        Example:
     *                        assertCurrentUrl(signUpPage.URL_PATH) or
     *                        assertCurrentUrl("/#/register")
     */
    public void assertCurrentUrl(String expectedUrlPath) {
        doAssert(new UrlAssert(webDriver, expectedUrlPath));
    }
}
