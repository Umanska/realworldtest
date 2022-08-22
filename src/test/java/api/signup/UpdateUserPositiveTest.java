package api.signup;

import org.realworld.api.Session;
import org.realworld.api.datamodel.requests.UpdateUser;
import org.realworld.api.datamodel.requests.UpdateUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.utils.ResponseHandler;
import org.realworld.utils.RetrofitFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.realworld.api.Constants.EMAIL_SUFFIX;
import static org.realworld.api.Constants.USER_NAME_PREFIX;

public class UpdateUserPositiveTest {

    private ApiService authorizedApiService;

    @BeforeMethod(alwaysRun = true)
    public void SignIn() {
        String userName = USER_NAME_PREFIX + System.nanoTime();
        ApiService apiService = RetrofitFactory.getInstance().createService(ApiService.class);
        Session session = new Session().createSessionWithEmail(userName + EMAIL_SUFFIX, apiService);
        authorizedApiService = RetrofitFactory.getInstance().createAuthorizedService(ApiService.class, session.getToken());
    }

    @Test(dataProvider = "email")
    public void UpdateUserEmailFields(String updatedEmail) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .email(updatedEmail)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseHandler.executeAndGetParsedResponse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), updatedEmail);
        sAssert.assertAll();
    }

    @Test(dataProvider = "username")
    public void UpdateUserUsernameFields(String updatedUsername) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .username(updatedUsername)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseHandler.executeAndGetParsedResponse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), updatedUsername);
        sAssert.assertAll();
    }

    @Test(dataProvider = "image")
    public void UpdateUserOptionalImageFields(String updatedImage) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .image(updatedImage)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseHandler.executeAndGetParsedResponse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getImage(), updatedImage);
        sAssert.assertAll();
    }

    @Test(dataProvider = "bio")
    public void UpdateUserOptionalBioFields(String updatedBio) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .bio(updatedBio)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseHandler.executeAndGetParsedResponse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getBio(), updatedBio);
        sAssert.assertAll();
    }

    @Test
    public void UpdateUsernameFieldTwice() {
        //TODO add json schema assert
        String updatedUsername = USER_NAME_PREFIX + System.nanoTime() + System.currentTimeMillis();
        UpdateUser updateUser = new UpdateUser.Builder()
                .username(updatedUsername)
                .build();
        ResponseHandler.executeAndGetParsedResponse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        ResponseWrapper<UserResponse> parsedResponse = ResponseHandler.executeAndGetParsedResponse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), updatedUsername);
        sAssert.assertAll();
    }

    @DataProvider(name = "email")
    public Object[][] createEmailData() {
        String userName = USER_NAME_PREFIX + System.nanoTime();
        return new Object[][]{
                {userName + System.currentTimeMillis() + EMAIL_SUFFIX},
                {userName + "-d" + EMAIL_SUFFIX},
                {userName + ".d" + EMAIL_SUFFIX},
                {userName + "_d" + EMAIL_SUFFIX},
                {userName + "@mail-archive.com"},
                {userName + "@mail.org"},
                {userName + "@mail.cc"}
        };
    }

    @DataProvider(name = "username")
    public Object[][] createUsernameData() {
        String userName = USER_NAME_PREFIX + System.nanoTime();
        return new Object[][]{
                {userName + System.currentTimeMillis()},
                {userName + "\t"},
                {userName + ".aaa"},
                {userName + "-abc123"}
        };
    }

    @DataProvider(name = "image")
    public Object[][] createImageData() {
        return new Object[][]{
                {"https://en.wikipedia.org/wiki/File:Juvenile_Ragdoll.jpg"},
                {"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQgAAAC/CAMAAAA1kLK0A"},
                {"https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.ytimg.com&w=1280&h=720"},
                {"https://i.imgur.com/ImtPjHP.png"},
                {"http://test.com"}
        };
    }

    @DataProvider(name = "bio")
    public Object[][] createBioData() {
        return new Object[][]{
                {"https://en.wikipedia.org"},
                {"http://test.com"},
                {"https://www.merriam-webster.com/dictionary/bio"}
        };
    }
}
