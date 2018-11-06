package com.billow.zuul.service;

import com.billow.tools.http.HttpUtils;
import com.billow.tools.resData.BaseResponse;
import com.billow.zuul.pojo.UserInfo;
import com.billow.zuul.pojo.re.UserRe;
import com.billow.zuul.remote.user.UserRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Levin
 * @date 2017-08-15.
 */
@Service
public class UserInfoService {

    @Autowired
    private UserRemote userRemote;

    public UserInfo findByName(String usercode) {
        BaseResponse<UserRe> baseResponse = userRemote.findUserInfoByUsercode(usercode);
        UserRe userRe = HttpUtils.getResData(baseResponse);
        return new UserInfo(userRe.getId(), userRe.getUsername(), userRe.getPassword());
    }
}
