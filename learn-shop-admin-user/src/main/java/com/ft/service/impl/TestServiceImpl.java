package com.ft.service.impl;

import com.ft.ResData.BaseResponse;
import com.ft.dao.TestRepository;
import com.ft.enums.ResCodeEnum;
import com.ft.model.TestModel;
import com.ft.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:46
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Transactional
    @Override
    public void save(TestModel test) {
        testRepository.save(test);
    }

    @Override
    public void saveProcess(TestModel test) {
        testRepository.save(test);
    }

    @Transactional
    @Override
    public void update(TestModel test) {
        testRepository.save(test);
    }

    @Override
    public BaseResponse<TestModel> saveUser(TestModel testModel) {
        BaseResponse<TestModel> res = new BaseResponse<>(ResCodeEnum.OK);
        try {
            testModel.setName("billow");
            testModel.setAge(18);
            testModel.setUpdateDate(new Date());
            testModel.setCreateDate(new Date());
            testRepository.save(testModel);
            res.setResData(testModel);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResCode(ResCodeEnum.FAIL);
        }
        return res;
    }
}
