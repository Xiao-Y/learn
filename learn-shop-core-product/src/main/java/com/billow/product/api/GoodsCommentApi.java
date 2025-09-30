package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.po.GoodsCommentPo;
import com.billow.product.pojo.search.GoodsCommentSearchParam;
import com.billow.product.service.GoodsCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品评价表 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsCommentApi", description = "商品评价表")
@RestController
@RequestMapping("/goodsCommentApi")
public class GoodsCommentApi extends HighLevelV2Api<GoodsCommentService, GoodsCommentPo, GoodsCommentSearchParam> {

}
