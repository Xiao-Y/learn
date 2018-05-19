package com.billow.common.whiteList;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.sys.WhiteListVo;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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

    @Autowired
    private AdminSystemRemote adminSystemRemote;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = ServletUtil.getClientIP(request);
        LOG.info("clientIP:{}", clientIP);
        LOG.info("springApplicationName:{}", springApplicationName);

        // 如果是system系统的由system自己控制
//        if (!"learn-shop-admin-system".equals(springApplicationName)) {
//            WhiteListVo vo = new WhiteListVo();
//            vo.setIp("127.0.0.1").setModule(springApplicationName).setValidInd(true);
//            BaseResponse<List<WhiteListVo>> baseRes = adminSystemRemote.findWhiteListVos(vo);
//            if (ResCodeEnum.OK.equals(baseRes.getResCode())) {
//                List<WhiteListVo> listVos = baseRes.getResData();
//                if (ToolsUtils.isNotEmpty(listVos)) {
//                    listVos.stream().forEach(item -> {
//                        LOG.info("item:{}", item);
//                    });
//                }
//                return true;
//            } else {
//                response.setContentType("application/json; charset=utf-8");
//                PrintWriter writer = response.getWriter();
//                writer.print(JSONObject.toJSONString(baseRes, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
//                writer.close();
//                response.flushBuffer();
//                return false;
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
