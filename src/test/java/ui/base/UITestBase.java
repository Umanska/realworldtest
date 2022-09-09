package ui.base;

import io.github.bonigarcia.wdm.WebDriverManager;
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
    protected void setupSuite(@Optional String browser) {
        if (browser == null) browser = "chrome";
        wdm = WebDriverManager.getInstance(browser);
        System.out.println("Created wdm instance..");
    }

    @BeforeMethod
    protected void setup() {
        dr.set(wdm.create());
        getDriver().navigate().to(UI_BASE_URL);
        getDriver().manage().window().fullscreen();
        System.out.println("Created driver..");
    }

    @AfterMethod
    protected void teardown() {
        if (getDriver() != null) {
            wdm.quit(getDriver());
            dr.remove();
        }
        System.out.println("Quited driver..");
    }

    @AfterSuite
    protected void teardownSuite() {
        wdm.quit();
        System.out.println("Quited wdm instance..");
    }

    protected WebDriver getDriver() {
        return dr.get();
    }
}
