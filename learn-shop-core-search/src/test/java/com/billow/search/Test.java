package com.billow.search;

import com.billow.search.pojo.po.GoodsInfoPo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author liuyongtao
 * @since 2021-9-2 20:29
 */
public class Test {

    public static void main(String[] args) throws Exception {
        GoodsInfoPo goodsInfoPo = new GoodsInfoPo();
//        Field field = ReflectionUtils.findField(GoodsInfoPo.class, "categoryId");
//        field.setAccessible(true);
//        field.set(goodsInfoPo, 1L);

//        Method method = ReflectionUtils.findMethod(GoodsInfoPo.class, "setCategoryId", Long.class);
////        method.invoke(goodsInfoPo, "1");
//        ReflectionUtils.invokeMethod(method,goodsInfoPo, 1L);

        setValue(goodsInfoPo, "recommandStatus", "1");

        System.out.println(goodsInfoPo);
    }

    public static void setValue(GoodsInfoPo goodsInfoPo, String fieldName, String value) throws Exception {
        Field field = ReflectionUtils.findField(goodsInfoPo.getClass(), fieldName);
        if (field == null) {
            return;
        }
        field.setAccessible(true);
        Object obj = null;
        if (StringUtils.isNotBlank(value)) {
            Class<?> type = field.getType();
            if (type.isAssignableFrom(Long.class)) {
                obj = new Long(value);
            } else if (type.isAssignableFrom(Integer.class)) {
                obj = new Integer(value);
            }
        }

        field.set(goodsInfoPo, obj);
    }
}
