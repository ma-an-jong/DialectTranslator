package com.springboot.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api")
    public String test() {
        return "동네 사람들 API";
    }
}
