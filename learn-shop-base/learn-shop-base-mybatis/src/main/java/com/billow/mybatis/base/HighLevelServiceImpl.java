package com.billow.mybatis.base;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.billow.mybatis.pojo.BasePage;
import com.billow.mybatis.pojo.BasePo;
import com.billow.mybatis.utils.MybatisKet;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
public abstract class HighLevelServiceImpl<M extends HighLevelMapper<E>, E extends BasePo, SP extends BasePage>
        extends ServiceImpl<M, E> implements HighLevelService<E, SP> {

    // 查询对象
    protected Class<E> eClass = (Class<E>) this.getClassByIndex(1);
    protected Class<SP> sPClass = (Class<SP>) this.getClassByIndex(2);

    @Override
    public Page<E> findListByPage(SP sp) {
        QueryWrapper wrapper = QueryWrapper.create();
        // 查询条件
        this.genQueryCondition(wrapper, sp);
        // 分页
        Page<E> page = new Page<>(sp.getPageNo(), sp.getPageSize());
        // 排序
        MybatisKet.addSortBy(sp, wrapper);
        return mapper.paginate(page, wrapper);
    }


    @Override
    public List<E> findList(SP sp) {
        QueryWrapper wrapper = QueryWrapper.create();
        // 查询条件
        this.genQueryCondition(wrapper, sp);
        // 排序
        MybatisKet.addSortBy(sp, wrapper);
        return this.list(wrapper);
    }

    @Override
    public boolean prohibitById(Long id) {
        E e = UpdateEntity.of(eClass, id);
        UpdateWrapper<E> updateWrapper = UpdateWrapper.of(e);
        updateWrapper.set("validInd", false);
        return mapper.update(e) > 0;
    }

    /**
     * 分页查询的查询条件
     *
     * @param wrapper
     * @param sp
     * @author liuyongtao
     * @since 2021-8-13 10:20
     */
    public void genQueryCondition(QueryWrapper wrapper, SP sp) {
        this.genQueryCondition(wrapper, sp, null);
    }

    /**
     * 分页查询的查询条件
     *
     * @param sp
     * @param tableAlias 表别名
     * @author liuyongtao
     * @since 2021-8-13 10:20
     */
    public void genQueryCondition(QueryWrapper queryWrapper, SP sp, String tableAlias) {
        // 排除字段
        List<String> excludedFields = Arrays.asList("pageSize", "pageNo", "orderBy", "isAsc");
        Filter<Field> filter = field -> {
            // 只取私有的，并且不在 excludedFields 中的字段
            if (Modifier.isPrivate(field.getModifiers())) {
                return !excludedFields.contains(field.getName());
            }
            return false;
        };
        // 获取查询字段
        Field[] fields = ReflectUtil.getFields(sPClass, filter);
        for (Field field : fields) {
            Object fieldValue = ReflectUtil.getFieldValue(sp, field);
            if (Objects.isNull(fieldValue)) {
                continue;
            }
            String column = Optional.ofNullable(tableAlias)
                    .filter(StringUtils::isNotBlank)
                    .map(m -> {
                        if (m.endsWith(".")) {
                            return m;
                        } else {
                            return m + ".";
                        }
                    })
                    .orElse("")
                    + StrUtil.toUnderlineCase(field.getName());
            // 使用 apply 方法动态添加条件
            if (fieldValue instanceof List && column.endsWith("list")) {
                List fieldValueList = (List) fieldValue;
                column = column.replaceFirst("_list", "");
                // 多个值查询
                queryWrapper.in(column, fieldValueList);
            } else if (column.startsWith("date_range_") && fieldValue instanceof String && fieldValue.toString().contains("~")) {
                // 时间范围查询
                column = column.replaceFirst("date_range_", "");
                String[] split = ((String) fieldValue).split("~");

                if (StringUtils.isNotEmpty(split[0])) {
                    queryWrapper.ge(column, split[0]);
                }
                if (StringUtils.isNotEmpty(split[1])) {
                    queryWrapper.le(column, split[1]);
                }

            } else {
                queryWrapper.eq(column, fieldValue);
            }
        }
    }

    /**
     * 获取泛型类型
     *
     * @return {@link Class<?>}
     * @author liuyongtao
     * @since 2021-8-12 15:00
     */
    protected Class<?> getClassByIndex(int index) {
//        return ReflectionKit.getSuperClassGenericType(this.getClass(), HighLevelServiceImpl.class, index);
        // 获取当前类的泛型父类类型（即HighLevelServiceImpl的泛型定义）
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        // 解析泛型参数，返回实际类型数组
        Type[] actualTypeArguments = TypeUtil.getTypeArguments(genericSuperclass);
        if (actualTypeArguments == null || index < 0 || index >= actualTypeArguments.length) {
            throw new IndexOutOfBoundsException("泛型参数索引超出范围: " + index);
        }
        // 将泛型参数类型转换为Class对象
        return TypeUtil.getClass(actualTypeArguments[index]);
    }
}

