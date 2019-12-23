package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.GoodsSpecKeyDao;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.vo.GoodsSpecKeyVo;
import com.billow.product.service.GoodsSpecKeyService;
import com.billow.tools.generator.NumUtil;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 规格表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSpecKeyServiceImpl extends ServiceImpl<GoodsSpecKeyDao, GoodsSpecKeyPo> implements GoodsSpecKeyService {

    @Autowired
    private GoodsSpecKeyDao goodsSpecKeyDao;

    @Override
    public IPage<GoodsSpecKeyPo> findListByPage(GoodsSpecKeyVo goodsSpecKeyVo) {
        IPage<GoodsSpecKeyPo> page = new Page<>(goodsSpecKeyVo.getPageNo(), goodsSpecKeyVo.getPageSize());
        LambdaQueryWrapper<GoodsSpecKeyPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<GoodsSpecKeyPo> selectPage = goodsSpecKeyDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        GoodsSpecKeyPo po = new GoodsSpecKeyPo();
        po.setValidInd(false);
        LambdaQueryWrapper<GoodsSpecKeyPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpecKeyPo::getId, id);
        return goodsSpecKeyDao.update(po, wrapper) >= 1;
    }

    @Override
    public List<GoodsSpecKeyPo> findListByCategoryId(String categoryId) {
        LambdaQueryWrapper<GoodsSpecKeyPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GoodsSpecKeyPo::getCategoryId, categoryId);
        wrapper.eq(GoodsSpecKeyPo::getValidInd, true);
        return goodsSpecKeyDao.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GoodsSpecKeyVo> saveList(List<GoodsSpecKeyVo> goodsSpecKeyVos) {
        for (GoodsSpecKeyVo goodsSpecKeyVo : goodsSpecKeyVos) {
            String id = goodsSpecKeyVo.getId();
            if (ToolsUtils.isEmpty(id)) {
                goodsSpecKeyVo.setSpecNo(NumUtil.makeNum("SP"));
                GoodsSpecKeyPo convert = ConvertUtils.convert(goodsSpecKeyVo, GoodsSpecKeyPo.class);
                goodsSpecKeyDao.insert(convert);
                ConvertUtils.convert(convert, goodsSpecKeyVo);
            } else {
                GoodsSpecKeyPo convert = ConvertUtils.convert(goodsSpecKeyVo, GoodsSpecKeyPo.class);
                goodsSpecKeyDao.updateById(convert);
                ConvertUtils.convert(convert, goodsSpecKeyVo);
            }
        }
        return goodsSpecKeyVos;
    }
}

