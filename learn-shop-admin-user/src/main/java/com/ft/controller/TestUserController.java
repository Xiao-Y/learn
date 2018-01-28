package com.ft.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testUser")
public class TestUserController {

    @RequestMapping("/indexUser")
    public String indexUser(String name) {
        System.out.println("indexUser: " + name);
        return "indexUser";
    }
}
