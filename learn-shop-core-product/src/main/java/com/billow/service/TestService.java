package com.billow.service;

import com.billow.pojo.vo.TestVo;

public interface TestService {
    TestVo saveTest(TestVo testVo) throws Exception;

    TestVo findTestById(Long id) throws Exception;

    TestVo updateTestById(Long id) throws Exception;

    void deleteTestById(Long id) throws Exception;
}
