package com.billow.controller;

import cn.hutool.core.util.ArrayUtil;
import com.billow.service.TestService;
import com.billow.pojo.vo.TestVo;
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
        TestVo test = new TestVo();
        test.setAge(22);
        test.setCreateTime(new Date());
        test.setUpdateTime(new Date());
        test.setName("billow");
        testService.save(test);
        ArrayUtil.isEmpty(ArrayUtil.newArray(3));
    }

    @PutMapping("/testUpdate")
    public void update() {
        TestVo test = new TestVo();
        test.setId(1L);
        test.setAge(24);
        test.setUpdateTime(new Date());
        testService.update(test);
    }

}
