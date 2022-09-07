package org.realworld.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.realworld.ui.utils.SeleniumUtils;

public class SignUpPage {

    public final String EMAIL_FIELD = "//input [@type='email' and contains(@placeholder, 'Email')]";
    public final String USERNAME_FIELD = "//input [contains(@placeholder, 'Username')]";
    public final String PASSWORD_FIELD = "//input [contains(@placeholder, 'Password')]";
    public final String SIGNUP_BUTTON = "//button [@type='submit' and contains(text(), 'Sign up')]";
    public final static String URL_PATH = "/#/register";
    protected WebDriver webDriver;

    public SignUpPage(WebDriver webDriver) {
        this.webDriver = webDriver;

        String signUpHeader = "//h1[contains(text(),'Sign up')]";
        SeleniumUtils.getElementWithWait(webDriver, signUpHeader);
    }

    public WebDriver signUp(String username, String email, String password) {
        fillInUsername(username);
        fillInEmail(email);
        fillInPassword(password);
        clickSignUp();
        return webDriver;
    }

    public WebElement getEmailField() {
        return SeleniumUtils.getElementWithWait(webDriver, EMAIL_FIELD);
    }

    public SignUpPage fillInEmail(String email) {
        WebElement email_field = SeleniumUtils.getElementWithWait(webDriver, EMAIL_FIELD);
        email_field.clear();
        email_field.sendKeys(email);
        return this;
    }

    public SignUpPage fillInUsername(String username) {
        WebElement username_field = SeleniumUtils.getElementWithWait(webDriver, USERNAME_FIELD);
        username_field.clear();
        username_field.sendKeys(username);
        return this;
    }

    public SignUpPage fillInPassword(String password) {
        WebElement password_field = SeleniumUtils.getElementWithWait(webDriver, PASSWORD_FIELD);
        password_field.clear();
        password_field.sendKeys(password);
        return this;
    }

    public void clickSignUp() {
        WebElement signUp = SeleniumUtils.getElementWithWait(webDriver, SIGNUP_BUTTON);
        signUp.click();
    }
}
