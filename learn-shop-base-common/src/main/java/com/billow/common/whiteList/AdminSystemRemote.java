package com.billow.common.whiteList;

import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.sys.WhiteListVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用于连接admin-system系统
 *
 * @author LiuYongTao
 * @date 2018/5/19 14:06
 */
@FeignClient(value = "learn-shop-admin-system", fallback = AdminSystemHystric.class)
public interface AdminSystemRemote {

    /**
     * 根据ip和模块查询出有效白名单
     *
     * @param ip       访问ip
     * @param module   模块
     * @param validInd 有效
     * @return com.billow.common.resData.BaseResponse<java.util.List<com.billow.pojo.vo.sys.WhiteListVo>>
     * @author LiuYongTao
     * @date 2018/5/21 8:35
     */
    @GetMapping(value = "/whiteList/findWhiteListVos/{ip}/{module}/{validInd}")
    BaseResponse<List<WhiteListVo>> findWhiteListVos(@PathVariable("ip") String ip,
                                                     @PathVariable("module") String module,
                                                     @PathVariable("validInd") boolean validInd);
}
