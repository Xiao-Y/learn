package com.billow.mybatis.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.billow.mybatis.pojo.BasePage;
import com.billow.mybatis.pojo.BasePo;
import com.billow.mybatis.utils.SqlUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 高级公用方法
 * <p>
 * 例如：
 * S-OrderItemService 继承 HighLevelV2Service
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
public class HighLevelV2Api<S extends HighLevelV2Service<E, SP>, E extends BasePo, SP extends BasePage> {

    @Autowired
    protected HttpServletRequest request;

    @Getter
    @Autowired
    private S service;

    @Operation(summary = "分页查询表数据")
    @PostMapping(value = "/list")
    public IPage<E> findListByPage(@RequestBody SP sp) {
        // 分页
        Page<E> page = new Page<>(sp.getPageNo(), sp.getPageSize());
        // 排序
        if (StringUtils.isNotEmpty(sp.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(sp.getOrderBy());
            page.addOrder(OrderItem.asc(orderBy).setAsc(sp.getIsAsc()));
        }
        return service.findListByPage(page, sp);
    }

    @Operation(summary = "根据id查询表数据")
    @GetMapping(value = "/getById/{id}")
    public E getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @Operation(summary = "新增表数据")
    @PostMapping(value = "/add")
    public E add(@RequestBody E po) {
        service.save(po);
        return po;
    }

    @Operation(summary = "删除表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") Long id) {
        return service.removeById(id);
    }

    @Operation(summary = "更新表数据")
    @PutMapping(value = "/update/{id}")
    public E update(@PathVariable("id") Long id, @RequestBody E po) {
        po.setId(id);
        service.updateById(po);
        return po;
    }

    @Operation(summary = "根据ID禁用表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable Long id) {
        return service.prohibitById(id);
    }
}