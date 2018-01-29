package com.ft.controller;

import com.ft.remote.TestUserRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testOrder")
public class TestOrderController {

    @Autowired
    private TestUserRemote testUserRemote;

    @RequestMapping("/indexUser")
    public String indexUser() {
        System.out.println("/testOrder/indexUser");
        return testUserRemote.indexClient("testOrder");
    }
}
