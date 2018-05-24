package com.billow.common.whiteList;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.billow.common.enums.RdsKey;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.common.whiteList.service.WhiteListService;
import com.billow.pojo.vo.sys.WhiteListVo;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    private WhiteListService whiteListService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = ServletUtil.getClientIP(request);
        StringBuilder key = new StringBuilder(RdsKey.WHITE_LIST.getKey());
        key.append(springApplicationName);
        key.append(":");
        key.append(clientIP);
        LOG.info("key：----> {}", key);

        List<WhiteListVo> whiteListVos;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String json = ops.get(key.toString());
        LOG.info("白名单：----> {}", json);

        if (ToolsUtils.isEmpty(json)) {
            whiteListVos = whiteListService.findByIpAndModuleAndValidInd(clientIP, springApplicationName, true);
            // 白名单放入到redis 中，设置失效时间
            ops.set(key.toString(), JSONObject.toJSONString(whiteListVos), 20, TimeUnit.SECONDS);
        } else {
            whiteListVos = JSONObject.parseObject(json, List.class);
        }

        // 模糊查询，获取所有的白名单
        Set<String> keys = redisTemplate.keys(RdsKey.WHITE_LIST.getKey() + "*");
        keys.forEach(item -> LOG.info("deleteKey：----> {}", item));
        // 删除所有查询出来的key （测试用）
        redisTemplate.delete(keys);

        // 再白名单里面，可以通过访问
        if (ToolsUtils.isNotEmpty(whiteListVos)) {
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
