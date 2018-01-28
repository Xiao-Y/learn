package com.ft.controller;

import com.ft.client.TestUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testOrder")
public class TestOrderController {

    @Autowired
    private TestUserClient testUserClient;

    @RequestMapping("/indexUser")
    public String indexUser() {
        testUserClient.indexClient("testOrder");
        System.out.println("/testOrder/indexUser");
        return "indexUser/";
    }
}
