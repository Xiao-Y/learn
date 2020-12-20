package com.billow.gateway.security.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVo {
    private Long id;
    private String usercode;
    private String username;
    private String password;
    private List<String> roles;

}
