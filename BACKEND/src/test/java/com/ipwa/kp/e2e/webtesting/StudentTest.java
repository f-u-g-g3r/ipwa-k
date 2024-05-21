package com.ipwa.kp.e2e.webtesting;


import com.ipwa.kp.e2e.pageobjects.LoginPage;
import com.ipwa.kp.e2e.pageobjects.home.StudentHomePage;
import com.ipwa.kp.e2e.pageobjects.student.StudentProfilePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTest {
    public static final Duration TIMEOUT = Duration.ofMillis(5000);

    public static WebDriver driver = new ChromeDriver();

    public static LoginPage loginPage;
    public static StudentHomePage studentHomePage;
    public static StudentProfilePage studentProfilePage;

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
    public void openWebPage() {
        loginPage.verifyUsernameInputVisible();
    }

    @Test
    @Order(2)
    public void loginAsStudent() {
        loginPage.fillUsername("userUsername");
        loginPage.fillPassword("password");

        studentHomePage = loginPage.logInAsStudent();
        loginPage.verifyUsernameInputVisible();
    }

    @Test
    @Order(3)
    public void editStudentName() {
        var firstName = "SomeName";
        var lastName = "SomeLast";

        studentProfilePage = studentHomePage.openMyAccount();
        studentProfilePage.fillFirstName(firstName);
        studentProfilePage.fillLastName(lastName);
        studentProfilePage.submitForm();

        studentProfilePage.verifyName(firstName + " " + lastName);
    }
}
