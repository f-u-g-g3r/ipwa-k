package com.ipwa.kp.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PostsPage extends Page {
    private static final By ALL_WORK_NAMES = By.cssSelector("#root > div.container.mx-auto > div > a > div > h2");
    //  a > div > h2

    public PostsPage(WebDriver driver) {
        super(driver);
    }

    public void verifyThatPostWithWorkNameExist(String workName) {
        List<WebElement> webElements = super.getElements(ALL_WORK_NAMES);
        var isFound = false;
        for (WebElement e : webElements) {
            if (e.getText().equals(workName)) {
                isFound = true;
            }
        }
        assert isFound;
    }
}
