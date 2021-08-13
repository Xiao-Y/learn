package com.billow.seckill.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.billow.mybatis.base.HighLevelService;
import com.billow.mybatis.pojo.BasePage;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 高级公用方法
 *
 * @author liuyongtao
 * @since 2021-8-12 14:23
 */
@Slf4j
public class HighLevelApi<S extends HighLevelService<E, V>, E, V extends BasePage> {

    @Autowired
    private S service;

    // 实体类
    protected Class<E> eClass = (Class<E>) this.getClass(1);
    // vo 类型
    protected Class<V> vClass = (Class<V>) this.getClass(2);

    @ApiOperation(value = "分页查询表数据")
    @PostMapping(value = "/list")
    public IPage<E> findListByPage(@RequestBody V v) {
        return service.findListByPage(v);
    }

    @ApiOperation(value = "根据id查询表数据")
    @GetMapping(value = "/{id}")
    public V getById(@PathVariable("id") Long id) {
        E po = service.getById(id);
        return ConvertUtils.convert(po, vClass);
    }

    @ApiOperation(value = "新增表数据")
    @PostMapping
    public V add(@RequestBody V v) {
        E po = ConvertUtils.convert(v, eClass);
        service.save(po);
        return ConvertUtils.convert(po, vClass);
    }

    @ApiOperation(value = "删除表数据")
    @DeleteMapping(value = "/{id}")
    public boolean delById(@PathVariable("id") Long id) {
        return service.removeById(id);
    }

    @ApiOperation(value = "更新表数据")
    @PutMapping
    public V update(@RequestBody V v) {
        E po = ConvertUtils.convert(v, eClass);
        service.updateById(po);
        return ConvertUtils.convert(po, vClass);
    }

    @ApiOperation("根据ID禁用表数据")
    @PutMapping("/prohibit/{id}")
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
