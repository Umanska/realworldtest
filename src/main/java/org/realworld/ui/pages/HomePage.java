package org.realworld.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.realworld.ui.utils.SeleniumUtils;

import java.util.List;

public class HomePage {

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;

        String homePageIdentifier = "//div [contains(@class, 'home-page')]";
        SeleniumUtils.getElementWithWait(webDriver, homePageIdentifier);
    }

    public WebElement bannerHeader() {
        String bannerHeader = "//h1 [text(),'conduit']";
        return SeleniumUtils.getElementWithWait(webDriver, bannerHeader);
    }

    public WebElement bannerMessage() {
        String bannerMessage = "//p [text(),'A place to share your knowledge.']";
        return SeleniumUtils.getElementWithWait(webDriver, bannerMessage);
    }

    public WebElement yourFeedTab() {
        String yourFeedTab = "//a [contains(text(),'Your Feed')]";
        return SeleniumUtils.getElementWithWait(webDriver, yourFeedTab);
    }

    public WebElement globalFeedTab() {
        String yourFeedTab = "//a [contains(text(),'Global Feed')]";
        return SeleniumUtils.getElementWithWait(webDriver, yourFeedTab);
    }

    public List<WebElement> itemsLoggedInNavMenu() {
        SeleniumUtils.getElementWithWait(webDriver, "//li/a/img [@class='user-pic']");
        return webDriver.findElements(By.xpath("//ul [@show-authed='true']/li/a"));
    }

    public WebElement navigationMenuLoggedIn() {
        String navigationMenu = "//ul [contains(@class, 'navbar-nav') and @show-authed='false']";
        return SeleniumUtils.getElementWithWait(webDriver, navigationMenu);
    }

    public WebElement signUpTabFromNavMenu() {
        String signUpTab = "//a[contains(text(), 'Sign up')]";
        return SeleniumUtils.getElementWithWait(webDriver, signUpTab);
    }

    public WebElement signInTabFromNavMenu() {
        String signInTab = "//a[contains(text(), 'Sign in')]";
        return SeleniumUtils.getElementWithWait(webDriver, signInTab);
    }

    public WebElement homePageTabFromNavMenu() {
        String homeTab = "//a[contains(text(), 'Home')]";
        return SeleniumUtils.getElementWithWait(webDriver, homeTab);
    }
}
