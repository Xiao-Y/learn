package com.billow.product.interfaces.api;

import com.billow.product.interfaces.constant.ContextPath;
import com.billow.product.interfaces.vo.GoodsBrandVo;
import com.billow.tools.resData.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsBrandApi {

    /**
     * 根据id查询品牌表数据
     *
     * @param id
     * @return {@link GoodsBrandVo}
     * @author xiaoy
     * @since 2021/2/4 16:20
     */
    @GetMapping(value = ContextPath.CORE_PRODUCT + "/goodsBrandApi/getById/{id}")
    BaseResponse<GoodsBrandVo> getBrandById(@PathVariable("id") Long id);
}
