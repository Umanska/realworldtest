package api.signup;

import org.realworld.api.datamodel.requests.NewUser;
import org.realworld.api.datamodel.requests.NewUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.api.services.RetrofitFactory;
import org.realworld.api.utils.ModelValidatorUtils;
import org.realworld.api.utils.ResponseUtils;
import org.realworld.utils.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

import static org.realworld.api.Constants.*;

public class SignUpTest {

    private ApiService apiService;
    private NewUserRequest newValidUser;

    @BeforeClass
    public void setUp() {
        apiService = RetrofitFactory.getInstance().createService(ApiService.class);
    }

    @Test
    public void signUpUserWithValidData() {
        String userName = StringUtils.getUniqueUsername(USER_NAME_PREFIX_API);
        newValidUser = new NewUserRequest(
                generateNewUser(userName, userName + EMAIL_SUFFIX, PASSWORD));
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(apiService.signUpUser(newValidUser));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        softAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), newValidUser.getUser().getUsername());
        softAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), newValidUser.getUser().getEmail());
        softAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        softAssert.assertAll();
    }


    @Test(dataProvider = "negativeCases", dependsOnMethods = "signUpUserWithValidData")
    public void signUpUserWithEmptyRequiredField(String username, String email, String password, String errorMessage) {
        NewUserRequest userRequest = new NewUserRequest(generateNewUser(username, email, password));
        ResponseWrapper<String> parsedResponse = ResponseUtils.executeAndParseError(apiService.signUpUser(userRequest));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(parsedResponse.getStatusCode(), 422);
        softAssert.assertEquals(parsedResponse.getResponseBody(), errorMessage);
        softAssert.assertAll();
    }

    private NewUser generateNewUser(String username, String email, String password) {
        return new NewUser.Builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

    @DataProvider(name = "negativeCases")
    public Object[][] createData() {
        String emptyField = "";
        String username = StringUtils.getUniqueUsername(USER_NAME_PREFIX_API);
        return new Object[][]{
                {username, username + EMAIL_SUFFIX, emptyField, "Password must be specified."},
                {emptyField, username + EMAIL_SUFFIX, PASSWORD, "Username must be specified."},
                {username, emptyField, PASSWORD, "Email must be specified."},
                {username, newValidUser.getUser().getEmail(), PASSWORD, "Email already taken: [" + newValidUser.getUser().getEmail() + "]"},
                {newValidUser.getUser().getUsername(), username + EMAIL_SUFFIX, PASSWORD, "Username already taken: [" + newValidUser.getUser().getUsername() + "]"},
                {username, StringUtils.getRandomString(256), PASSWORD, "Email must be less than 256 characters"},
        };
    }
}
