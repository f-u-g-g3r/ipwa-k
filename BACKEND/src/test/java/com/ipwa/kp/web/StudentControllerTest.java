package com.ipwa.kp.web;


import com.ipwa.kp.controllers.requests.StudentPatchRequest;
import com.ipwa.kp.security.auth.AuthenticationRequest;
import com.ipwa.kp.specification.Specifications;
import io.restassured.http.Header;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    void setUp() {

        // GETTING TOKEN
        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/auth/authenticate"), Specifications.responseSpecOK200());
        token = given()
                .body(new AuthenticationRequest("coordinatorEmail", "password"))
                .when()
                .post()
                .then()
                .extract()
                .path("token");
        // --------------

        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/students"), Specifications.responseSpecOK200());
    }

    @Test
    @Order(1)
    public void getAllStudents() {
        given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .get()
                .then()
                .body("content", notNullValue());
    }

    @Test
    @Order(2)
    public void getOneStudentById1() {
        given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/1")
                .then()
                .body("id", equalTo(1));
    }


    @Test
    @Order(3)
    public void updateStudentLastName() {
        StudentPatchRequest request = new StudentPatchRequest();
        request.setLastName("NewLastName");
        given()
                .body(request)
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .patch("/1")
                .then()
                .body("lastName", equalTo("NewLastName"));
    }


    @Test
    @Order(4)
    public void deleteStudentByIdStatusOk() {
        given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .delete("/1");
    }

    @Test
    public void getStudentByNonExistentId() {
        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/students"), Specifications.responseSpecForbidden403());
        given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/1000000")
                .then().log().all();
    }
}
