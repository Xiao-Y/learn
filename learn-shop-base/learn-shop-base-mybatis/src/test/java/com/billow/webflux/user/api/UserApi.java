package com.billow.webflux.user.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.webflux.user.pojo.build.UserBuildParam;
import com.billow.webflux.user.pojo.vo.UserVo;
import com.billow.webflux.user.pojo.search.UserSearchParam;
import com.billow.webflux.user.pojo.po.UserPo;
import com.billow.webflux.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-12-14
 * @version v2.0
 */
@Slf4j
@Api(tags = {"UserApi"},value = "")
@RestController
@RequestMapping("/userApi")
public class UserApi extends HighLevelApi<UserService, UserPo, UserVo, UserBuildParam, UserSearchParam> {

}
