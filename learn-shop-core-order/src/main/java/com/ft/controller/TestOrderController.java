package com.ft.controller;

import com.ft.producer.CoreOrderProducer;
import com.ft.remote.TestUserRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "TestOrderController", description = "订单管理测试")
@RestController
@RequestMapping("/testOrder")
public class TestOrderController {

    @Autowired
    private TestUserRemote testUserRemote;

    @Autowired
    private CoreOrderProducer coreOrderProducer;

    @ApiOperation(value = "调用用户系统", notes = "用于测试远程调用用户系统")
    @GetMapping("/indexUser")
    public String indexUser() {
        System.out.println("/testOrder/indexUser");
        return testUserRemote.indexClient("testOrder");
    }

    @ApiOperation(value = "发送MQ消息", notes = "用于测试发送MQ消息")
    @GetMapping("/sendMQ")
    public boolean sendMQ() {
        boolean flag = false;
        try {
            coreOrderProducer.sendOrderCar();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
