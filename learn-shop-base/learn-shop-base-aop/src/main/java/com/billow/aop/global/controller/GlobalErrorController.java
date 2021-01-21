//package com.billow.aop.global.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.billow.tools.enums.ResCodeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
//import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
//import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by billow.
// *
// * @author: bilow
// * @version: 1.0
// * @date: 2019/5/27 14:36
// * @apiNote: 全局异常处理(用于在还未进入到 controller 中发生的异常)
// * @see BasicErrorController
// */
//@Slf4j
//@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
//public class GlobalErrorController extends AbstractErrorController {
//
//    private final ErrorProperties errorProperties;
//
//    /**
//     * Create a new {@link BasicErrorController} instance.
//     *
//     * @param errorAttributes the error attributes
//     * @param errorProperties configuration properties
//     */
//    public GlobalErrorController(ErrorAttributes errorAttributes,
//                                 ErrorProperties errorProperties) {
//        this(errorAttributes, errorProperties,
//                Collections.<ErrorViewResolver>emptyList());
//    }
//
//    /**
//     * Create a new {@link BasicErrorController} instance.
//     *
//     * @param errorAttributes    the error attributes
//     * @param errorProperties    configuration properties
//     * @param errorViewResolvers error view resolvers
//     */
//    public GlobalErrorController(ErrorAttributes errorAttributes,
//                                 ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
//        super(errorAttributes, errorViewResolvers);
//        Assert.notNull(errorProperties, "ErrorProperties must not be null");
//        this.errorProperties = errorProperties;
//    }
//
//    @Override
//    public String getErrorPath() {
//        return this.errorProperties.getPath();
//    }
//
//    @RequestMapping(produces = "text/html")
//    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
//        HttpStatus status = getStatus(request);
//        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
//                request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
//        log.error(JSONObject.toJSONString(model));
//        response.setStatus(status.value());
//        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
//        return (modelAndView == null ? new ModelAndView("error", model) : modelAndView);
//    }
//
//    @RequestMapping
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
//        HttpStatus status = getStatus(request);
//        if (HttpStatus.NOT_FOUND.equals(status)) {
//            body.put("resCode", ResCodeEnum.RESCODE_NULL_RESULT);
//        } else {
//            body.put("resCode", ResCodeEnum.RESCODE_FORBIDDEN);
//        }
//        log.error(JSONObject.toJSONString(body));
//        return new ResponseEntity<>(body, HttpStatus.OK);
//    }
//
//    /**
//     * Determine if the stacktrace attribute should be included.
//     *
//     * @param request  the source request
//     * @param produces the media type produced (or {@code MediaType.ALL})
//     * @return if the stacktrace attribute should be included
//     */
//    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
//        IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
//        if (include == IncludeStacktrace.ALWAYS) {
//            return true;
//        }
//        if (include == IncludeStacktrace.ON_TRACE_PARAM) {
//            return getTraceParameter(request);
//        }
//        return false;
//    }
//
//    /**
//     * Provide access to the error properties.
//     *
//     * @return the error properties
//     */
//    protected ErrorProperties getErrorProperties() {
//        return this.errorProperties;
//    }
//}
