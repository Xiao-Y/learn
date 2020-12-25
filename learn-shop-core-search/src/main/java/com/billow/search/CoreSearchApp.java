package com.billow.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CoreSearchApp {

    public static void main(String[] args) {
        SpringApplication.run(CoreSearchApp.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "hello elasticsearch";
    }
}
