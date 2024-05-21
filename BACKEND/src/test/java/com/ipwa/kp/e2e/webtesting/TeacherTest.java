package com.ipwa.kp.e2e.webtesting;

import com.ipwa.kp.e2e.pageobjects.LoginPage;
import com.ipwa.kp.e2e.pageobjects.home.TeacherHomePage;
import com.ipwa.kp.e2e.pageobjects.teacher.TeacherProfilePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeacherTest {
    public static final Duration TIMEOUT = Duration.ofMillis(5000);

    public static WebDriver driver = new ChromeDriver();

    public static LoginPage loginPage;
    public static TeacherHomePage teacherHomePage;
    public static TeacherProfilePage teacherProfilePage;

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
    public void loginAsTeacher() {
        loginPage.fillUsername("teacherUsername");
        loginPage.fillPassword("password");

        teacherHomePage = loginPage.logInAsTeacher();
        loginPage.verifyUsernameInputVisible();
    }

    @Test
    @Order(3)
    public void editTeacherName() {
        var firstName = "SomeName";
        var lastName = "SomeLast";

        teacherProfilePage = teacherHomePage.openMyAccount();
        teacherProfilePage.fillFirstName(firstName);
        teacherProfilePage.fillLastName(lastName);
        teacherProfilePage.submitForm();

        teacherProfilePage.verifyName(firstName + " " + lastName);
    }
}
