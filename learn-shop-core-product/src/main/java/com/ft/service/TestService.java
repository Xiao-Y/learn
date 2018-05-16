package com.ft.service;

import com.ft.vo.TestVo;

public interface TestService {
    TestVo saveTest(TestVo testVo) throws Exception;

    TestVo findTestById(Long id) throws Exception;

    TestVo updateTestById(Long id) throws Exception;

    void deleteTestById(Long id) throws Exception;
}
