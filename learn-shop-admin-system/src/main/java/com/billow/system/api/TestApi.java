package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 白名单
 *
 * @author liuyongtao
 * @create 2018-05-19 14:35
 */
@Api(value = "WhiteListController", description = "白名单")
@RestController
@RequestMapping("/api")
@RabbitListener(queues = "${config.mq.orderToUser.orderStatus}")
@RefreshScope
public class TestApi extends BaseApi {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${words}")
    private String words;

    @Value("${config.mq.orderToUser.orderStatus}")
    public String orderStatus;

    @ApiOperation(value = "sendMQ", notes = "sendMQ")
    @GetMapping("/sendMQ")
    public BaseResponse<String> sendMQ() {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            String context = "wqh say hello " + new Date();
            logger.info("发送消息=========》》{}》》{}", orderStatus, context);
            this.amqpTemplate.convertAndSend(orderStatus, context);
            baseResponse.setResData(context);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResCode(ResCodeEnum.FAIL);
        }
        return baseResponse;
    }

    @RabbitHandler
    public void receiver(String hello) {
        logger.info("接收消息=====》》》》》{}", hello);
    }

    @ApiOperation(value = "getWords", notes = "getWords")
    @GetMapping("/getWords")
    public BaseResponse<String> getWords() {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            baseResponse.setResData(words);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResCode(ResCodeEnum.FAIL);
        }
        return baseResponse;
    }

    @ApiOperation(value = "getWords", notes = "getWords")
    @GetMapping("/getWords2")
    public BaseResponse<String> getWords2() {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            baseResponse.setResData(words);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResCode(ResCodeEnum.FAIL);
        }
        return baseResponse;
    }
}
