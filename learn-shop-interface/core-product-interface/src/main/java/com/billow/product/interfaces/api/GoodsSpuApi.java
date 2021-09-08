package com.billow.product.interfaces.api;

import com.billow.product.interfaces.constant.ContextPath;
import com.billow.product.interfaces.vo.GoodsSpuVo;
import com.billow.tools.resData.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * spu表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@RestController
@RequestMapping(ContextPath.CORE_PRODUCT +"/goodsSpuApi")
public interface GoodsSpuApi {

    /**
     * 根据id查询spu表数据
     *
     * @param id
     * @return {@link GoodsSpuVo}
     * @author liuyongtao
     * @since 2021-9-7 14:22
     */
    @GetMapping(value = "/getById/{id}")
    BaseResponse<GoodsSpuVo> getById(@PathVariable("id") Long id);
}