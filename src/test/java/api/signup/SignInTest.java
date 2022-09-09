package api.signup;

import org.realworld.api.datamodel.requests.LoginUser;
import org.realworld.api.datamodel.requests.LoginUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.api.services.RetrofitFactory;
import org.realworld.api.utils.ModelValidatorUtils;
import org.realworld.api.utils.ResponseUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

import static org.realworld.api.Constants.PASSWORD;
import static org.realworld.api.Constants.USERNAME_OF_EXISTED_USER;

public class SignInTest {

    private ApiService apiService;

    @BeforeClass
    public void setUp() {
        apiService = RetrofitFactory.getInstance().createService(ApiService.class);
    }

    @Test
    public void loginWithValidEmailPassword() {
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(USERNAME_OF_EXISTED_USER, PASSWORD));
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(apiService.loginUser(userForSignIn));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), USERNAME_OF_EXISTED_USER);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @Test(dataProvider = "negativeCases")
    public void loginNegativeCases(String userEmail, String userPassword, String expectedErrorMessage) {
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(userEmail, userPassword));
        ResponseWrapper<String> parsedErrorResponse = ResponseUtils.executeAndParseError(apiService.loginUser(userForSignIn));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedErrorResponse.getStatusCode(), 422);
        sAssert.assertEquals(parsedErrorResponse.getResponseBody(), expectedErrorMessage);
        sAssert.assertAll();
    }

    @DataProvider(name = "negativeCases")
    public Object[][] createData() {
        String emptyField = "";
        String usernameSpecialChars = "qa00&^%&50@gmail.com";
        return new Object[][]{
                {usernameSpecialChars, PASSWORD, "Email not found: [" + usernameSpecialChars + "]"},
                {emptyField, PASSWORD, "Email must be specified."},
                {USERNAME_OF_EXISTED_USER, emptyField, "Password must be specified."},
                {USERNAME_OF_EXISTED_USER, "12346", "Wrong password."},
        };
    }
}
