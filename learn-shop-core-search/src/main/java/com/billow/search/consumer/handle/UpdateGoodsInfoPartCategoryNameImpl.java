package com.billow.search.consumer.handle;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.billow.search.common.cons.FieldNameConstant;
import com.billow.search.pojo.vo.CanalDbVo;
import com.billow.search.pojo.vo.GoodsBrandVo;
import com.billow.search.service.GoodsInfoService;
import com.billow.search.utils.EsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pms_goods_brand 变更后，更新品牌
 *
 * @author 千面
 * @date 2022/8/9 9:32
 */
@Slf4j
@Component
public class UpdateGoodsInfoPartCategoryNameImpl implements UpdateTableData {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Override
    public void execute(CanalDbVo canalDbVo) {
        // 更新前的字段和值
        List<String> old = canalDbVo.getOld();
        // 更新后的所有字段和值
        List<String> data = canalDbVo.getData();

        if (CollectionUtils.isEmpty(old)) {
            log.info("pms_goods_brand 表插入新数据，old 为空，不更新");
            return;
        }
        for (int i = 0; i < old.size(); i++) {
            String oldJson = old.get(i);
            log.info("oldJson数据:{}", oldJson);
            if (!oldJson.contains(StrUtil.toUnderlineCase(FieldNameConstant.FIELD_BRAND_NAME))) {
                continue;
            }
            // 更新后的所有字段和值
            GoodsBrandVo newVo = JSON.parseObject(data.get(i), GoodsBrandVo.class);
            try {
                // 更新条件
                Map<String, Object> condition = new HashMap<>();
                condition.put(FieldNameConstant.FIELD_BRAND_ID, newVo.getId());
                // 更新值
                Map<String, Object> updateVle = new HashMap<>();
                updateVle.put(FieldNameConstant.FIELD_BRAND_NAME, newVo.getBrandName());
                updateVle.put(FieldNameConstant.FIELD_UPDATE_TIME, EsUtils.getStrNow());
                goodsInfoService.updateByCondition(condition, updateVle);
            } catch (Exception e) {
                log.error("es 更新商品分类异常:brandId:{},brandName:{},error:{}",
                        newVo.getId(), newVo.getBrandName(), e.getMessage(), e);
            }
        }
    }
}
