package com.billow.service;


import com.billow.common.resData.BaseResponse;

import com.billow.pojo.vo.TestVo;

public interface TestService {
    void save(TestVo test);

    void saveProcess(TestVo test);

    void update(TestVo test);

    BaseResponse<TestVo> saveUser(TestVo TestVo);
}
