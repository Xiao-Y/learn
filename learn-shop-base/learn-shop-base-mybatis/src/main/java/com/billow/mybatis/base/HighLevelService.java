package com.billow.mybatis.base;

import com.billow.mybatis.pojo.BasePage;
import com.billow.mybatis.pojo.BasePo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 高级公用服务
 * <p>
 * E-OrderItemPo 实体类
 * <p>
 * SP-OrderItemSearchParam 查询对象 继承 BasePage
 *
 * @author liuyongtao
 * @since 2021-8-12 14:33
 */
public interface HighLevelService<E extends BasePo, SP extends BasePage> extends IService<E> {

    /**
     * 分页查询
     *
     * @param sp 查询条件
     * @return {@link Page<E>}
     * @author liuyongtao
     * @since 2021-8-13 10:35
     */
    Page<E> findListByPage(Page<E> page, SP sp);

    /**
     * 查询列表
     *
     * @param sp 查询条件
     * @return {@link List<E>}
     * @author liuyongtao
     * @since 2021-8-13 10:35
     */
    List<E> findList(SP sp);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2019-11-27
     */
    boolean prohibitById(Long id);
}
