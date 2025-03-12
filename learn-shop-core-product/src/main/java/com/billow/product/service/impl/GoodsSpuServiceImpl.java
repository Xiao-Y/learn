package com.billow.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.excel.core.ExcelExporter;
import com.billow.excel.core.ExcelImporter;
import com.billow.excel.model.ImportResult;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSkuDao;
import com.billow.product.dao.GoodsSkuSpecValueDao;
import com.billow.product.dao.GoodsSpuDao;
import com.billow.product.pojo.excel.GoodsSpuExcel;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
@Slf4j
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
    @Autowired
    private ExcelExporter excelExporter;
    @Autowired
    private ExcelImporter excelImporter;

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
                if (ToolsUtils.isNotEmpty(skuIds)) {
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

    @Override
    public String asyncExport(HttpServletResponse response) {
        // 1. 查询所有商品SPU数据
        List<GoodsSpuPo> spuList = this.list();

        // 2. 转换为Excel对象
        List<GoodsSpuExcel> excelList = spuList.stream().map(spu -> {
            GoodsSpuExcel excel = new GoodsSpuExcel();
            // 复制基本属性
            BeanUtils.copyProperties(spu, excel);
            return excel;
        }).collect(Collectors.toList());

        // 3. 使用DefaultExcelExporter的exportAsync方法进行异步导出
        String taskId = excelExporter.exportAsync(excelList);

        // 可以在这里处理导出完成后的逻辑，例如记录日志或通知用户
        log.info("导出任务已提交，任务ID: {}", taskId);
        return taskId;
    }

    @Override
    public String asyncImport(MultipartFile file) throws ExecutionException, InterruptedException {
        Future<ImportResult<GoodsSpuExcel>> futureResult = excelImporter.importAsync(file, GoodsSpuExcel.class);
        // TODO 获取其它业务数据

        // 等待导入完成并获取结果
        ImportResult<GoodsSpuExcel> importResult = futureResult.get();

        // 获取成功导入的数据列表
        List<GoodsSpuExcel> dataList = importResult.getSuccessList();

        // 转换为PO对象并批量保存
        List<GoodsSpuPo> spuList = dataList.stream().map(excel -> {
            GoodsSpuPo spu = new GoodsSpuPo();
            // 复制基本属性
            BeanUtils.copyProperties(excel, spu);
            return spu;
        }).collect(Collectors.toList());

        this.saveBatch(spuList);
        return "导入成功";
    }
}

