package com.billow.aop.aspect;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Slf4j
public class OperationLogsAspect {

    @Pointcut("@annotation(com.billow.aop.annotation.OperationLog)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object operationLogController(ProceedingJoinPoint point) throws Throwable {

        log.info("/**************请求开始*********************/");
        long startTime = System.currentTimeMillis();
        //拦截目标
        Object[] args = point.getArgs();
        String clsName = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        log.info("请求【{} {}】, 参数:{}.", clsName, methodName, getParam(point));
        try {
            return point.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("总耗时：{}", endTime - startTime);
            log.info("/**************请求结束********************/");
        }
    }

    /**
     * 组装请求参数
     *
     * @param point
     * @return
     */
    private String getParam(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = point.getArgs();
        int i = 0;
        List<String> paramList = new ArrayList<>();
        for (Object pojo : args) {
            if (pojo instanceof HttpServletRequest || pojo instanceof HttpServletResponse) {
                continue;
            }
            String e = parameterNames[i++] + ":" + JSON.toJSONString(pojo);
            paramList.add(e);
        }
        return String.format("[%s]", String.join(",", paramList));
    }

}
