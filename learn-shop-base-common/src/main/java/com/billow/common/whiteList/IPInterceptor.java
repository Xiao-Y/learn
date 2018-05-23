package com.billow.common.whiteList;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.common.whiteList.service.WhiteListService;
import com.billow.pojo.vo.sys.WhiteListVo;
import com.billow.tools.utlis.SpringContextUtil;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * IP拦截器
 *
 * @author liuyongtao
 * @create 2018-05-19 12:56
 */
public class IPInterceptor implements HandlerInterceptor {

    public final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    private String springApplicationName;

//    @Autowired
//    private WhiteListService whiteListService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = ServletUtil.getClientIP(request);
        LOG.info("clientIP:{}", clientIP);
        LOG.info("springApplicationName:{}", springApplicationName);
        WhiteListService whiteListService = SpringContextUtil.getBean("whiteListServiceImpl");
        List<WhiteListVo> whiteListVos = whiteListService.findByIpAndModuleAndValidInd(clientIP, springApplicationName, true);

        // 再白名单里面，可以通过访问
        if (ToolsUtils.isNotEmpty(whiteListVos)) {
            whiteListVos.stream().forEach(item -> {
                LOG.info("item:{}", item);
            });
            return true;
        }

        //必须返回200, 否则远程调用出现异常
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        BaseResponse<String> baseResponse = new BaseResponse<>(ResCodeEnum.RESCODE_FORBIDDEN.getStatusCode());
        baseResponse.setTraceID(springApplicationName);
        response.getWriter().write(JSONObject.toJSONString(baseResponse));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
