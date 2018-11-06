package com.billow.product.remote;

import com.billow.tools.resData.BaseResponse;
import com.billow.product.pojo.re.WhiteListRe;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "learn-shop-admin-system", fallback = AdminSystemHystric.class)
public interface AdminSystemRemote {

    @GetMapping("/whiteList/findWhiteListVos/{ip}/{module}/{validInd}")
    BaseResponse<List<WhiteListRe>> findWhiteListVos(@PathVariable("ip") String ip,
                                                     @PathVariable("module") String module,
                                                     @PathVariable("validInd") boolean validInd);
}
