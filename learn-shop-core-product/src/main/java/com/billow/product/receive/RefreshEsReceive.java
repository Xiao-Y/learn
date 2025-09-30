package com.billow.product.receive;

import com.alibaba.fastjson.JSON;
//import com.billow.common.amqp.config.BaseMqConfig;
import com.billow.product.dao.GoodsBrandDao;
import com.billow.product.dao.GoodsCategoryDao;
import com.billow.product.dao.GoodsSpuDao;
import com.billow.product.pojo.ex.SpuInfoEx;
import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.po.GoodsCategoryPo;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.service.GoodsSpuService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 刷新 es 缓存生产者
 *
 * @author liuyongtao
 * @create 2018-02-06 16:45
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Component
public class RefreshEsReceive {

////    private final BaseMqConfig baseMqConfig;
//    private final AmqpTemplate amqpTemplate;
    private final GoodsSpuService goodsSpuService;
    private final GoodsBrandDao goodsBrandDao;
    private final GoodsCategoryDao goodsCategoryDao;
    private final GoodsSpuDao goodsSpuDao;

    /**
     * 刷新 es 缓存生产者
     *
     * @author xiaoy
     * @since 2021/2/7 19:58
     */
    public void sendRefreshEsInfoAll() {
        List<GoodsSpuPo> list = goodsSpuService.list();
        list.stream().forEach(f -> {
            // 组装数据，发送mq
            this.converDataSendMQ(f);
        });
    }

    /**
     * 刷新 es 缓存生产者
     *
     * @author xiaoy
     * @since 2021/2/7 19:58
     */
    public void sendRefreshEsInfoBySpuId(Long spuId) {
        GoodsSpuPo goodsSpuPo = goodsSpuDao.selectById(spuId);
        // 组装数据，发送mq
        this.converDataSendMQ(goodsSpuPo);
    }

    /**
     * 组装数据，发送mq
     *
     * @param goodsSpuPo
     * @author xiaoy
     * @since 2021/2/7 21:32
     */
    private void converDataSendMQ(GoodsSpuPo goodsSpuPo) {
        GoodsBrandPo goodsBrandPo = goodsBrandDao.selectById(goodsSpuPo.getBrandId());
        GoodsCategoryPo goodsCategoryPo = goodsCategoryDao.selectById(goodsSpuPo.getCategoryId());
        // 构建数据
        SpuInfoEx ex = new SpuInfoEx();
        ex.setBrandId(goodsBrandPo.getId())
                .setBrandName(goodsBrandPo.getBrandName())
                .setCategoryId(goodsCategoryPo.getId())
                .setCategoryName(goodsCategoryPo.getCategoryName())
                .setGoodsName(goodsSpuPo.getGoodsName())
                .setLowPrice(goodsSpuPo.getLowPrice())
                .setSpuId(goodsSpuPo.getId())
                .setStock(goodsSpuPo.getStock())
                .setSubTitle(goodsSpuPo.getSubTitle());
        String message = JSON.toJSONString(ex);
//        amqpTemplate.convertAndSend(baseMqConfig.getExchange().getProduct(),
//                baseMqConfig.getRouteKey().getRefreshEs(), message);
        log.info("【MQ发送内容】" + message);
    }

}
