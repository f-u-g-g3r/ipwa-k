package com.ipwa.kp.e2e.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Page {
    private static final int DEFAULT_TIMEOUT = 5000;
    public WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void findAndClick(By locator) {
        driver.findElement(locator).click();
    }

    public String getElementText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void sendText(By locator, String text) {
        var element = driver.findElement(locator);
        element.click();
        element.sendKeys(text);
    }

    public void clearField(By locator) {
        driver.findElement(locator).clear();
    }

    public String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    public void scrollTo(WebElement element) {
        Actions actions = new Actions(driver);
        actions.scrollToElement(element);
        actions.perform();
    }

    public void hoverOverElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void waitUntilElementText(By locator, String text) {
        WebElement element = this.driver.findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(DEFAULT_TIMEOUT));
        wait.until((ExpectedCondition<Boolean>) d -> element.getText().equals(text));
    }

    public void waitForElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void verifyRoutingEndsWith(String routingEnd) {
        String url = driver.getCurrentUrl();
        assert url.endsWith(routingEnd);
    }
}
