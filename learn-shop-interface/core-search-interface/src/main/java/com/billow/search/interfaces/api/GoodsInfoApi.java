package com.billow.search.interfaces.api;

import com.billow.search.interfaces.commons.CustomPage;
import com.billow.search.interfaces.constant.ContextPath;
import com.billow.search.interfaces.search.GoodsInfoSearchParam;
import com.billow.tools.resData.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品搜索相关操作
 *
 * @author liuyongtao
 * @since 2021-9-2 15:43
 */
@RestController
@RequestMapping(ContextPath.CORE_SEARCH + "/goodsInfoApi")
public interface GoodsInfoApi {

    /**
     * 条件查询
     *
     * @param param    查询参数
     * @param pageSize 页面大小
     * @param pageNo   当前页
     * @return {@link CustomPage}
     * @author liuyongtao
     * @since 2021-9-8 18:54
     */
    @PostMapping("/search")
    BaseResponse<CustomPage> search(@RequestBody GoodsInfoSearchParam param,
                        @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo);

}
