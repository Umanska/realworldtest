package ui.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static org.realworld.api.Constants.UI_BASE_URL;

public class UITestBase {

    private static ThreadLocal<WebDriver> dr = new ThreadLocal<>();
    private WebDriverManager wdm;

    @Parameters({"browser"})
    @BeforeSuite
    @Description("Creating WebDriverManager instance..")
    protected void setupSuite(@Optional String browser) {
        if (browser == null) browser = "chrome";
        wdm = WebDriverManager.getInstance(browser);
    }

    @BeforeMethod
    @Description("Creating driver..")
    protected void setup() {
        dr.set(wdm.create());
        getDriver().navigate().to(UI_BASE_URL);
        getDriver().manage().window().fullscreen();
    }

    @AfterMethod
    @Description("Quiting driver..")
    protected void teardown() {
        if (getDriver() != null) {
            wdm.quit(getDriver());
            dr.remove();
        }
    }

    @AfterSuite
    @Description("Quiting WebDriverManager instance..")
    protected void teardownSuite() {
        wdm.quit();
    }

    protected WebDriver getDriver() {
        return dr.get();
    }
}
