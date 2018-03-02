package com.ft.service.impl;

import com.ft.dao.TestRepository;
import com.ft.model.TestModel;
import com.ft.service.TestService;
import com.ft.sysEvent.dao.SysEventPublishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
