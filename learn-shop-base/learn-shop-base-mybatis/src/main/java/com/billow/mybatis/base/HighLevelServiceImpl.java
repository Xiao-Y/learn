package com.billow.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.mybatis.pojo.BasePage;

/**
 * <p>
 * 高级公用服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public abstract class HighLevelServiceImpl<M extends BaseMapper<E>, E, SP extends BasePage> extends ServiceImpl<M, E> implements HighLevelService<E, SP> {

    @Override
    public IPage<E> findListByPage(SP sp) {
        IPage<E> page = new Page<>(sp.getPageNo(), sp.getPageSize());
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
    }
}

