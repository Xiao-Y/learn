package com.billow.order.api;

import com.billow.common.base.BaseApi;
import com.billow.order.pojo.vo.OrderVo;
import com.billow.order.producer.CoreOrderProducer;
import com.billow.order.remote.TestUserRemote;
import com.billow.order.service.CoreOrderService;
import com.billow.tools.resData.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(value = "TestOrderController", description = "订单管理测试")
@RestController
@RequestMapping("/testOrderApi")
public class TestOrderApi extends BaseApi {

    @Autowired
    private TestUserRemote testUserRemote;

    @Autowired
    private CoreOrderProducer coreOrderProducer;
    @Autowired
    private CoreOrderService coreOrderService;

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
//            coreOrderProducer.sendOrderCar();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @ApiOperation(value = "发送MQ对象消息", notes = "用于测试发送MQ对象消息")
    @GetMapping("/sendMQTest")
    public String sendMQTest() {
        OrderVo vo = new OrderVo();
        vo.setProductName("Billow").setProductNo("SSDFW1231").setCreateTime(new Date());
//        coreOrderProducer.sendOrderCar(vo);
        return "SSDFW1231";
    }

    @ApiOperation(value = "发送TxMQ消息", notes = "用于测试事务处理发送MQ消息")
    @GetMapping("/sendTxMQ")
    public boolean sendTxMQ() {
        boolean flag = false;
        try {
//            coreOrderService.sendOrderCar();
//            flag = true;
            for (int i = 0; i < 500; i++) {
                coreOrderService.sendOrderCar();
                flag = true;
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    @ApiOperation(value = "调用用户系统,使用Feign直接调用", notes = "用于测试远程调用用户系统带事务的")
    @GetMapping("/saveUserAndOrder")
    public BaseResponse<OrderVo> saveUserAndOrder() {
        return coreOrderService.saveUserAndOrder();
    }

    @ApiOperation(value = "调用用户系统,使用Feign+分布式事务调用", notes = "用于测试远程调用用户系统带事务的")
    @GetMapping("/saveUserAndOrderTx")
    public BaseResponse<OrderVo> saveUserAndOrderTx() {
        return coreOrderService.saveUserAndOrderTx();
    }
}
