package com.billow.search.consumer.handle;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.billow.search.common.cons.FieldNameConstant;
import com.billow.search.pojo.vo.CanalDbVo;
import com.billow.search.pojo.vo.GoodsCategoryVo;
import com.billow.search.service.GoodsInfoService;
import com.billow.search.utils.EsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pms_goods_category 变更后，更新分类
 *
 * @author 千面
 * @date 2022/8/9 9:30
 */
@Slf4j
public class UpdateGoodsInfoPartBrandNameImpl implements UpdateTableData {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Override
    public void execute(CanalDbVo canalDbVo) {

        // 更新前的字段和值
        List<String> old = canalDbVo.getOld();
        // 更新后的所有字段和值
        List<String> data = canalDbVo.getData();

        if (CollectionUtils.isEmpty(old)) {
            log.info("pms_goods_category 表插入新数据，old 为空，不更新");
            return;
        }
        for (int i = 0; i < old.size(); i++) {
            String oldJson = old.get(i);
            log.info("oldJson数据:{}", oldJson);
            if (!oldJson.contains(StrUtil.toUnderlineCase(FieldNameConstant.FIELD_CATEGORY_NAME))) {
                continue;
            }
            // 更新后的所有字段和值
            GoodsCategoryVo goodsCategoryVoNew = JSON.parseObject(data.get(i), GoodsCategoryVo.class);
            try {
                // 更新条件
                Map<String, Object> condition = new HashMap<>();
                condition.put(FieldNameConstant.FIELD_CATEGORY_ID, goodsCategoryVoNew.getId());
                // 更新值
                Map<String, Object> updateVle = new HashMap<>();
                updateVle.put(FieldNameConstant.FIELD_CATEGORY_NAME, goodsCategoryVoNew.getCategoryName());
                updateVle.put(FieldNameConstant.FIELD_UPDATE_TIME, EsUtils.getStrNow());
                goodsInfoService.updateByCondition(condition, updateVle);
            } catch (Exception e) {
                log.error("es 更新商品分类异常:categoryId:{},categoryName:{},error:{}",
                        goodsCategoryVoNew.getId(), goodsCategoryVoNew.getCategoryName(), e.getMessage(), e);
            }
        }
    }
}
