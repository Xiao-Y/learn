package com.billow.zuul.filter;

import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.tools.utlis.UserTools;
import com.billow.zuul.redis.RedisUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 黑名单-修改用户后重新登陆
 *
 * @author liuyongtao
 * @create 2019-07-31 16:52
 */
@Component
public class BlacklistEditUserFilter extends ZuulFilter {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserTools userTools;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.DEBUG_FILTER_ORDER - 9;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String requestURI = request.getRequestURI();
        if(requestURI.contains("login")){
            return false;
        }
        return true;
    }

    @Override
    public Object run() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.setSendZuulResponse(true);
        requestContext.setResponseStatusCode(200);

        String currentUserCode = userTools.getCurrentUserCode();
        Map map = redisUtils.getObj(RedisCst.BLACKLIST_EDITUSER + currentUserCode, Map.class);
        if (ToolsUtils.isEmpty(map)) {
            return null;
        }

        List<String> currentRoleCode = userTools.getCurrentRoleCode();
        Object obj = map.get(RedisCst.BLACKLIST_EDITUSER_ROLECODES);
        // 没有分配角色，不处理
        if (obj == null && ToolsUtils.isEmpty(currentRoleCode)) {
            return null;
        }

        // 都有角色时，判断角色是否相同
        if (obj != null && ToolsUtils.isNotEmpty(currentRoleCode)) {
            List<String> roleCodes = ((List<String>) obj);
            // 需要重新登陆
            if (roleCodes.size() != currentRoleCode.size()) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(412);
                requestContext.setResponseBody("用户信息修改，需要重新登陆...");
                return null;
            }

            List<String> collect = roleCodes.stream()
                    .filter(f -> !currentRoleCode.contains(f))
                    .collect(Collectors.toList());
            // 需要重新登陆
            if (ToolsUtils.isNotEmpty(collect)) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(412);
                requestContext.setResponseBody("用户信息修改，需要重新登陆...");
                return null;
            }
        }
        return null;
    }
}
