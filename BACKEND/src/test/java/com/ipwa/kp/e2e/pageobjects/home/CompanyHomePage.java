package com.ipwa.kp.e2e.pageobjects.home;

import com.ipwa.kp.e2e.pageobjects.Page;
import com.ipwa.kp.e2e.pageobjects.company.CompanyProfilePage;
import com.ipwa.kp.e2e.pageobjects.company.CreatePostForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompanyHomePage extends Page {
    private static final String URL = "http://localhost:5173/home?action=1";
    private static final By ROLE_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-lg.font-medium.capitalize");

    private static final By CREATE_POST_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-center.hidden.lg\\:flex > ul > li:nth-child(3) > a");

    private static final By NAV_END_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div");
    private static final By MY_ACCOUNT_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > ul > li:nth-child(1) > a");

    private static final By LOG_OUT_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > ul > li:nth-child(2) > a");

    public CompanyHomePage(WebDriver driver) {
        super(driver);
    }

    public void openUrl() {
        super.openUrl(URL);
    }

    public void verifyRoleName() {
        super.waitForElementVisible(ROLE_NAME);
        String role = super.getElementText(ROLE_NAME);
        assert role.equals("COMPANY");
    }

    public void logOut() {
        super.hoverOverElement(super.getElement(NAV_END_BUTTON));
        super.findAndClick(LOG_OUT_BUTTON);
    }

    public CreatePostForm openCreatePostForm() {
        super.findAndClick(CREATE_POST_BUTTON);
        return new CreatePostForm(super.getDriver());
    }

    public CompanyProfilePage openProfile() {
        super.hoverOverElement(super.getElement(NAV_END_BUTTON));
        super.findAndClick(MY_ACCOUNT_BUTTON);
        return new CompanyProfilePage(super.getDriver());
    }
}
