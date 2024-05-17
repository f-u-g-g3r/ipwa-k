package com.ipwa.kp.api;

import com.ipwa.kp.models.Post;
import com.ipwa.kp.security.auth.AuthenticationRequest;
import com.ipwa.kp.specification.Specifications;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostControllerTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    void setUp() {

        // GETTING TOKEN
        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/auth/authenticate"), Specifications.responseSpecOK200());
        token = given()
                .body(new AuthenticationRequest("companyUsername", "password"))
                .when()
                .post()
                .then()
                .extract()
                .path("token");
        // --------------

        Specifications.installSpecification(Specifications.requestSpec("http://localhost:" + port + "/posts"), Specifications.responseSpecOK200());
    }

    @Test
    public void createPost() {
        Post post = new Post();
        given()
                .header(new Header("Authorization", "Bearer " + token))
                .body(post)
                .when()
                .post()
                .then().log().all();
    }

}
