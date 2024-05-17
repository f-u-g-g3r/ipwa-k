package com.ipwa.kp.e2e.pageobjects.company;

import com.ipwa.kp.e2e.pageobjects.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.security.core.parameters.P;

public class CreatePostForm extends Page {
    private static final By WORK_NAME = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(1) > label > input");
    private static final By WORK_DESCRIPTION = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(2) > label > textarea");
    private static final By SALARY = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(3) > label > input");
    private static final By SKILLS = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(4) > label > input");
    private static final By ADDITIONAL_INFO = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(5) > label > textarea");
    private static final By EXPIRATION_DATE = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(6) > label > input");
    private static final By SUBMIT = By.cssSelector("#root > div.container.mx-auto > div.mb-20 > form > div:nth-child(8) > input");

    public CreatePostForm(WebDriver driver) {
        super(driver);
    }

    public void fillWorkName(String text) {
        super.sendText(WORK_NAME, text);
    }

    public void fillDescription(String text) {
        super.sendText(WORK_DESCRIPTION, text);
    }

    public void fillSalary(int num) {
        super.sendText(SALARY, String.valueOf(num));
    }

    public void fillSkills(String text) {
        super.sendText(SKILLS, text);
    }


    public void fillAdditionalInfo(String text) {
        super.sendText(ADDITIONAL_INFO, text);
    }

    public void fillExpirationDate(String text) {
        super.sendText(EXPIRATION_DATE, text);
    }

    public void submitForm() {
        super.findAndClick(SUBMIT);
    }
}
