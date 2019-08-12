/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.billow.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/test1/getTest1")
    public String getTest() {
        return "test";
    }

    @GetMapping("/test/getTest2/{id}")
    public Authentication getTest2(@PathVariable(required = false) String id, Authentication authentication) throws Exception {
        if ("1".equals(id)) {
            throw new RuntimeException("发生了异常：99999DDDDDD");
        }
        return authentication;
    }

    @GetMapping("/test/getTest3")
    public Authentication getTest3() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/test/getTest4")
    public void getTest4() {
        System.out.println("void");
    }

    @GetMapping("/user")
    public Authentication getUser(Authentication authentication) {
        return authentication;
    }
}
