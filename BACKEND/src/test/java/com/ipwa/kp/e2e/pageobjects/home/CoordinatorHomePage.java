package com.ipwa.kp.e2e.pageobjects.home;

import com.ipwa.kp.e2e.pageobjects.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CoordinatorHomePage extends Page {

    private static final String URL = "http://localhost:5173/home?action=1";
    private static final By ROLE_NAME = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div > div > p.text-lg.font-medium.capitalize");
    private static final By NAV_END_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > div");
    private static final By LOG_OUT_BUTTON = By.cssSelector("#root > div.navbar.bg-base-100 > div.navbar-end > div > ul > li:nth-child(2) > a");

    private static final By STUDENT_PROFILE_MANAGEMENT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-side.h-full > ul > li:nth-child(1) > a");
    private static final By STUDENT_HEADING = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > p");
    private static final By CREATE_NEW_STUDENT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.tabs.tabs-bordered.w-1\\/2.mx-auto.mb-5 > a:nth-child(2)");
    private static final By GENERATE_RANDOM_STUDENT_USERNAME = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div:nth-child(3) > div.flex.flex-wrap.justify-center.mt-10 > button");
    private static final By GENERATE_RANDOM_STUDENT_PASSWORD = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div:nth-child(3) > div.flex.flex-wrap.justify-center.mt-5 > button");
    private static final By SUBMIT_NEW_STUDENT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div:nth-child(3) > div:nth-child(3) > button");

    private static final By COMPANY_PROFILE_MANAGEMENT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-side.h-full > ul > li:nth-child(2) > a");
    private static final By CREATE_NEW_COMPANY = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.tabs.tabs-bordered.w-1\\/2.mx-auto.mb-5 > a:nth-child(2)");
    private static final By COMPANY_HEADING = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > p");
    private static final By GENERATE_RANDOM_COMPANY_USERNAME = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.flex.flex-wrap.justify-center.mt-10 > button");
    private static final By GENERATE_RANDOM_COMPANY_PASSWORD = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.flex.flex-wrap.justify-center.mt-5 > button");
    private static final By SUBMIT_NEW_COMPANY = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div:nth-child(5) > button");

    private static final By TEACHER_PROFILE_MANAGEMENT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-side.h-full > ul > li:nth-child(3) > a");
    private static final By CREATE_NEW_TEACHER = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.tabs.tabs-bordered.w-1\\/2.mx-auto.mb-5 > a:nth-child(2)");
    private static final By TEACHER_HEADING = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > p");
    private static final By GENERATE_RANDOM_TEACHER_USERNAME = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.flex.flex-wrap.justify-center.mt-10 > button");
    private static final By GENERATE_RANDOM_TEACHER_PASSWORD = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div.flex.flex-wrap.justify-center.mt-5 > button");
    private static final By SUBMIT_NEW_TEACHER = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > div:nth-child(5) > button");


    private static final By GROUP_MANAGEMENT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-side.h-full > ul > li:nth-child(4) > a");
    private static final By NEW_GROUP_INPUT = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > form > div > label > input");
    private static final By ADD_NEW_GROUP = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > form > div > button");
    private static final By GROUP_HEADING = By.cssSelector("#root > div.container.mx-auto > div.flex > div > div.drawer-content.flex.flex-col.px-20.pt-5.bg-gray-200.pb-20 > p");




    public CoordinatorHomePage(WebDriver driver) {
        super(driver);
    }

    public void openUrl() {
        super.openUrl(URL);
    }


    public void verifyRoleName() {
        super.waitForElementVisible(ROLE_NAME);
        String role = super.getElementText(ROLE_NAME);
        assert role.equals("COORDINATOR");
    }

    public void logOut() {
        super.hoverOverElement(super.getElement(NAV_END_BUTTON));
        super.findAndClick(LOG_OUT_BUTTON);
    }

    public void openStudentProfileManagement() {
        super.findAndClick(STUDENT_PROFILE_MANAGEMENT);
    }

    public void verifyStudentProfileManagementIsOpen() {
        assert super.getElementText(STUDENT_HEADING).equalsIgnoreCase("students");
    }

    public void openCreateNewStudent() {
        super.findAndClick(CREATE_NEW_STUDENT);
    }

    public void generateRandomDataForNewStudentAndSubmit() {
        super.findAndClick(GENERATE_RANDOM_STUDENT_USERNAME);
        super.findAndClick(GENERATE_RANDOM_STUDENT_PASSWORD);
        super.findAndClick(SUBMIT_NEW_STUDENT);
    }

    public void openCompanyProfileManagement() {
        super.findAndClick(COMPANY_PROFILE_MANAGEMENT);
    }

    public void verifyCompanyProfileManagementIsOpen() {
        assert super.getElementText(COMPANY_HEADING).equalsIgnoreCase("companies");
    }

    public void openCreateNewCompany() {
        super.findAndClick(CREATE_NEW_COMPANY);
    }

    public void generateRandomDataForNewCompanyAndSubmit() {
        super.findAndClick(GENERATE_RANDOM_COMPANY_USERNAME);
        super.findAndClick(GENERATE_RANDOM_COMPANY_PASSWORD);
        super.findAndClick(SUBMIT_NEW_COMPANY);
    }

    public void openTeacherProfileManagement() {
        super.findAndClick(TEACHER_PROFILE_MANAGEMENT);
    }

    public void verifyTeacherProfileManagementIsOpen() {
        assert super.getElementText(TEACHER_HEADING).equalsIgnoreCase("teachers");
    }

    public void openCreateNewTeacher() {
        super.findAndClick(CREATE_NEW_TEACHER);
    }

    public void generateRandomDataForNewTeacherAndSubmit() {
        super.findAndClick(GENERATE_RANDOM_TEACHER_USERNAME);
        super.findAndClick(GENERATE_RANDOM_TEACHER_PASSWORD);
        super.findAndClick(SUBMIT_NEW_TEACHER);
    }

    public void openGroupsManagement() {
        super.findAndClick(GROUP_MANAGEMENT);
    }

    public void verifyGroupManagementIsOpen() {
        assert super.getElementText(GROUP_HEADING).equalsIgnoreCase("groups");
    }

    public void createNewGroup(String groupName) {
        super.sendText(NEW_GROUP_INPUT, groupName);
        super.findAndClick(ADD_NEW_GROUP);
    }


}
