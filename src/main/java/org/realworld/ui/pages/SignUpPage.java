package org.realworld.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.realworld.ui.utils.SeleniumUtils;

public class SignUpPage {

    private final String emailField = "//input [@type='email' and contains(@placeholder, 'Email')]";
    protected WebDriver webDriver;

    public SignUpPage(WebDriver webDriver) {
        this.webDriver = webDriver;

        String signUpHeader = "//h1[contains(text(),'Sign up')]";
        SeleniumUtils.getElementWithWait(webDriver, signUpHeader);
    }

    public boolean isSignUpPage(){
        return webDriver.getCurrentUrl().endsWith("/register");
    }

    public WebDriver signUp(String username, String email, String password) {
        fillInUsername(username);
        fillInEmail(email);
        fillInPassword(password);
        clickSignUp();
        return webDriver;
    }

    public WebElement getEmailField(){
        return SeleniumUtils.getElementWithWait(webDriver, emailField);
    }

    public SignUpPage fillInEmail(String email) {
        WebElement email_field = SeleniumUtils.getElementWithWait(webDriver, emailField);
        email_field.clear();
        email_field.sendKeys(email);
        return this;
    }

    public SignUpPage fillInUsername(String username) {
        String userNameField = "//input [contains(@placeholder, 'Username')]";
        WebElement username_field = SeleniumUtils.getElementWithWait(webDriver, userNameField);
        username_field.clear();
        username_field.sendKeys(username);
        return this;
    }

    public SignUpPage fillInPassword(String password) {
        String passwordField = "//input [contains(@placeholder, 'Password')]";
        WebElement password_field = SeleniumUtils.getElementWithWait(webDriver, passwordField);
        password_field.clear();
        password_field.sendKeys(password);
        return this;
    }

    public void clickSignUp() {
        String signUpButton = "//button [@type='submit' and contains(text(), 'Sign up')]";
        WebElement signUp = SeleniumUtils.getElementWithWait(webDriver, signUpButton);
        signUp.click();
    }
}
