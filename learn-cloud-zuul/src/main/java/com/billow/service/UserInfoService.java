package com.billow.service;

import com.billow.pojo.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author Levin
 * @date 2017-08-15.
 */
@Service
public class UserInfoService {

    public UserInfo findByName(String username) {
        //TODO 该处只是为了模拟查询数据库
        if (!username.equals("test") && !username.equals("admin") && !username.equals("sa")) {
            return null;
        }
        return new UserInfo(username, "test");
    }
}
