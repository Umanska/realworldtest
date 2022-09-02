package ui.user;

import org.openqa.selenium.WebElement;
import org.realworld.ui.pages.HomePage;
import org.realworld.ui.pages.SignUpPage;
import org.realworld.ui.utils.SeleniumUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.base.UITestBase;

import java.util.List;

import static org.realworld.api.Constants.*;

public class SignUpTest extends UITestBase {

    @Test
    public void signUpUserWithValidData() {
        SignUpPage signUpPage = loadSignUpPage();
        String userName = USER_NAME_PREFIX_UI + System.nanoTime();
        HomePage homePage = new HomePage(signUpPage.signUp(userName, userName + EMAIL_SUFFIX, PASSWORD));
        List<WebElement> menuItems = homePage.itemsLoggedInNavMenu();

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertNotNull(SeleniumUtils.getElementByText(menuItems, userName),
                userName + " tab is missing in nav menu");
        sAssert.assertTrue(SeleniumUtils.isActive(homePage.homePageTabFromNavMenu()));
        sAssert.assertTrue(SeleniumUtils.isActive(homePage.yourFeedTab()));
        sAssert.assertAll();
    }

    @Test(dataProvider = "signUpUserEmptyRequiredFields")
    public void signUpUserEmptyRequiredFields(String username, String email, String password, String errorMessage) {
        SignUpPage signUpPage = loadSignUpPage();
        signUpPage.signUp(username, email, password);
        String elementXpath = "//li[normalize-space(text())=\"" + errorMessage + "\"]";
        SoftAssert sAssert = new SoftAssert();
        sAssert.assertTrue(SeleniumUtils.isPresent(this.getDriver(), elementXpath),
                "Element by elementXpath:" + elementXpath + "is missing");
        sAssert.assertTrue(signUpPage.isSignUpPage());
        sAssert.assertAll();
    }

    @Test(dataProvider = "signUpUserInvalidEmail")
    public void signUpUserInvalidEmail(String email, String errorMessage) {
        SignUpPage signUpPage = loadSignUpPage();
        signUpPage.signUp(getUniqueUserName(), email, PASSWORD);
        String msg = signUpPage.getEmailField().getAttribute("validationMessage");

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(msg, errorMessage);
        sAssert.assertTrue(signUpPage.isSignUpPage());
        sAssert.assertAll();
    }

    @Test(dataProvider = "signUpWithExistedEmailUsername")
    public void signUpWithExistedEmailUsername(String username, String email, String errorMessage) {
        SignUpPage signUpPage = loadSignUpPage();
        signUpPage.signUp(username, email, PASSWORD);
        String elementXpath = "//li[normalize-space(text())='" + errorMessage + "']";

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertTrue(SeleniumUtils.isPresent(this.getDriver(), elementXpath),
                "Element by elementXpath:" + elementXpath + "is missing");
        sAssert.assertTrue(signUpPage.isSignUpPage());
        sAssert.assertAll();
    }

    private SignUpPage loadSignUpPage() {
        HomePage homePage = new HomePage(this.getDriver());
        homePage.signUpTabFromNavMenu().click();
        return new SignUpPage(this.getDriver());
    }

    private String getUniqueUserName() {
        return USER_NAME_PREFIX_UI + System.currentTimeMillis();
    }

    @DataProvider(name = "signUpUserEmptyRequiredFields")
    public Object[][] createUserDataWithEmptyField() {
        return new Object[][]{
                {getUniqueUserName(), "", PASSWORD, "email can't be blank"},
                {"", getUniqueUserName() + EMAIL_SUFFIX, PASSWORD, "username can't be blank"},
                {getUniqueUserName(), getUniqueUserName() + EMAIL_SUFFIX, "", "password can't be blank"},
        };
    }

    @DataProvider(name = "signUpUserInvalidEmail")
    public Object[][] createInvalidEmailField() {
        String username = getUniqueUserName();
        return new Object[][]{
                {username, "Please include an '@' in the email address. '" + username + "' is missing an '@'."},
                {username + "@", "Please enter a part following '@'. '" + username + "@' is incomplete."},
        };
    }

    @DataProvider(name = "signUpWithExistedEmailUsername")
    public Object[][] createData() {
        String usernameOfExistedUser = "qa123";
        return new Object[][]{
                {usernameOfExistedUser, getUniqueUserName() + EMAIL_SUFFIX, "username has already been taken"},
                {getUniqueUserName(), usernameOfExistedUser + EMAIL_SUFFIX, "email has already been taken"},
        };
    }
}
