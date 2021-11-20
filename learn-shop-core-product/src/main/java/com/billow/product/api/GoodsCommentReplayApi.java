package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsCommentReplayBuildParam;
import com.billow.product.pojo.po.GoodsCommentReplayPo;
import com.billow.product.pojo.search.GoodsCommentReplaySearchParam;
import com.billow.product.pojo.vo.GoodsCommentReplayVo;
import com.billow.product.service.GoodsCommentReplayService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 产品评价回复表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsCommentReplayApi"},value = "产品评价回复表")
@RestController
@RequestMapping("/goodsCommentReplayApi")
public class GoodsCommentReplayApi extends HighLevelApi<GoodsCommentReplayService, GoodsCommentReplayPo, GoodsCommentReplayVo, GoodsCommentReplayBuildParam, GoodsCommentReplaySearchParam> {

}
