package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsCommentBuildParam;
import com.billow.product.pojo.po.GoodsCommentPo;
import com.billow.product.pojo.search.GoodsCommentSearchParam;
import com.billow.product.pojo.vo.GoodsCommentVo;
import com.billow.product.service.GoodsCommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品评价表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsCommentApi"},value = "商品评价表")
@RestController
@RequestMapping("/goodsCommentApi")
public class GoodsCommentApi extends HighLevelApi<GoodsCommentService, GoodsCommentPo, GoodsCommentVo, GoodsCommentBuildParam, GoodsCommentSearchParam> {

}
