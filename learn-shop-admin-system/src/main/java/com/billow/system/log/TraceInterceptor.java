package com.billow.system.log;

import com.billow.common.thread.TraceLogUtils;
import com.billow.tools.constant.CommonCst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 向调用链路中添加 traceid
 *
 * @author xiaoy
 * @since 2021/2/4 11:45
 */
public class TraceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(CommonCst.HTTP_HEADER_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceLogUtils.getTraceId();
        }
        MDC.put(CommonCst.LOG_TRACE_ID, traceId);
        return true;
    }
}