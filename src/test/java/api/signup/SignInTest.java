package api.signup;

import org.realworld.api.datamodel.requests.LoginUser;
import org.realworld.api.datamodel.requests.LoginUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.utils.ResponseHandler;
import org.realworld.utils.RetrofitFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SignInTest {

    private ApiService apiService;

    @BeforeClass
    public void SetUp() {
        apiService = RetrofitFactory.getInstance().createService(ApiService.class);
    }

    @Test
    public void loginWithValidEmailPassword() {
        String userEmail = "qa2@gmail.com";
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(userEmail, "12345"));
        ResponseWrapper<UserResponse> parsedResponse = ResponseHandler.executeAndGetParsedResponse(apiService.loginUser(userForSignIn));
        assertEquals(parsedResponse.getStatusCode(), 200);
        assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), userEmail);
    }

    @Test(dataProvider = "negativeCases")
    public void loginNegativeCases(String userEmail, String userPassword, String expectedErrorMessage) {
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(userEmail, userPassword));
        ResponseWrapper<String> parsedErrorResponse = ResponseHandler.executeAndGetParsedErrorResponse(apiService.loginUser(userForSignIn));
        assertEquals(parsedErrorResponse.getStatusCode(), 422);
        assertEquals(parsedErrorResponse.getResponseBody(), expectedErrorMessage);
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
