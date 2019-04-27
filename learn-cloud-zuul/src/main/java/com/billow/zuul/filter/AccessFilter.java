package com.billow.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 */
////@Component
//public class AccessFilter extends ZuulFilter {
//
//    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);
//
//    /**
//     * 过滤器的类型 pre表示请求在路由之前被过滤
//     *
//     * @return 类型
//     */
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    /**
//     * 过滤器的执行顺序
//     *
//     * @return 顺序 数字越大表示优先级越低，越后执行
//     */
//    @Override
//    public int filterOrder() {
//        return FilterConstants.SEND_ERROR_FILTER_ORDER;
//    }
//
//    /**
//     * 过滤器是否会被执行
//     *
//     * @return true
//     */
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    /**
//     * 过滤逻辑
//     *
//     * @return 过滤结果
//     */
//    @Override
//    public Object run() {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//
//        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
//
//        String auth = request.getHeader("Authorization");
//        boolean haveOauth2Token = auth == null;
//        String accessToken = request.getParameter("access_token");
//        boolean haveAccessToken = accessToken == null;
//        if (haveOauth2Token && haveAccessToken) {
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(401);
//            if (haveOauth2Token) {
//                logger.warn("Authorization token is empty");
//                requestContext.setResponseBody("Authorization token is empty");
//            } else if (haveAccessToken) {
//                logger.warn("Access token is empty");
//                requestContext.setResponseBody("Access token is empty");
//            }
//        }
//
//        if (!haveOauth2Token) {
//            logger.info("Authorization token is ok.{}", auth);
//        } else if (!haveAccessToken) {
//            logger.info("Access token is ok.{}", accessToken);
//        }
//        return null;
//    }
//}