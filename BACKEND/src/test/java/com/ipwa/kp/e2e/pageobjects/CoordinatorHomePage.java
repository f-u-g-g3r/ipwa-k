package com.ipwa.kp.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CoordinatorHomePage extends Page {

    private static final String URL = "http://localhost:5173/home?action=1";
    private static final By ROLE_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-lg.font-medium.capitalize");
    private static final By NAV_END_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div");
    private static final By LOG_OUT_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > ul > li:nth-child(2) > a");

    public CoordinatorHomePage(WebDriver driver) {
        super(driver);
    }

    public void openUrl() {
        super.openUrl(URL);
    }


    public void verifyRoleName() {
        super.waitForElementVisible(ROLE_NAME);
        String role = super.getElementText(ROLE_NAME);
        assert role.equals("COORDINATOR");
    }

    public void logOut() {
        super.hoverOverElement(super.getElement(NAV_END_BUTTON));
        super.findAndClick(LOG_OUT_BUTTON);
    }
}
