package com.billow.common.whiteList;

import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.sys.WhiteListVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * @param whiteListVo ip,module,validind
     * @return java.util.List<com.billow.pojo.po.sys.WhiteListVo>
     * @author LiuYongTao
     * @date 2018/5/19 14:27
     */
    @GetMapping(value = "/whiteList/findWhiteListVos")
    BaseResponse<List<WhiteListVo>> findWhiteListVos(@RequestBody WhiteListVo whiteListVo);
}
