package com.billow.gateway.security.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String password;
    private Long id;
    private int status;
    private List<String> roles;
}
