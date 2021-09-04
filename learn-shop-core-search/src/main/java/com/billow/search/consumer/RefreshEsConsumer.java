package com.billow.search.consumer;

import com.alibaba.fastjson.JSON;
import com.billow.search.common.enums.SpuPublishStatusEnum;
import com.billow.search.common.enums.SpuVerifyStatusEnum;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.vo.RefreshEsDataVo;
import com.billow.search.service.GoodsInfoService;
import com.billow.tools.utlis.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 商品系统 发送过来的消息（商品上下架）
 *
 * @author liuyongtao
 * @create 2019-08-11 11:14
 */
@Slf4j
@Component
public class RefreshEsConsumer {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "${config.mq.queue.refreshEs}")
    @RabbitHandler
    public void refreshEs(String message) {
        log.info("获取的信息：{}", message);
        RefreshEsDataVo refreshEsDataVo = JSON.parseObject(message, RefreshEsDataVo.class);
        if (Objects.isNull(refreshEsDataVo) || Objects.isNull(refreshEsDataVo.getSpuId())) {
            log.info("SpuId 为空，没有要刷新的数据...");
            return;
        }
        GoodsInfoPo goodsInfoPoUpdate = goodsInfoService.getById(refreshEsDataVo.getSpuId());
        ConvertUtils.copyNonNullProperties(refreshEsDataVo, goodsInfoPoUpdate);
        if (!Objects.equals(SpuVerifyStatusEnum.APPROVED.getStatus(), refreshEsDataVo.getVerifyStatus())
                || !Objects.equals(SpuPublishStatusEnum.UP.getStatus(), refreshEsDataVo.getPublishStatus())) {
            log.info("状态不对，没有要刷新的数据...");
            return;
        }
        goodsInfoService.saveOrUpdate(goodsInfoPoUpdate);
        log.info("完成刷新...");
    }
}
