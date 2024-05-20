package com.ipwa.kp.e2e.pageobjects.company;

import com.ipwa.kp.e2e.pageobjects.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CompanyProfilePage extends Page {
    private static final By COMPANY_NAME_INPUT = By.cssSelector("#root > div.container.mx-auto > form > div:nth-child(1) > label > input");
    private static final By SUBMIT = By.cssSelector("#root > div.container.mx-auto > form > div:nth-child(10) > div > input");
    private static final By COMPANY_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-xl.w-full");



    public CompanyProfilePage(WebDriver driver) {
        super(driver);
    }

    public void editCompanyName(String name) {
        super.clearField(COMPANY_NAME_INPUT);
        super.sendText(COMPANY_NAME_INPUT, name);
    }

    public void saveChanges() {
        super.findAndClick(SUBMIT);
    }

    public void verifyCompanyName(String name) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assert super.getElementText(COMPANY_NAME).equals(name);
    }
}
