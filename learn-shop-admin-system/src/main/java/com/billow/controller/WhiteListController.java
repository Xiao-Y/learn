package com.billow.controller;

import com.billow.common.base.BaseController;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.sys.WhiteListVo;
import com.billow.service.WhiteListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 白名单
 *
 * @author liuyongtao
 * @create 2018-05-19 14:35
 */
@Api(value = "WhiteListController", description = "白名单")
@RestController
@RequestMapping("/whiteList")
public class WhiteListController extends BaseController {

    @Autowired
    private WhiteListService whiteListService;

    @ApiOperation(value = "获取有效的白名单信息", notes = "根据ip和模块获取有效的白名单信息")
    @GetMapping("/findWhiteListVos")
    public BaseResponse<List<WhiteListVo>> findWhiteListVos(@RequestBody WhiteListVo whiteListVo) {
        BaseResponse<List<WhiteListVo>> baseResponse = new BaseResponse<>();
        try {
            List<WhiteListVo> whiteListVos = whiteListService.findByIpAndModuleAndValidInd(whiteListVo.getIp(), whiteListVo.getModule(), whiteListVo.getValidInd());
            baseResponse.setResData(whiteListVos);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResCode(ResCodeEnum.FAIL);
        }
        return baseResponse;
    }
}
