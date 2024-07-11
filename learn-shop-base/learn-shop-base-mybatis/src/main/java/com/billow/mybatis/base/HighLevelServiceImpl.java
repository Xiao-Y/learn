package com.billow.mybatis.base;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.mybatis.pojo.BasePage;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 高级公用服务实现类
 * </p>
 * M-OrderItemDao 继承 BaseMapper
 * <p>
 * E-OrderItemPo 实体类
 * <p>
 * SP-OrderItemSearchParam 查询对象 继承 BasePage
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public abstract class HighLevelServiceImpl<M extends BaseMapper<E>, E, SP extends BasePage> extends ServiceImpl<M, E> implements HighLevelService<E, SP> {

    // 查询对象
    protected Class<SP> sPClass = (Class<SP>) this.getClass(2);

    @Override
    public IPage<E> findListByPage(IPage<E> page, SP sp) {
        LambdaQueryWrapper<E> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        this.genQueryCondition(wrapper, sp);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean prohibitById(Long id) {
        UpdateWrapper<E> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("validInd", false)
                .eq("id", id);
        return this.update(updateWrapper);
    }

    /**
     * 分页查询的查询条件
     *
     * @param wrapper
     * @param sp
     * @author liuyongtao
     * @since 2021-8-13 10:20
     */
    public void genQueryCondition(LambdaQueryWrapper<E> wrapper, SP sp) {
        // 排除字段
        List<String> excludedFields = Arrays.asList("pageSize", "pageNo", "orderBy", "isAsc");
        Filter<Field> filter = field -> {
            // 只取私有的，并且不在 excludedFields 中的字段
            if (Modifier.isPrivate(field.getModifiers())) {
                return !excludedFields.contains(field.getName());
            }
            return false;
        };
        StringBuffer bufferSql = new StringBuffer();
        int i = 0;
        List<Object> values = new ArrayList<>();
        // 获取查询字段
        Field[] fields = ReflectUtil.getFields(sPClass, filter);
        for (Field field : fields) {
            Object fieldValue = ReflectUtil.getFieldValue(sp, field);
            if (Objects.nonNull(fieldValue)) {
                String column = StrUtil.toUnderlineCase(field.getName());
                // 使用 apply 方法动态添加条件
                bufferSql.append(" and ");
                if (fieldValue instanceof List && column.endsWith("list")) {
                    column = column.replaceFirst("_list", "");
                    // 多个值查询
                    bufferSql.append(column + " in ( ");
                    List fieldValueList = (List) fieldValue;
                    for (int j = 0; j < fieldValueList.size(); j++) {
                        if (j != 0) {
                            bufferSql.append(",");
                        }
                        bufferSql.append("{" + i + "}");
                        i++;
                    }
                    bufferSql.append(" )");
                    values.addAll(fieldValueList);
                } else if (column.startsWith("date_range_") && fieldValue instanceof String && fieldValue.toString().contains("~")) {
                    // 时间范围查询
                    column = column.replaceFirst("date_range_", "");
                    String[] split = ((String) fieldValue).split("~");
                    if (StringUtils.isNotEmpty(split[0])) {
                        bufferSql.append(column + " >= {" + i + "}");
                        values.add(split[0]);
                        i++;
                    }
                    if (StringUtils.isNotEmpty(split[1])) {
                        if (StringUtils.isNotEmpty(split[0])) {
                            bufferSql.append(" and ");
                        }
                        bufferSql.append(column + " <= {" + i + "}");
                        values.add(split[1]);
                    }

                } else {
                    bufferSql.append(column + " = {" + i + "}");
                    values.add(fieldValue);
                }
                i++;
            }
        }
        if (bufferSql.length() != 0) {
            wrapper.apply(bufferSql.toString().replaceFirst("and", ""), values.toArray());
        }
    }

    /**
     * 获取泛型类型
     *
     * @return {@link Class<?>}
     * @author liuyongtao
     * @since 2021-8-12 15:00
     */
    protected Class<?> getClass(int index) {
        return ReflectionKit.getSuperClassGenericType(this.getClass(), index);
    }
}

