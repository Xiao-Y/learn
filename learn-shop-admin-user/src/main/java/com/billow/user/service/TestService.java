package com.billow.user.service;


import com.billow.tools.resData.BaseResponse;

import com.billow.user.pojo.vo.TestVo;

public interface TestService {
    void save(TestVo test);

    void saveProcess(TestVo test);

    void update(TestVo test);

    BaseResponse<TestVo> saveUser(TestVo TestVo);
}
