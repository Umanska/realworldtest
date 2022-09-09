package ui.user;

import org.openqa.selenium.WebElement;
import org.realworld.ui.pages.HomePage;
import org.realworld.ui.pages.SignUpPage;
import org.realworld.ui.utils.SeleniumUtils;
import org.realworld.utils.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.base.UITestBase;
import ui.asserts.WebSoftAssert;

import java.util.List;

import static org.realworld.api.Constants.*;

public class SignUpTest extends UITestBase {

    @Test
    public void signUpUserWithValidData() {
        SignUpPage signUpPage = loadSignUpPage();
        String userName = StringUtils.getUniqueUsername(USER_NAME_PREFIX_UI);
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

        WebSoftAssert sAssert = new WebSoftAssert(getDriver());
        sAssert.assertElementExist(elementXpath);
        sAssert.assertCurrentUrl(SignUpPage.URL_PATH);
        sAssert.assertAll();
    }

    @Test(dataProvider = "signUpUserInvalidEmail")
    public void signUpUserInvalidEmail(String invalidEmail, String errorMessage) {
        SignUpPage signUpPage = loadSignUpPage();
        signUpPage.signUp(StringUtils.getUniqueUsername(USER_NAME_PREFIX_UI), invalidEmail, PASSWORD);
        String msg = signUpPage.getEmailField().getAttribute("validationMessage");

        WebSoftAssert sAssert = new WebSoftAssert(getDriver());
        sAssert.assertEquals(msg, errorMessage);
        sAssert.assertCurrentUrl(SignUpPage.URL_PATH);
        sAssert.assertAll();
    }

    @Test(dataProvider = "signUpWithExistedUser")
    public void signUpWithExistedUser(String username, String email, String errorMessage) {
        SignUpPage signUpPage = loadSignUpPage();
        signUpPage.signUp(username, email, PASSWORD);
        String elementXpath = "//li[normalize-space(text())='" + errorMessage + "']";

        WebSoftAssert sAssert = new WebSoftAssert(getDriver());
        sAssert.assertElementExist(elementXpath);
        sAssert.assertCurrentUrl(SignUpPage.URL_PATH);
        sAssert.assertAll();
    }

    private SignUpPage loadSignUpPage() {
        HomePage homePage = new HomePage(getDriver());
        homePage.signUpTabFromNavMenu().click();
        return new SignUpPage(getDriver());
    }

    @DataProvider(name = "signUpUserEmptyRequiredFields")
    public Object[][] createUserDataWithEmptyField() {
        String emptyField = "";
        String username = StringUtils.getUniqueUsername(USER_NAME_PREFIX_UI);
        return new Object[][]{
                {username, emptyField, PASSWORD, "email can't be blank"},
                {emptyField, username + EMAIL_SUFFIX, PASSWORD, "username can't be blank"},
                {username, username + EMAIL_SUFFIX, emptyField, "password can't be blank"},
        };
    }

    @DataProvider(name = "signUpUserInvalidEmail")
    public Object[][] createInvalidEmailField() {
        String username = StringUtils.getUniqueUsername(USER_NAME_PREFIX_UI);
        return new Object[][]{
                {username, "Please include an '@' in the email address. '" + username + "' is missing an '@'."},
                {username + "@", "Please enter a part following '@'. '" + username + "@' is incomplete."},
        };
    }

    @DataProvider(name = "signUpWithExistedUser")
    public Object[][] createData() {
        String uniqueUsername = StringUtils.getUniqueUsername(USER_NAME_PREFIX_UI);
        return new Object[][]{
                {USERNAME_OF_EXISTED_USER, uniqueUsername + EMAIL_SUFFIX, "username has already been taken"},
                {uniqueUsername, USERNAME_OF_EXISTED_USER + EMAIL_SUFFIX, "email has already been taken"},
        };
    }
}
