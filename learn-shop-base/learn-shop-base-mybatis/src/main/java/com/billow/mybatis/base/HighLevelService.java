package com.billow.mybatis.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.mybatis.pojo.BasePage;

/**
 * 高级公用服务
 *
 * @author liuyongtao
 * @since 2021-8-12 14:33
 */
public interface HighLevelService<E, SP extends BasePage> extends IService<E> {

    /**
     * 分页查询
     *
     * @param sp 查询条件
     * @return {@link IPage<E>}
     * @author liuyongtao
     * @since 2021-8-13 10:35
     */
    IPage<E> findListByPage(SP sp);

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
