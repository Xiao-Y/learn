package com.billow.mybatis.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.billow.mybatis.pojo.BasePage;
import com.billow.mybatis.utils.SqlUtil;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 高级公用方法
 * <p>
 * 例如：
 * S-OrderItemService 继承 HighLevelService
 * <p>
 * E-OrderItemPo 实体类
 * <p>
 * V-OrderItemVo 返回对象
 * <p>
 * BP-OrderItemBuildParam 新增对象
 * <p>
 * SP-OrderItemSearchParam 查询对象 继承 BasePage
 *
 * @author liuyongtao
 * @since 2021-8-12 14:23
 */
@Slf4j
public class HighLevelApi<S extends HighLevelService<E, SP>, E, V, BP, SP extends BasePage> {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private S service;

    // 实体类
    protected Class<E> eClass = (Class<E>) this.getClass(1);
    // vo 类型
    protected Class<V> vClass = (Class<V>) this.getClass(2);

    @ApiOperation(value = "分页查询表数据")
    @PostMapping(value = "/list")
    public IPage<E> findListByPage(@RequestBody SP sp) {
        // 分页
        Page<E> page = new Page<>(sp.getPageNo(), sp.getPageSize());
        // 排序
        if (StringUtils.isNotEmpty(sp.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(sp.getOrderBy());
            page.addOrder(new OrderItem(orderBy, sp.getIsAsc()));
        }
        return service.findListByPage(page, sp);
    }

    @ApiOperation(value = "根据id查询表数据")
    @GetMapping(value = "/getById/{id}")
    public V getById(@PathVariable("id") Long id) {
        E po = service.getById(id);
        return ConvertUtils.convert(po, vClass);
    }

    @ApiOperation(value = "新增表数据")
    @PostMapping(value = "/add")
    public V add(@RequestBody BP bp) {
        E po = ConvertUtils.convert(bp, eClass);
        service.save(po);
        return ConvertUtils.convert(po, vClass);
    }

    @ApiOperation(value = "删除表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") Long id) {
        return service.removeById(id);
    }

    @ApiOperation(value = "更新表数据")
    @PutMapping(value = "/update")
    public V update(@RequestBody SP sp) {
        E po = ConvertUtils.convert(sp, eClass);
        service.updateById(po);
        return ConvertUtils.convert(po, vClass);
    }

    @ApiOperation("根据ID禁用表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable Long id) {
        return service.prohibitById(id);
    }

    /**
     * 返回当前service
     *
     * @return {@link S}
     * @author liuyongtao
     * @since 2021-8-13 9:35
     */
    public S getService() {
        return service;
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
