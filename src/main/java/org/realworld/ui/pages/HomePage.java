package org.realworld.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {

    public final static String URL_PATH = "/#";
    public static final String YOUR_FEED_TAB = "//a[normalize-space(text())='Your Feed']";
    public static final String GLOBAL_FEED_TAB = "//a[normalize-space(text())='Global Feed']";
    public static final String HOME_HEADER_BANNER = "//h1 [normalize-space(text())='conduit']";
    public static final String HOME_MESSAGE_BANNER = "//p [normalize-space(text())='A place to share your knowledge.']";
    public static final String HOME_OPTION_NAV_MENU = "//ul[@show-authed='true']/li/a[normalize-space(text())='Home']";
    public static final String SIGN_UP_OPTION_NAV_MENU = "//a[normalize-space(text())='Sign up']";
    public static final String SIGN_IN_OPTION_NAV_MENU = "//a[normalize-space(text())='Sign in']";
    public static final String NAV_MENU_LOGGED_OUT = "//ul [contains(@class, 'navbar-nav') and @show-authed='false']";
    public static final String OPTIONS_NAV_MENU_LOGGED_IN = "//ul [@show-authed='true']/li/a";
    public static final String HOME_PAGE_IDENTIFIER = "//div [contains(@class, 'home-page')]";
    public static final String USER_PROFILE_ICON = "a[ui-sref*=\"app.profile.main\"]";

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void waitUntilLoaded() {
        waitVisibilityAndGet(By.xpath(HOME_PAGE_IDENTIFIER));
    }

    public WebElement bannerHeader() {
        return waitVisibilityAndGet(By.xpath(HOME_HEADER_BANNER));
    }

    public WebElement bannerMessage() {
        return waitVisibilityAndGet(By.xpath(HOME_MESSAGE_BANNER));
    }

    public WebElement yourFeedTab() {
        return waitVisibilityAndGet(By.xpath(YOUR_FEED_TAB));
    }

    public WebElement globalFeedTab() {
        return waitVisibilityAndGet(By.xpath(GLOBAL_FEED_TAB));
    }

    public List<WebElement> itemsLoggedInNavMenu() {
        waitVisibility(By.cssSelector(USER_PROFILE_ICON));
        return getDriver().findElements(By.xpath(OPTIONS_NAV_MENU_LOGGED_IN));
    }

    public WebElement navigationMenuLoggedIn() {
        return waitVisibilityAndGet(By.xpath(NAV_MENU_LOGGED_OUT));
    }

    public WebElement signUpTabFromNavMenu() {
        return waitVisibilityAndGet(By.xpath(SIGN_UP_OPTION_NAV_MENU));
    }

    public WebElement signInTabFromNavMenu() {
        return waitVisibilityAndGet(By.xpath(SIGN_IN_OPTION_NAV_MENU));
    }

    public WebElement homePageTabFromNavMenu() {
        return waitVisibilityAndGet(By.xpath(HOME_OPTION_NAV_MENU));
    }
}
