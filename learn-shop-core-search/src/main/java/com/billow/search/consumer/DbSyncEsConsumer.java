package com.billow.search.consumer;

import com.alibaba.fastjson.JSON;
import com.billow.search.common.cons.CrudConstant;
import com.billow.search.common.enums.DbTableNameEnum;
import com.billow.search.consumer.handle.UpdateTableData;
import com.billow.search.pojo.vo.CanalDbVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 增量数据库同步数据到es（字段变更）
 *
 * @author liuyongtao
 * @since 2021-9-2 8:18
 */
@Slf4j
@Component
public class DbSyncEsConsumer {

    @Autowired
    private Map<String, UpdateTableData> updateTableDataMap;

    /**
     * 同步 mysql 数据到 es
     *
     * @param message
     * @author liuyongtao
     * @since 2021-9-1 15:34
     */
    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "canal_queue")
    @RabbitHandler
    public void syncEs(String message) {
        log.info("mysql：{}", message);
        CanalDbVo canalDbVo = JSON.parseObject(message, CanalDbVo.class);
        if (!CrudConstant.UPDATE.equalsIgnoreCase(canalDbVo.getType())
                && !CrudConstant.INSERT.equalsIgnoreCase(canalDbVo.getType())
                && !CrudConstant.DELETE.equalsIgnoreCase(canalDbVo.getType())) {
            return;
        }
        // 更新商品
        String serviceImpl = DbTableNameEnum.getServiceImlByTableName(canalDbVo.getTable());
        updateTableDataMap.get(serviceImpl).execute(canalDbVo);
        log.info("完成刷新...");
    }

}
