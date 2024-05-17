package com.ipwa.kp.e2e.webtesting;

import com.ipwa.kp.e2e.pageobjects.*;
import com.ipwa.kp.e2e.pageobjects.company.CreatePostForm;
import com.ipwa.kp.e2e.pageobjects.home.CompanyHomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyTesting {
    public static final Duration TIMEOUT = Duration.ofMillis(5000);

    public static WebDriver driver = new ChromeDriver();

    public static LoginPage loginPage;
    public static CompanyHomePage companyHomePage;
    public static CreatePostForm createPostForm;

    @BeforeAll
    public static void setUp() {

        driver.get("http://localhost:5173/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TIMEOUT).scriptTimeout(TIMEOUT).implicitlyWait(TIMEOUT);

        loginPage = new LoginPage(driver);
        loginPage.openUrl();
    }

    @AfterAll
    public static void endUp() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void logIn() {
        loginPage.fillUsername("companyUsername");
        loginPage.fillPassword("password");
        companyHomePage = loginPage.logInAsCompany();
        companyHomePage.verifyRoleName();
    }

    @Test
    @Order(2)
    public void createPost() {
        createPostForm = companyHomePage.openCreatePostForm();
        createPostForm.fillWorkName("TestWork");
        createPostForm.fillDescription("TestDescription");
        createPostForm.fillSalary(30);
        createPostForm.fillSkills("Some Skills");
        createPostForm.fillAdditionalInfo("Additional");
        createPostForm.fillExpirationDate("12122024");
        createPostForm.submitForm();
    }
}
