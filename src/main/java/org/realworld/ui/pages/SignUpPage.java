package org.realworld.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUpPage extends BasePage {

    public static final String URL_PATH = "/#/register";
    public static final String EMAIL_FIELD = "//input [@type='email' and contains(@placeholder, 'Email')]";
    public static final String USERNAME_FIELD = "//input [contains(@placeholder, 'Username')]";
    public static final String PASSWORD_FIELD = "//input [contains(@placeholder, 'Password')]";
    public static final String SIGN_UP_BUTTON = "//button [@type='submit' and normalize-space(text())=\"Sign up\"]";
    public static final String SIGN_UP_HEADER = "//h1[normalize-space(text())=\"Sign up\"]";

    public SignUpPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void waitUntilLoaded() {
        waitVisibility(By.xpath(SIGN_UP_HEADER));
    }

    @Step("Sign up user with username: [0], email: [1], and password: [2] step...")
    public WebDriver signUp(String username, String email, String password) {
        fillInUsername(username);
        fillInEmail(email);
        fillInPassword(password);
        clickSignUp();
        System.out.println("Sign Up with username: " + username + ", email: " + email);
        return getDriver();
    }

    public WebElement getEmailField() {
        return waitVisibilityAndGet(By.xpath(EMAIL_FIELD));
    }

    @Step("Fill in email field with value: [0] step...")
    public SignUpPage fillInEmail(String email) {
        WebElement email_field = waitVisibilityAndGet(By.xpath(EMAIL_FIELD));
        email_field.clear();
        email_field.sendKeys(email);
        return this;
    }

    @Step("Fill in username field with value: [0] step...")
    public SignUpPage fillInUsername(String username) {
        WebElement username_field = waitVisibilityAndGet(By.xpath(USERNAME_FIELD));
        username_field.clear();
        username_field.sendKeys(username);
        return this;
    }

    @Step("Fill in password field with value: [0] step...")
    public SignUpPage fillInPassword(String password) {
        WebElement password_field = waitVisibilityAndGet(By.xpath(PASSWORD_FIELD));
        password_field.clear();
        password_field.sendKeys(password);
        return this;
    }

    @Step("Click on Sign up button step...")
    public void clickSignUp() {
        WebElement signUp = waitVisibilityAndGet(By.xpath(SIGN_UP_BUTTON));
        signUp.click();
    }
}
