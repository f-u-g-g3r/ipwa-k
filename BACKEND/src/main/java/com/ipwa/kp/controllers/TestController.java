package com.ipwa.kp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    @GetMapping
    public String testRequest() {
        return "Hello";
    }
}
