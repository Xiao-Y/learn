package com.billow.zuul.component;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 当前请求的服务不存在时，返回友好信息
 *
 * @author liuyongtao
 * @create 2018-05-12 17:39
 */
@Component
public class ServerFallback implements FallbackProvider {

    private final Logger logger = LoggerFactory.getLogger(ServerFallback.class);

    /**
     * 对所有微服务处理
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/5/12 17:57
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        if (cause != null) {
            String reason = cause.getMessage();
            if (reason != null && reason.length() > 0) {
                String[] split = reason.split(":");
                logger.error("找不到{} 服务：{}", split[1], reason);
            } else if (cause.getCause() != null) {
                logger.error("系统异常：{}", cause.getCause().getLocalizedMessage());
            }
        }
        logger.error("系统未知异常...");
        return new ClientHttpResponse() {

            /**
             * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的，
             * 不应该把api的404,500等问题抛给客户端
             * 网关和api服务集群对于客户端来说是黑盒子
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return getStatusCode().getReasonPhrase();
            }

            @Override
            public InputStream getBody() throws IOException {
                String resTimestamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resCode", "9999");
                jsonObject.put("resMsg", "系统服务异常，请求失败");
                jsonObject.put("resTimestamp", resTimestamp);
                return new ByteArrayInputStream(jsonObject.toJSONString().getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //和body中的内容编码一致，否则容易乱码
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }

            @Override
            public void close() {

            }
        };
    }
}
