package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.build.GoodsCommentReplayBuildParam;
import com.billow.product.pojo.po.GoodsCommentReplayPo;
import com.billow.product.pojo.search.GoodsCommentReplaySearchParam;
import com.billow.product.pojo.vo.GoodsCommentReplayVo;
import com.billow.product.service.GoodsCommentReplayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 产品评价回复表 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsCommentReplayApi", description = "产品评价回复表")
@RestController
@RequestMapping("/goodsCommentReplayApi")
public class GoodsCommentReplayApi extends HighLevelV2Api<GoodsCommentReplayService, GoodsCommentReplayPo, GoodsCommentReplaySearchParam> {

}
