package com.billow.search.api;

import com.billow.aop.commons.CustomPage;
import com.billow.search.common.cons.EsIndexConstant;
import com.billow.search.pojo.po.GoodsInfoPo;
import com.billow.search.pojo.search.GoodsInfoSearchParam;
import com.billow.search.service.GoodsInfoService;
import com.billow.search.utils.LambdaEsQueryWrapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品搜索相关操作
 *
 * @author liuyongtao
 * @since 2021-9-2 15:43
 */
@Slf4j
@RestController
@RequestMapping("/goodsInfoApi")
public class TestGoodsInfoApi {

//    @Autowired
    private ElasticsearchClient client;

    @PostMapping("/build")
    public String build(@RequestBody GoodsInfoSearchParam param) throws Exception {
        // 示例1：使用基础版本
        LambdaEsQueryWrapper<GoodsInfoSearchParam> wrapper = new LambdaEsQueryWrapper<>();
        // 设置一些查询参数
        param.setSpuNo("SPU123");
        param.setBrandId(1L);

        // 自动构建查询条件
        LambdaEsQueryWrapper<GoodsInfoSearchParam> queryWrapper = LambdaEsQueryWrapperUtils.buildQueryWrapper(wrapper, param);


        return null;
    }
}
