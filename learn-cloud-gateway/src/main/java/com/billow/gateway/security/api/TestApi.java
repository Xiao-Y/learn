package com.billow.gateway.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试使用
 *
 * @author liuyongtao
 * @create 2019-11-12 11:36
 */
@RestController
public class TestApi {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test/noauth")
    public String noAuth() {
        String url = "http://learn-shop-admin-system/admin-system/menuApi/homeMenus";

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("version","1.0.0");
        headers.add("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIndlYmFwcCJdLCJpZCI6MiwiZXhwIjoxNjA4NDEwMzIxLCJ1c2VyY29kZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiMmM0NjgxNDItMGYyMy00YTBiLWFiZDAtNTVjY2JkMmNmZWM5IiwiY2xpZW50X2lkIjoid2ViYXBwIn0.Ks6EqJBmY7kP_sHcYBNUAP4jPOuYT_TkbeC46cSiU_m5CZWu9vZEo8NASR4si2NyeG80LlPgCCdYIgb4JTGmqfbRD9X3mCRKl8QqCQneNW37UVvszXD-3rGa4XAJzsxGTEt5HUPkxwozQ7Kfow5al8rywkvap618F6vNrGbv-EQ7jmEtX9cc-ifqw-OMnaONtcE09elHQJZ7RAu26EYs2pH1arGd8b3iSAn9F0oYFdFp_2cwE6teTBFxlkgTiwjmIwUq12JPA-pRgcqWuOWFv-rZabUa0VT4sJ2eJ9kbw9Sv-l0hugHCNWx9DHPRvZ78MAepnLg7Q3MuuufWe1Ippg");
        HttpEntity<String> requestParam = new HttpEntity<>( headers);
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, requestParam, String.class);
        System.out.println(entity);

        return "hi no auth";
    }

    @GetMapping("/testApi/auth")
    public String auth() {
        return "hi auth";
    }
}
