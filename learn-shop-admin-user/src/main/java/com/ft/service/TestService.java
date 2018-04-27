package com.ft.service;


import com.ft.ResData.BaseResponse;

import com.ft.vo.TestVo;

public interface TestService {
    void save(TestVo test);

    void saveProcess(TestVo test);

    void update(TestVo test);

    BaseResponse<TestVo> saveUser(TestVo TestVo);
}
