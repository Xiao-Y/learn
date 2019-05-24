package com.billow.system.api;

import com.alibaba.fastjson.JSONObject;
import com.billow.system.pojo.vo.UserVo;
import com.billow.tools.resData.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆退出时处理
 *
 * @author liuyongtao
 * @create 2019-05-22 14:10
 */
@RestController
@RequestMapping("/api")
public class loginApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserVo userVo) {
        logger.info("userVo:{}", JSONObject.toJSONString(userVo));
        BaseResponse baseResponse = new BaseResponse();
        return baseResponse;
    }
}
