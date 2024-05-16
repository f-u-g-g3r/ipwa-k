package com.ipwa.kp.api;

import com.ipwa.kp.security.auth.AuthenticationRequest;
import com.ipwa.kp.specification.Specifications;
import io.restassured.http.Header;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoordinatorControllerTest {

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

        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/internship-coordinators"), Specifications.responseSpecOK200());
    }


    @Test
    public void getOneCoordinatorById1() {
        given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/1")
                .then()
                .body("id", equalTo(1));
    }

    @Test
    public void getCoordinatorByNonExistentIdNotFound404() {
        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/internship-coordinators"), Specifications.responseSpecNotFound404());
        given()
                .when()
                .header(new Header("Authorization", "Bearer " + token))
                .get("/1000000")
                .then()
                .body("error", notNullValue());
    }
}
