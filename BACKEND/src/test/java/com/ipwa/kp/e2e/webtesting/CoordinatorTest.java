package com.ipwa.kp.e2e.webtesting;


import com.ipwa.kp.e2e.pageobjects.LoginPage;
import com.ipwa.kp.e2e.pageobjects.home.CoordinatorHomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoordinatorTest {
    public static final Duration TIMEOUT = Duration.ofMillis(5000);

    public static WebDriver driver = new ChromeDriver();

    public static LoginPage loginPage;
    public static CoordinatorHomePage coordinatorHomePage;

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
    public void loginAsCoordinator() {
        loginPage.fillUsername("coordinatorEmail");
        loginPage.fillPassword("password");

        coordinatorHomePage = loginPage.logInAsCoordinator();
        loginPage.verifyUsernameInputVisible();
    }

    @Test
    @Order(3)
    public void openStudentProfileManagement() {
        coordinatorHomePage.openStudentProfileManagement();
        coordinatorHomePage.verifyStudentProfileManagementIsOpen();
    }

    @Test
    @Order(4)
    public void createNewStudent() {
        coordinatorHomePage.openCreateNewStudent();
        coordinatorHomePage.generateRandomDataForNewStudentAndSubmit();
    }

    @Test
    @Order(5)
    public void openCompanyProfileManagement() {
        coordinatorHomePage.openCompanyProfileManagement();
        coordinatorHomePage.verifyCompanyProfileManagementIsOpen();
    }

    @Test
    @Order(6)
    public void createNewCompany() {
        coordinatorHomePage.openCreateNewCompany();
        coordinatorHomePage.generateRandomDataForNewCompanyAndSubmit();
    }

    @Test
    @Order(7)
    public void openTeacherProfileManagement() {
        coordinatorHomePage.openTeacherProfileManagement();
        coordinatorHomePage.verifyTeacherProfileManagementIsOpen();
    }

    @Test
    @Order(8)
    public void createNewTeacher() {
        coordinatorHomePage.openCreateNewTeacher();
        coordinatorHomePage.generateRandomDataForNewTeacherAndSubmit();
    }

}
