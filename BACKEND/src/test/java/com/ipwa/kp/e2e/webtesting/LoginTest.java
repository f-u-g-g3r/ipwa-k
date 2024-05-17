package com.ipwa.kp.e2e.webtesting;

import com.ipwa.kp.e2e.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.time.Duration;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {
    public static final Duration TIMEOUT = Duration.ofMillis(5000);

    public static WebDriver driver = new ChromeDriver();

    public static LoginPage loginPage;
    public static StudentHomePage studentHomePage;
    public static CompanyHomePage companyHomePage;
    public static TeacherHomePage teacherHomePage;
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
    public void loginAsStudentThenLogOut() {
        loginPage.fillUsername("userUsername");
        loginPage.fillPassword("password");

        studentHomePage = loginPage.logInAsStudent();

        studentHomePage.verifyRoleName();
        studentHomePage.logOut();
    }

    @Test
    @Order(3)
    public void loginAsCompanyThenLogOut() {
        loginPage.fillUsername("companyUsername");
        loginPage.fillPassword("password");

        companyHomePage = loginPage.logInAsCompany();

        companyHomePage.verifyRoleName();
        companyHomePage.logOut();
    }

    @Test
    @Order(4)
    public void loginAsTeacherThenLogOut() {
        loginPage.fillUsername("teacherUsername");
        loginPage.fillPassword("password");

        teacherHomePage = loginPage.logInAsTeacher();

        teacherHomePage.verifyRoleName();
        teacherHomePage.logOut();
    }

    @Test
    @Order(5)
    public void loginAsCoordinatorThenLogOut() {
        loginPage.fillUsername("coordinatorEmail");
        loginPage.fillPassword("password");

        coordinatorHomePage = loginPage.logInAsCoordinator();

        coordinatorHomePage.verifyRoleName();
        coordinatorHomePage.logOut();
    }

    @Test
    @Order(6)
    public void loginWithWrongData() {
        loginPage.fillUsername("asfsafsao");
        loginPage.fillPassword("safsafsafsa");
        loginPage.logIn();

        loginPage.verifyUsernameInputVisible();
    }





}
