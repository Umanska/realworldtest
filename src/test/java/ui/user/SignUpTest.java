package ui.user;

import org.openqa.selenium.WebElement;
import org.realworld.ui.pages.HomePage;
import org.realworld.ui.pages.SignUpPage;
import org.realworld.ui.utils.SeleniumUtils;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.base.UITestBase;

import java.util.List;

import static org.realworld.api.Constants.*;

public class SignUpTest extends UITestBase {

    @Test
    public void signUpUserWithValidData() {
        HomePage homePage = new HomePage(this.getDriver());
        homePage.signUpTabFromNavMenu().click();
        SignUpPage signUpPage = new SignUpPage(this.getDriver());
        String userName = USER_NAME_PREFIX_UI + System.nanoTime();
        homePage = new HomePage(signUpPage.signUp(userName, userName + EMAIL_SUFFIX, PASSWORD));
        List<WebElement> menuItems = homePage.itemsLoggedInNavMenu();

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertTrue(SeleniumUtils.getElementWithText(menuItems, userName) != null,
                userName + " tab is missing in nav menu");
        sAssert.assertTrue(SeleniumUtils.isActive(homePage.homePageTabFromNavMenu()));
        sAssert.assertTrue(SeleniumUtils.isActive(homePage.yourFeedTab()));
        sAssert.assertAll();
    }
}
