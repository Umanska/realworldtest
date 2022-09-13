package org.realworld.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.realworld.ui.utils.SeleniumUtils;

import java.util.List;

public class HomePage {

    public final static String URL_PATH = "/#";
    public static final String YOUR_FEED_TAB = "//a[normalize-space(text())=\"Your Feed\"]";
    public static final String GLOBAL_FEED_TAB = "//a[normalize-space(text())=\"Global Feed\"]";
    public static final String HOME_HEADER_BANNER = "//h1 [normalize-space(text())=\"conduit\"]";
    public static final String HOME_MESSAGE_BANNER = "//p [normalize-space(text())=\"A place to share your knowledge.\"]";
    public static final String HOME_OPTION_NAV_MENU = "//a[normalize-space(text())=\"Home\")]";
    public static final String SIGN_UP_OPTION_NAV_MENU = "//a[normalize-space(text())=\"Sign up\")]";
    public static final String SIGN_IN_OPTION_NAV_MENU = "//a[normalize-space(text())=\"Sign in\")]";
    public static final String NAV_MENU_LOGGED_OUT = "//ul [contains(@class, 'navbar-nav') and @show-authed='false']";
    public static final String OPTIONS_NAV_MENU_LOGGED_IN = "//ul [@show-authed='true']/li/a";
    public static final String HOME_PAGE_IDENTIFIER = "//div [contains(@class, 'home-page')]";
    public static final String USER_PROFILE_IMG = "//li/a/img [@class='user-pic']";

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        SeleniumUtils.getElementWithWait(webDriver, HOME_PAGE_IDENTIFIER);
    }

    public WebElement bannerHeader() {
        return SeleniumUtils.getElementWithWait(webDriver, HOME_HEADER_BANNER);
    }

    public WebElement bannerMessage() {
        return SeleniumUtils.getElementWithWait(webDriver, HOME_MESSAGE_BANNER);
    }

    public WebElement yourFeedTab() {
        return SeleniumUtils.getElementWithWait(webDriver, YOUR_FEED_TAB);
    }

    public WebElement globalFeedTab() {
        return SeleniumUtils.getElementWithWait(webDriver, GLOBAL_FEED_TAB);
    }

    public List<WebElement> itemsLoggedInNavMenu() {
        SeleniumUtils.getElementWithWait(webDriver, USER_PROFILE_IMG);
        return webDriver.findElements(By.xpath(OPTIONS_NAV_MENU_LOGGED_IN));
    }

    public WebElement navigationMenuLoggedIn() {
        return SeleniumUtils.getElementWithWait(webDriver, NAV_MENU_LOGGED_OUT);
    }

    public WebElement signUpTabFromNavMenu() {
        return SeleniumUtils.getElementWithWait(webDriver, SIGN_UP_OPTION_NAV_MENU);
    }

    public WebElement signInTabFromNavMenu() {
        return SeleniumUtils.getElementWithWait(webDriver, SIGN_IN_OPTION_NAV_MENU);
    }

    public WebElement homePageTabFromNavMenu() {
        return SeleniumUtils.getElementWithWait(webDriver, HOME_OPTION_NAV_MENU);
    }
}
