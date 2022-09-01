package ui.base;

import org.openqa.selenium.WebDriver;
import org.realworld.ui.services.WebDriverService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static org.realworld.api.Constants.UI_BASE_URL;

public class UITestBase {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    protected void setup(@Optional String browser) {
        if (browser == null) browser = "chrome";
        driver = new WebDriverService().createDriver(browser);
        driver.navigate().to(UI_BASE_URL);
        driver.manage().window().fullscreen();
    }

    protected WebDriver getDriver() {
        return this.driver;
    }

    @AfterMethod
    protected void teardown() {
        getDriver().quit();
    }
}
