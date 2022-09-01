package api.signup;

import org.realworld.api.datamodel.requests.LoginUser;
import org.realworld.api.datamodel.requests.LoginUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.api.utils.ModelValidatorUtils;
import org.realworld.api.utils.ResponseUtils;
import org.realworld.api.services.RetrofitFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

public class SignInTest {

    private ApiService apiService;

    @BeforeClass
    public void setUp() {
        apiService = RetrofitFactory.getInstance().createService(ApiService.class);
    }

    @Test
    public void loginWithValidEmailPassword() {
        String userEmail = "qa2@gmail.com";
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(userEmail, "12345"));
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(apiService.loginUser(userForSignIn));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), userEmail);
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
        return new Object[][]{
                {"qa00&^%&50@gmail.com", "12345", "Email not found: [qa00&^%&50@gmail.com]"},
                {"", "12345", "Email must be specified."},
                {"qa2@gmail.com", "", "Password must be specified."},
                {"qa2@gmail.com", "12346", "Wrong password."},
        };
    }
}
