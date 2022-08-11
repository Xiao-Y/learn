package com.billow.search.common.enums;

import cn.hutool.core.util.StrUtil;
import com.billow.search.common.cons.DbTableNameConstant;
import com.billow.search.consumer.handle.UpdateGoodsInfoImpl;
import com.billow.search.consumer.handle.UpdateGoodsInfoPartBrandNameImpl;
import com.billow.search.consumer.handle.UpdateGoodsInfoPartCategoryNameImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@AllArgsConstructor
public enum DbTableNameEnum {

    PMS_GOODS_SPU(DbTableNameConstant.TABLE_PMS_GOODS_SPU, StrUtil.lowerFirst(UpdateGoodsInfoImpl.class.getSimpleName())),
    PMS_GOODS_CATEGORY(DbTableNameConstant.TABLE_PMS_GOODS_CATEGORY, StrUtil.lowerFirst(UpdateGoodsInfoPartBrandNameImpl.class.getSimpleName())),
    PMS_GOODS_BRAND(DbTableNameConstant.TABLE_PMS_GOODS_BRAND, StrUtil.lowerFirst(UpdateGoodsInfoPartCategoryNameImpl.class.getSimpleName())),
    ;

    private String tableName;
    private String serviceImpl;

    /**
     * 通过 tableName 找到 serviceImpl
     *
     * @param tableName
     * @return String
     * @author 千面
     * @date 2022/8/9 9:42
     */
    public static String getServiceImlByTableName(String tableName) {
        log.info("处理的表:{}", tableName);
        tableName = StringUtils.upperCase(tableName);
        DbTableNameEnum dbTableNameEnum = DbTableNameEnum.valueOf(tableName);
        if (dbTableNameEnum == null) {
            log.error("DbTableNameEnum 未配置对应的 {} 表处理器", tableName);
        }
        return dbTableNameEnum.getServiceImpl();
    }
}
