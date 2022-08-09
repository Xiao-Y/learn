package com.billow.search.consumer.handle;

import com.alibaba.fastjson.JSON;
import com.billow.product.interfaces.vo.GoodsBrandVo;
import com.billow.search.common.cons.CrudConstant;
import com.billow.search.common.enums.SpuPublishStatusEnum;
import com.billow.search.common.enums.SpuVerifyStatusEnum;
import com.billow.search.feign.GoodsBrandFeign;
import com.billow.search.feign.GoodsCategoryFeign;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.vo.CanalDbVo;
import com.billow.search.pojo.vo.GoodsSpuVo;
import com.billow.search.service.GoodsInfoService;
import com.billow.search.utils.EsUtils;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * 更新商品基础信息，更换分类、更换品牌
 *
 * @author 千面
 * @date 2022/8/9 9:48
 */
@Slf4j
public class UpdateGoodsInfoImpl implements UpdateTableData {

    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private GoodsBrandFeign coreProductBrandFeign;
    @Autowired
    private GoodsCategoryFeign coreProductCategoryFeign;

    @Override
    public void execute(CanalDbVo canalDbVo) {
        // 更新后的所有字段和值
        List<String> data = canalDbVo.getData();
        // 操作类型
        String optType = canalDbVo.getType();

        for (String json : data) {
            // 更新后的所有字段和值
            GoodsSpuVo goodsInfoVoNew = JSON.parseObject(json, GoodsSpuVo.class);
            try {
                // 没有审核通过或者不是上架
                if (!Objects.equals(SpuVerifyStatusEnum.APPROVED.getStatus(), goodsInfoVoNew.getVerifyStatus())
                        || !Objects.equals(SpuPublishStatusEnum.UP.getStatus(), goodsInfoVoNew.getPublishStatus())
                        || CrudConstant.DELETE.equalsIgnoreCase(optType)) {
                    goodsInfoService.delById(goodsInfoVoNew.getSpuId());
                    log.info("移除es中的商品信息,spuId:{},goodsName:{},verifyStatus:{},publishStatus:{}",
                            goodsInfoVoNew.getSpuId(), goodsInfoVoNew.getGoodsName(),
                            goodsInfoVoNew.getVerifyStatus(), goodsInfoVoNew.getPublishStatus());
                    continue;
                }
                // 查询 es 中是否存在
                GoodsInfoPo goodsInfoPoOld = goodsInfoService.getById(goodsInfoVoNew.getSpuId());
                // 更新品牌名称
                if (!Objects.equals(goodsInfoPoOld.getBrandId(), goodsInfoVoNew.getBrandId())) {
                    try {
                        // 远程调用查询品牌
                        BaseResponse<GoodsBrandVo> baseResponse = coreProductBrandFeign.getBrandById(goodsInfoVoNew.getBrandId());
                        if (Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK) && baseResponse.getResData() != null) {
                            goodsInfoPoOld.setBrandName(baseResponse.getResData().getBrandName());
                        }
                    } catch (Exception e) {
                        log.error("远程调用查询品牌异常:brandId:{},error:{}", goodsInfoVoNew.getBrandId(), e.getMessage(), e);
                    }
                }
                // 更新分类名称
                if (!Objects.equals(goodsInfoPoOld.getCategoryId(), goodsInfoVoNew.getCategoryId())) {
                    try {
                        //  远程调用查询分类
                        BaseResponse<com.billow.product.interfaces.vo.GoodsCategoryVo> baseResponse = coreProductCategoryFeign.getCategoryById(goodsInfoVoNew.getCategoryId());
                        if (Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK) && baseResponse.getResData() != null) {
                            goodsInfoPoOld.setCategoryName(baseResponse.getResData().getCategoryName());
                        }
                    } catch (Exception e) {
                        log.error("远程调用查询分类异常:categoryId:{},error:{}", goodsInfoVoNew.getCategoryId(), e.getMessage(), e);
                    }
                }
                // 用于校验是否有es中的字段更新
                GoodsInfoPo temp = new GoodsInfoPo();
                BeanUtils.copyProperties(goodsInfoVoNew, temp);
                if (temp.equals(goodsInfoPoOld)) {
                    log.info("pms_goods_spu 没有要同步的字段");
                    return;
                }
                BeanUtils.copyProperties(goodsInfoVoNew, goodsInfoPoOld);
                goodsInfoPoOld.setUpdateTime(EsUtils.getLocalNow());
                goodsInfoService.saveOrUpdate(goodsInfoPoOld);
                log.info("更新数据 spuId:{}", goodsInfoPoOld.getSpuId());
            } catch (Exception e) {
                log.error("更新es异常,spuId:{},goodsName:{},error:{}", goodsInfoVoNew.getSpuId(), goodsInfoVoNew.getGoodsName(), e.getMessage(), e);
            }
        }
    }
}
