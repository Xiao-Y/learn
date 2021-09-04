package com.billow.search.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.billow.search.common.enums.SpuPublishStatusEnum;
import com.billow.search.common.enums.SpuVerifyStatusEnum;
import com.billow.search.feign.CoreProductFeign;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.vo.CanalDbVo;
import com.billow.search.pojo.vo.GoodsBrandVo;
import com.billow.search.pojo.vo.GoodsCategoryVo;
import com.billow.search.pojo.vo.GoodsSpuVo;
import com.billow.search.service.GoodsInfoService;
import com.billow.tools.date.DateUtils;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 增量数据库同步数据到es（字段变更）
 *
 * @author liuyongtao
 * @since 2021-9-2 8:18
 */
@Slf4j
@Component
public class DbSyncEsConsumer {

    private final static String TABLE_PMS_GOODS_SPU = "pms_goods_spu";
    private final static String TABLE_PMS_GOODS_CATEGORY = "pms_goods_category";
    private final static String TABLE_PMS_GOODS_BRAND = "pms_goods_brand";

    private final static String FIELD_BRAND_ID = "brandId";
    private final static String FIELD_BRAND_NAME = "brandName";
    private final static String FIELD_CATEGORY_ID = "categoryId";
    private final static String FIELD_CATEGORY_NAME = "categoryName";
    private final static String FIELD_UPDATE_TIME = "updateTime";

    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private CoreProductFeign coreProductFeign;

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
        if (!"update".equalsIgnoreCase(canalDbVo.getType())
                && !"INSERT".equalsIgnoreCase(canalDbVo.getType())
                && !"DELETE".equalsIgnoreCase(canalDbVo.getType())) {
            return;
        }
        // 获取表名
        String table = canalDbVo.getTable();
        // 更新前的字段和值
        List<String> old = canalDbVo.getOld();
        // 更新后的所有字段和值
        List<String> data = canalDbVo.getData();
        log.info("有变化的表:{}", table);
        // 更新商品
        if (TABLE_PMS_GOODS_SPU.equalsIgnoreCase(table)) {
            this.upateGoodsInfo(data, canalDbVo.getType());
        } else if (TABLE_PMS_GOODS_CATEGORY.equalsIgnoreCase(table)) {// 更新分类
            updateGoodsInfoPartCategoryName(old, data);
        } else if (TABLE_PMS_GOODS_BRAND.equalsIgnoreCase(table)) {// 更新品牌
            updateGoodsInfoPartBrandName(old, data);
        }
        log.info("完成刷新...");
    }

    /**
     * 更新品牌
     *
     * @param old
     * @param data
     * @author liuyongtao
     * @since 2021-9-3 9:16
     */
    private void updateGoodsInfoPartBrandName(List<String> old, List<String> data) {
        for (int i = 0; i < old.size(); i++) {
            String oldJson = old.get(i);
            log.info("oldJson数据:{}", oldJson);
            if (!oldJson.contains(StrUtil.toUnderlineCase(FIELD_BRAND_NAME))) {
                continue;
            }
            // 更新后的所有字段和值
            GoodsBrandVo newVo = JSON.parseObject(data.get(i), GoodsBrandVo.class);
            try {
                // 构建查询条件
                Map<String, Object> queryCondition = new HashMap<>();
                queryCondition.put(FIELD_BRAND_ID, newVo.getId());
                // 构建更新值
                Map<String, Object> updateVal = new HashMap<>();
                updateVal.put(FIELD_BRAND_NAME, newVo.getBrandName());
                Date date = DateUtils.addHours(new Date(), -8);
                updateVal.put(FIELD_UPDATE_TIME, DateUtils.getSimpleDateFormat(date));
                goodsInfoService.updateByCondition(queryCondition, updateVal);
            } catch (Exception e) {
                log.error("es 更新商品分类异常:brandId:{},brandName:{},error:{}",
                        newVo.getId(), newVo.getBrandName(), e.getMessage(), e);
            }
        }
    }

    /**
     * 更新分类
     *
     * @param old
     * @param data
     * @author liuyongtao
     * @since 2021-9-3 9:16
     */
    private void updateGoodsInfoPartCategoryName(List<String> old, List<String> data) {
        for (int i = 0; i < old.size(); i++) {
            String oldJson = old.get(i);
            log.info("oldJson数据:{}", oldJson);
            if (!oldJson.contains(StrUtil.toUnderlineCase(FIELD_CATEGORY_NAME))) {
                continue;
            }
            // 更新后的所有字段和值
            GoodsCategoryVo goodsCategoryVoNew = JSON.parseObject(data.get(i), GoodsCategoryVo.class);
            try {
                // 构建查询条件
                Map<String, Object> queryCondition = new HashMap<>();
                queryCondition.put(FIELD_CATEGORY_ID, goodsCategoryVoNew.getId());
                // 构建更新值
                Map<String, Object> updateVal = new HashMap<>();
                updateVal.put(FIELD_CATEGORY_NAME, goodsCategoryVoNew.getCategoryName());
                Date date = DateUtils.addHours(new Date(), -8);
                updateVal.put(FIELD_UPDATE_TIME, DateUtils.getSimpleDateFormat(date));
                goodsInfoService.updateByCondition(queryCondition, updateVal);
            } catch (Exception e) {
                log.error("es 更新商品分类异常:categoryId:{},categoryName:{},error:{}",
                        goodsCategoryVoNew.getId(), goodsCategoryVoNew.getCategoryName(), e.getMessage(), e);
            }
        }
    }

    /**
     * 通过主键刷新商品信息
     *
     * @param data    更新后的所有字段和值
     * @param optType 操作类型：CREATE，QUERY，INSERT，UPDATE，DELETE，ALTER
     * @author liuyongtao
     * @since 2021-9-2 21:18
     */
    private void upateGoodsInfo(List<String> data, String optType) {
        for (String json : data) {
            // 更新后的所有字段和值
            GoodsSpuVo goodsInfoVoNew = JSON.parseObject(json, GoodsSpuVo.class);
            try {
                // 没有审核通过或者不是上架
                if (!Objects.equals(SpuVerifyStatusEnum.APPROVED.getStatus(), goodsInfoVoNew.getVerifyStatus())
                        || !Objects.equals(SpuPublishStatusEnum.UP.getStatus(), goodsInfoVoNew.getPublishStatus())
                        || "DELETE".equalsIgnoreCase(optType)) {
                    goodsInfoService.delById(goodsInfoVoNew.getSpuId());
                    log.info("移除es中的商品信息,spuId:{},goodsName:{},verifyStatus:{},publishStatus:{}",
                            goodsInfoVoNew.getSpuId(), goodsInfoVoNew.getGoodsName(),
                            goodsInfoVoNew.getVerifyStatus(), goodsInfoVoNew.getPublishStatus());
                    continue;
                }
                // 查询 es 中是否存在
                GoodsInfoPo goodsInfoPoOld = goodsInfoService.getById(goodsInfoVoNew.getSpuId());
                if (!Objects.equals(goodsInfoPoOld.getBrandId(), goodsInfoVoNew.getBrandId())) {
                    try {
                        // 远程调用查询品牌
                        BaseResponse<com.billow.product.interfaces.vo.GoodsBrandVo> baseResponse = coreProductFeign.getBrandById(goodsInfoVoNew.getBrandId());
                        if (Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK) && baseResponse.getResData() != null) {
                            goodsInfoPoOld.setBrandName(baseResponse.getResData().getBrandName());
                        }
                    } catch (Exception e) {
                        log.error("远程调用查询品牌异常:brandId:{},error:{}", goodsInfoVoNew.getBrandId(), e.getMessage(), e);
                    }
                }
                if (!Objects.equals(goodsInfoPoOld.getCategoryId(), goodsInfoVoNew.getCategoryId())) {
                    try {
                        //  远程调用查询分类
                        BaseResponse<com.billow.product.interfaces.vo.GoodsCategoryVo> baseResponse = coreProductFeign.getCategoryById(goodsInfoVoNew.getCategoryId());
                        if (Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK) && baseResponse.getResData() != null) {
                            goodsInfoPoOld.setCategoryName(baseResponse.getResData().getCategoryName());
                        }
                    } catch (Exception e) {
                        log.error("远程调用查询分类异常:categoryId:{},error:{}", goodsInfoVoNew.getCategoryId(), e.getMessage(), e);
                    }
                }
                BeanUtils.copyProperties(goodsInfoVoNew, goodsInfoPoOld);
                goodsInfoPoOld.setUpdateTime(new Date());
                goodsInfoService.saveOrUpdate(goodsInfoPoOld);
                log.info("更新数据 spuId:{}", goodsInfoPoOld.getSpuId());
            } catch (Exception e) {
                log.error("更新es异常,spuId:{},goodsName:{},error:{}", goodsInfoVoNew.getSpuId(), goodsInfoVoNew.getGoodsName(), e.getMessage(), e);
            }
        }
    }

}
