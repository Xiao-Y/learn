package com.ft.service;


import com.ft.ResData.BaseResponse;
import com.ft.model.TestModel;

public interface TestService {
    void save(TestModel test);

    void saveProcess(TestModel test);

    void update(TestModel test);

    BaseResponse<TestModel> saveUser(TestModel testModel);
}
