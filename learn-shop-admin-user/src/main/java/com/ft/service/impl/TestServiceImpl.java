package com.ft.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.ft.ResData.BaseResponse;
import com.ft.dao.TestDao;
import com.ft.enums.ResCodeEnum;

import com.ft.po.TestPo;
import com.ft.service.TestService;
import com.ft.utlis.BeanUtils;
import com.ft.vo.TestVo;
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
    private TestDao testDao;

    @Transactional
    @Override
    public void save(TestVo test) {
        testDao.save(test);
    }

    @Override
    public void saveProcess(TestVo test) {
        testDao.save(test);
    }

    @Transactional
    @Override
    public void update(TestVo test) {
        testDao.save(test);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackOn = Exception.class)
    public BaseResponse<TestVo> saveUser(TestVo testVo) {
        BaseResponse<TestVo> res = new BaseResponse<>(ResCodeEnum.OK);
        try {
            testVo.setName("billow");
            testVo.setAge(18);
            testVo.setUpdateTime(new Date());
            testVo.setCreateTime(new Date());
            TestPo testPo = BeanUtils.convert(testVo, TestPo.class);
            testDao.save(testPo);
            res.setResData(testVo);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResCode(ResCodeEnum.FAIL);
        }
        return res;
    }
}
