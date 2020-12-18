package com.billow.gateway.security.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String usercode;
    private List<String> roles;
}
