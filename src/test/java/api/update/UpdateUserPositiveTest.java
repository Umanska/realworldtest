package api.update;

import org.realworld.api.Session;
import org.realworld.api.datamodel.requests.UpdateUser;
import org.realworld.api.datamodel.requests.UpdateUserRequest;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.api.services.AuthenticationService;
import org.realworld.api.services.RetrofitFactory;
import org.realworld.api.utils.ModelValidatorUtils;
import org.realworld.api.utils.ResponseUtils;
import org.realworld.utils.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

import static org.realworld.api.Constants.EMAIL_SUFFIX;
import static org.realworld.api.Constants.USER_NAME_PREFIX_API;

public class UpdateUserPositiveTest {

    private ApiService authorizedApiService;

    @BeforeMethod(alwaysRun = true)
    public void signIn() {
        String userName = StringUtils.getUniqueUsername(USER_NAME_PREFIX_API);
        ApiService apiService = RetrofitFactory.getInstance().createService(ApiService.class);
        Session session = new AuthenticationService(apiService).createSessionWithEmail(userName + EMAIL_SUFFIX);
        authorizedApiService = RetrofitFactory.getInstance().createAuthorizedService(ApiService.class, session.getToken());
    }

    @Test(dataProvider = "email")
    public void updateUserEmailFields(String updatedEmail) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .email(updatedEmail)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), updatedEmail);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @Test(dataProvider = "username")
    public void updateUserUsernameFields(String updatedUsername) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .username(updatedUsername)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), updatedUsername);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @Test(dataProvider = "image")
    public void updateUserOptionalImageFields(String updatedImage) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .image(updatedImage)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getImage(), updatedImage);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @Test(dataProvider = "bio")
    public void updateUserOptionalBioFields(String updatedBio) {
        UpdateUser updateUser = new UpdateUser.Builder()
                .bio(updatedBio)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getBio(), updatedBio);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @Test
    public void updateUsernameFieldTwice() {
        String updatedUsername = StringUtils.getUniqueUsername(USER_NAME_PREFIX_API);
        UpdateUser updateUser = new UpdateUser.Builder()
                .username(updatedUsername)
                .build();
        ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), updatedUsername);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @Test
    public void updateAllFields() {
        String updatedUsername = StringUtils.getUniqueUsername(USER_NAME_PREFIX_API);
        String updatedImage = "https://test.com";
        String updatedBio = "https://en.wikipedia.org";
        UpdateUser updateUser = new UpdateUser.Builder()
                .username(updatedUsername)
                .email(updatedUsername + EMAIL_SUFFIX)
                .image(updatedImage)
                .bio(updatedBio)
                .build();
        ResponseWrapper<UserResponse> parsedResponse = ResponseUtils.executeAndParse(
                authorizedApiService.updateUser(new UpdateUserRequest(updateUser)));

        SoftAssert sAssert = new SoftAssert();
        sAssert.assertEquals(parsedResponse.getStatusCode(), 200);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getUsername(), updatedUsername);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getEmail(), updatedUsername + EMAIL_SUFFIX);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getImage(), updatedImage);
        sAssert.assertEquals(parsedResponse.getResponseBody().getUser().getBio(), updatedBio);
        sAssert.assertEquals(ModelValidatorUtils.validate(parsedResponse.getResponseBody().getUser()), Collections.EMPTY_SET);
        sAssert.assertAll();
    }

    @DataProvider(name = "email")
    public Object[][] createEmailData() {
        String userName = USER_NAME_PREFIX_API + System.nanoTime();
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
        String userName = StringUtils.getUniqueUsername(USER_NAME_PREFIX_API);
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
