package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSkuDao;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.dao.GoodsSpuDao;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.search.GoodsSpuSearchParam;
import com.billow.product.pojo.vo.GoodsSpuVo;
import com.billow.product.service.GoodsSkuSpecValueService;
import com.billow.product.service.GoodsSpuService;
import com.billow.tools.generator.NumUtil;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * spu表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Service
public class GoodsSpuServiceImpl extends HighLevelServiceImpl<GoodsSpuDao, GoodsSpuPo, GoodsSpuSearchParam> implements GoodsSpuService {

    @Autowired
    private GoodsSpuDao goodsSpuDao;
    @Autowired
    private GoodsSkuSpecValueDao goodsSkuSpecValueDao;
    @Autowired
    private GoodsSkuDao goodsSkuDao;
    @Autowired
    private GoodsSkuSpecValueService goodsSkuSpecValueService;

    @Override
    public void genQueryCondition(LambdaQueryWrapper<GoodsSpuPo> wrapper, GoodsSpuSearchParam goodsSpuSearchParam) {
        // 查询条件
        wrapper.eq(ToolsUtils.isNotEmpty(goodsSpuSearchParam.getSpuNo()), GoodsSpuPo::getSpuNo, goodsSpuSearchParam.getSpuNo())
                .like(ToolsUtils.isNotEmpty(goodsSpuSearchParam.getGoodsName()), GoodsSpuPo::getGoodsName, goodsSpuSearchParam.getGoodsName())
                .eq(ToolsUtils.isNotEmpty(goodsSpuSearchParam.getBrandId()), GoodsSpuPo::getBrandId, goodsSpuSearchParam.getBrandId())
                .eq(ToolsUtils.isNotEmpty(goodsSpuSearchParam.getCategoryId()), GoodsSpuPo::getCategoryId, goodsSpuSearchParam.getCategoryId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(GoodsSpuVo goodsSpuVo) {
        // 保存更新商品信息
        GoodsSpuPo po = ConvertUtils.convert(goodsSpuVo, GoodsSpuPo.class);
        Long id = goodsSpuVo.getId();
        if (ToolsUtils.isEmpty(id)) {
            po.setSpuNo(NumUtil.makeNum("PG"));
        }
        this.saveOrUpdate(po);

        List<Long> specKeys = goodsSpuVo.getSpecKeys();
        if (ToolsUtils.isNotEmpty(id)) {
            // 查询出原始的商品规格key
            List<Long> spuSpecKey = goodsSkuSpecValueService.findSpuSpecKey(id);
            // 获取需要删除的规格key
            List<Long> delSpecKeys = spuSpecKey.stream().filter(f -> !specKeys.contains(f)).collect(Collectors.toList());
            if (ToolsUtils.isNotEmpty(delSpecKeys)) {
                // 通过规格key 获取 所有涉及到的 sku
                LambdaQueryWrapper<GoodsSkuSpecValuePo> wrapper = Wrappers.lambdaQuery();
                wrapper.in(GoodsSkuSpecValuePo::getSpecKeyId, delSpecKeys)
                        .eq(GoodsSkuSpecValuePo::getValidInd, true);
                List<GoodsSkuSpecValuePo> skuSpecValuePos = goodsSkuSpecValueDao.selectList(wrapper);
                // 设置对应的 sku spec value 为无效
                wrapper = Wrappers.lambdaQuery();
                wrapper.in(GoodsSkuSpecValuePo::getSpecKeyId, delSpecKeys)
                        .eq(GoodsSkuSpecValuePo::getValidInd, true);
                GoodsSkuSpecValuePo skuSpecValuePo = new GoodsSkuSpecValuePo();
                skuSpecValuePo.setValidInd(false);
                goodsSkuSpecValueDao.update(skuSpecValuePo, wrapper);
                // 设置对应的 sku 为无效
                Set<Long> skuIds = skuSpecValuePos.stream().map(m -> m.getSkuId()).collect(Collectors.toSet());
                if(ToolsUtils.isNotEmpty(skuIds)){
                    LambdaQueryWrapper<GoodsSkuPo> wupdate = Wrappers.lambdaQuery();
                    wupdate.in(GoodsSkuPo::getId, skuIds);
                    GoodsSkuPo skuPo = new GoodsSkuPo();
                    skuPo.setValidInd(false);
                    goodsSkuDao.update(skuPo, wupdate);
                }
            }
//            // 删除原有的商品规格信息
//            LambdaQueryWrapper<GoodsSkuSpecValuePo> rwrapper = Wrappers.lambdaQuery();
//            rwrapper.eq(GoodsSkuSpecValuePo::getSpuId, id);
//            goodsSkuSpecValueService.remove(rwrapper);
        }
        // 重新保存商品规格信息
        specKeys.forEach(f -> {
            GoodsSkuSpecValuePo skuSpecValuePo = new GoodsSkuSpecValuePo();
            skuSpecValuePo.setSpuId(id);
            skuSpecValuePo.setSpecKeyId(f);
            goodsSkuSpecValueService.save(skuSpecValuePo);
        });
        ConvertUtils.convert(po, goodsSpuVo);
    }

}

