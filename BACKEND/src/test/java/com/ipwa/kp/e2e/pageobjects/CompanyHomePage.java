package com.ipwa.kp.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompanyHomePage extends Page {
    private static final String URL = "http://localhost:5173/home?action=1";
    private static final By ROLE_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-lg.font-medium.capitalize");
    private static final By HOME_MESSAGE = By.cssSelector("#root > div.container.mx-auto > div.drawer.lg\\:drawer-open > div.drawer-content.flex.flex-col.px-20.pt-5.bg-base-200 > div > p");
    private static final By NAV_END_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div");
    private static final By LOG_OUT_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > ul > li:nth-child(2) > a");

    public CompanyHomePage(WebDriver driver) {
        super(driver);
    }

    public void openUrl() {
        super.openUrl(URL);
    }

    public void verifyWelcomeMessageIsVisible() {
        WebElement message = super.getElement(HOME_MESSAGE);
        assert message != null;
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
}
