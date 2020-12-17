//package com.billow.gateway.filter;
//
//import com.billow.gateway.redis.RedisUtils;
//import com.billow.tools.constant.RedisCst;
//import com.billow.tools.utlis.ToolsUtils;
//import com.billow.tools.utlis.UserTools;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * 黑名单-修改用户后重新登陆
// *
// * @author liuyongtao
// * @create 2019-07-31 16:52
// */
//@Slf4j
//@Component
//public class BlacklistEditUserFilter implements GlobalFilter, Ordered {
//
//    @Autowired
//    private RedisUtils redisUtils;
//    @Autowired
//    private UserTools userTools;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpResponse response = exchange.getResponse();
//        ServerHttpRequest request = exchange.getRequest();
//
//        String requestURI = request.getURI().getPath();
//        if (requestURI.contains("login")) {
//            return chain.filter(exchange);
//        }
//
//        String currentUserCode = userTools.getCurrentUserCode();
//        Map map = redisUtils.getObj(RedisCst.BLACKLIST_EDITUSER + currentUserCode, Map.class);
//        if (ToolsUtils.isEmpty(map)) {
//            log.error("没有查询到用户信息，需要重新登陆...");
//            response.setStatusCode(HttpStatus.PRECONDITION_FAILED);
//            return response.setComplete();
//        }
//
//        List<String> currentRoleCode = userTools.getCurrentRoleCode();
//        Object obj = map.get(RedisCst.BLACKLIST_EDITUSER_ROLECODES);
//        // 没有分配角色，不处理
//        if (obj == null && ToolsUtils.isEmpty(currentRoleCode)) {
//            return chain.filter(exchange);
//        }
//
//        // 都有角色时，判断角色是否相同
//        if (obj != null && ToolsUtils.isNotEmpty(currentRoleCode)) {
//            List<String> roleCodes = ((List<String>) obj);
//            // 需要重新登陆
//            if (roleCodes.size() != currentRoleCode.size()) {
//                log.error("用户角色信息修改，需要重新登陆...");
//                response.setStatusCode(HttpStatus.PRECONDITION_FAILED);
//                return response.setComplete();
//            }
//
//            List<String> collect = roleCodes.stream()
//                    .filter(f -> !currentRoleCode.contains(f))
//                    .collect(Collectors.toList());
//            // 需要重新登陆
//            if (ToolsUtils.isNotEmpty(collect)) {
//                log.error("用户角色信息修改，需要重新登陆...");
//                response.setStatusCode(HttpStatus.PRECONDITION_FAILED);
//                return response.setComplete();
//            }
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE + 9;
//    }
//}
