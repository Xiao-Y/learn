package com.billow.product.interfaces.api;

import com.billow.product.interfaces.constant.ContextPath;
import com.billow.product.interfaces.vo.GoodsBrandVo;
import com.billow.product.interfaces.vo.GoodsCategoryVo;
import com.billow.tools.resData.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@RestController
@RequestMapping(ContextPath.CORE_PRODUCT + "/goodsCategoryApi")
public interface GoodsCategoryApi {

    /**
     * 根据id查询品牌表数据
     *
     * @param id
     * @return {@link GoodsCategoryVo}
     * @author xiaoy
     * @since 2021/2/4 16:20
     */
    @GetMapping(value = "/getById/{id}")
    BaseResponse<GoodsCategoryVo> getCategoryById(@PathVariable("id") Long id);
}
