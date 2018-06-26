package com.billow.api;

import com.billow.common.base.BaseApi;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.common.whiteList.service.WhiteListService;
import com.billow.config.RabbitMqConfig;
import com.billow.pojo.vo.sys.WhiteListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 白名单
 *
 * @author liuyongtao
 * @create 2018-05-19 14:35
 */
@Api(value = "WhiteListController", description = "白名单")
@RestController
@RequestMapping("/test")
@RabbitListener(queues = RabbitMqConfig.queueName)
@RefreshScope
public class TestApi extends BaseApi {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${words}")
    private String words;

    @ApiOperation(value = "sendMQ", notes = "sendMQ")
    @GetMapping("/sendMQ")
    public BaseResponse<String> sendMQ() {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            String context = "wqh say hello " + new Date();
            logger.info("发送消息=========》》》》{}", context);
            this.amqpTemplate.convertAndSend(RabbitMqConfig.queueName, context);
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
}
