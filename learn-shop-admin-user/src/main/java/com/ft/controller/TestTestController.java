package com.ft.controller;

import com.ft.model.TestModel;
import com.ft.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:53
 */
@RestController
public class TestTestController {

    @Autowired
    private TestService testService;

    @PutMapping("/testSave")
    public void save() {
        TestModel test = new TestModel();
        test.setAge(22);
        test.setCreateDate(new Date());
        test.setUpdateDate(new Date());
        test.setName("billow");
        testService.save(test);
    }

    @PutMapping("/testUpdate")
    public void update() {
        TestModel test = new TestModel();
        test.setId(1L);
        test.setAge(24);
        test.setUpdateDate(new Date());
        testService.update(test);
    }

}
