package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSpecValueBuildParam;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.search.GoodsSpecValueSearchParam;
import com.billow.product.pojo.vo.GoodsSpecValueVo;
import com.billow.product.service.GoodsSpecValueService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 规格值表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-27
 * @version v1.0
 */
@Slf4j
@Api(tags = {"GoodsSpecValueApi"},value = "规格值表")
@RestController
@RequestMapping("/goodsSpecValueApi")
public class GoodsSpecValueApi extends HighLevelApi<GoodsSpecValueService, GoodsSpecValuePo, GoodsSpecValueVo, GoodsSpecValueBuildParam, GoodsSpecValueSearchParam>
{

    @Autowired
    private GoodsSpecValueService goodsSpecValueService;

    @ApiOperation(value = "通过 SpecKeyId 查询出所有的规格 Value")
    @GetMapping(value = "/findListBySpecKeyId/{specKeyId}")
    public List<GoodsSpecValueVo> findListBySpecKeyId(@PathVariable("specKeyId") Long specKeyId) {
        return goodsSpecValueService.findListBySpecKeyId(specKeyId);
    }
}
