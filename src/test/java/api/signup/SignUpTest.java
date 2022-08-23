package api.signup;

import org.apache.commons.lang3.RandomStringUtils;
import org.realworld.api.datamodel.requests.NewUser;
import org.realworld.api.datamodel.requests.NewUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.utils.ResponseUtils;
import org.realworld.utils.RetrofitFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        String userName = getUniqueUserName();
        newValidUser = new NewUserRequest(
                generateNewUser(userName, userName + EMAIL_SUFFIX, PASSWORD));
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(apiService.signUpUser(newValidUser));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        softAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), newValidUser.getUser().getUsername());
        softAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), newValidUser.getUser().getEmail());
        softAssert.assertAll();
    }


    @Test(dataProvider = "emptyFieldCases", dependsOnMethods = "signUpUserWithValidData")
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

    private String getUniqueUserName() {
        return USER_NAME_PREFIX + System.currentTimeMillis();
    }

    @DataProvider(name = "emptyFieldCases")
    public Object[][] createData() {
        return new Object[][]{
                {getUniqueUserName(), getUniqueUserName() + EMAIL_SUFFIX, "", "Password must be specified."},
                {"", getUniqueUserName() + EMAIL_SUFFIX, PASSWORD, "Username must be specified."},
                {getUniqueUserName(), "", PASSWORD, "Email must be specified."},
                {getUniqueUserName(), newValidUser.getUser().getEmail(), PASSWORD, "Email already taken: [" + newValidUser.getUser().getEmail() + "]"},
                {newValidUser.getUser().getUsername(), getUniqueUserName() + EMAIL_SUFFIX, PASSWORD, "Username already taken: [" + newValidUser.getUser().getUsername() + "]"},
                {getUniqueUserName(), RandomStringUtils.randomAlphanumeric(256), PASSWORD, "Email must be less than 256 characters"},
        };
    }
}
