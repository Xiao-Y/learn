package com.ft.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testUser")
public class TestUserController {

    @Value("${words}")
    private String words;

    @RequestMapping("/indexUser")
    public String indexUser(String name) {
        System.out.println(words);
        System.out.println("indexUser: " + name);
        return "indexUser";
    }
}
