package com.billow.gateway.pojo.search;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchParam implements Serializable {
    private String username;
    private String password;
}
