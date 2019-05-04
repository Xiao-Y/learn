package com.billow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * Hello world!
 *
 */
@EnableOAuth2Sso
@SpringBootApplication
public class Client3App {

    public static void main(String[] args) {
        SpringApplication.run(Client3App.class, args);
    }
}
