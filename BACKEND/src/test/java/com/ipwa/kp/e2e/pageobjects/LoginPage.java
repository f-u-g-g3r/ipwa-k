package com.ipwa.kp.e2e.pageobjects;

import com.ipwa.kp.e2e.pageobjects.home.CompanyHomePage;
import com.ipwa.kp.e2e.pageobjects.home.CoordinatorHomePage;
import com.ipwa.kp.e2e.pageobjects.home.StudentHomePage;
import com.ipwa.kp.e2e.pageobjects.home.TeacherHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends Page {
    private static final String URL = "http://localhost:5173/login";
    private static final By USERNAME_INPUT = By.cssSelector("#root > div.container.mx-auto > form > div:nth-child(1) > label > input");
    private static final By PASSWORD_INPUT = By.cssSelector("#root > div.container.mx-auto > form > div:nth-child(2) > label > input");
    private static final By LOGIN_BUTTON = By.cssSelector("#root > div.container.mx-auto > form > div.flex.justify-center.my-3 > button");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openUrl() {
        super.openUrl(URL);
    }

    public void verifyUsernameInputVisible() {
        WebElement usernameInput = super.getElement(USERNAME_INPUT);
        assert usernameInput != null;
    }

    public void fillUsername(String username) {
        super.clearField(USERNAME_INPUT);
        super.sendText(USERNAME_INPUT, username);
    }

    public void fillPassword(String password) {
        super.clearField(PASSWORD_INPUT);
        super.sendText(PASSWORD_INPUT, password);
    }

    public void logIn() {
        super.findAndClick(LOGIN_BUTTON);
    }

    public StudentHomePage logInAsStudent() {
        super.findAndClick(LOGIN_BUTTON);
        return new StudentHomePage(super.getDriver());
    }

    public CompanyHomePage logInAsCompany() {
        super.findAndClick(LOGIN_BUTTON);
        return new CompanyHomePage(super.getDriver());
    }

    public TeacherHomePage logInAsTeacher() {
        super.findAndClick(LOGIN_BUTTON);
        return new TeacherHomePage(super.getDriver());
    }

    public CoordinatorHomePage logInAsCoordinator() {
        super.findAndClick(LOGIN_BUTTON);
        return new CoordinatorHomePage(super.getDriver());
    }
}
