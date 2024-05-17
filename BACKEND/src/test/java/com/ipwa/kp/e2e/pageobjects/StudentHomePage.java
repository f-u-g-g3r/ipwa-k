package com.ipwa.kp.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StudentHomePage extends Page {
    private static final String URL = "http://localhost:5173/login";
    private static final By WELCOME_HEADING = By.cssSelector("#root > div.container.mx-auto > div.flex.justify-center.items-start.my-20.min-h-screen > div > div > h1");

    private static final By ROLE_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-lg.font-medium.capitalize");
    private static final By JOB_POSTS_BUTTON = By.cssSelector("#root > div.container.mx-auto > div.flex.justify-center.items-start.my-20.min-h-screen > div > div > a:nth-child(2)");
    private static final By MY_APPLICATIONS_BUTTON = By.cssSelector("#root > div.container.mx-auto > div.flex.justify-center.items-start.my-20.min-h-screen > div > div > a:nth-child(3)");
    private static final By NAV_END_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div");
    private static final By LOG_OUT_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > ul > li:nth-child(2) > a");



    public StudentHomePage(WebDriver driver) {
        super(driver);
    }

    public void openUrl() {
        super.openUrl(URL);
    }

    public void verifyWelcomeMessageIsVisible() {
        WebElement welcome = super.getElement(WELCOME_HEADING);
        assert welcome != null;
    }

    public void verifyRoleName() {
        super.waitForElementVisible(ROLE_NAME);
        String role = super.getElementText(ROLE_NAME);
        assert role.equals("STUDENT");
    }

    public void logOut() {
        super.hoverOverElement(super.getElement(NAV_END_BUTTON));
        super.findAndClick(LOG_OUT_BUTTON);
    }
}
