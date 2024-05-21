package com.ipwa.kp.e2e.pageobjects.teacher;

import com.ipwa.kp.e2e.pageobjects.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TeacherProfilePage extends Page {


    private static final By FIRST_NAME_INPUT = By.cssSelector("#root > div.container.mx-auto > div.flex.justify-center > form > div:nth-child(1) > label > input");
    private static final By LAST_NAME_INPUT = By.cssSelector("#root > div.container.mx-auto > div.flex.justify-center > form > div:nth-child(2) > label > input");
    private static final By SUBMIT = By.cssSelector("#root > div.container.mx-auto > div.flex.justify-center > form > div:nth-child(4) > div > input");

    private static final By NAVBAR_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-xl.w-full");
    public TeacherProfilePage(WebDriver driver) {
        super(driver);
    }

    public void fillFirstName(String firstName) {
        super.clearField(FIRST_NAME_INPUT);
        super.sendText(FIRST_NAME_INPUT, firstName);
    }

    public void fillLastName(String lastName) {
        super.clearField(LAST_NAME_INPUT);
        super.sendText(LAST_NAME_INPUT, lastName);
    }

    public void submitForm() {
        super.findAndClick(SUBMIT);
    }

    public void verifyName(String fullName) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assert super.getElementText(NAVBAR_NAME).equalsIgnoreCase(fullName);
    }
}
