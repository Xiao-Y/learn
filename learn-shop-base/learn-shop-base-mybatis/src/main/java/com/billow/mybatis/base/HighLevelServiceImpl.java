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
public abstract class HighLevelServiceImpl<M extends BaseMapper<E>, E, V extends BasePage> extends ServiceImpl<M, E> implements HighLevelService<E, V> {

    @Override
    public IPage<E> findListByPage(V v) {
        IPage<E> page = new Page<>(v.getPageNo(), v.getPageSize());
        LambdaQueryWrapper<E> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        this.genQueryCondition(wrapper, v);
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
     * @param v
     * @author liuyongtao
     * @since 2021-8-13 10:20
     */
    public void genQueryCondition(LambdaQueryWrapper<E> wrapper, V v) {
    }
}

