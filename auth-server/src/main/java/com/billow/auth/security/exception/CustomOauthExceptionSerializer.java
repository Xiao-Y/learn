package com.billow.auth.security.exception;

import com.billow.auth.supper.BaseResponse;
import com.billow.auth.supper.ResCodeEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录失败异常信息
 *
 * @author liuyongtao
 * @create 2018-11-20 16:20
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        if (HttpStatus.UNAUTHORIZED.value() == value.getHttpErrorCode()) {
            baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusCode());
            baseResponse.setResMsg(ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusName());
        } else if (HttpStatus.BAD_REQUEST.value() == value.getHttpErrorCode()) {
            if ("Missing grant type".equals(value.getLocalizedMessage())) {
                baseResponse.setResCode(ResCodeEnum.RESCODE_BAD_REQUEST.getStatusCode());
                baseResponse.setResMsg(ResCodeEnum.RESCODE_BAD_REQUEST.getStatusName());
            } else {
                baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusCode());
                baseResponse.setResMsg(ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusName());
            }
        } else {
            baseResponse.setResCode(ResCodeEnum.RESCODE_OTHER_ERROR.getStatusCode());
            baseResponse.setResMsg(ResCodeEnum.RESCODE_OTHER_ERROR.getStatusName());
        }

        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("errorCode", value.getHttpErrorCode());
        jsonObject.put("reasonPhrase", HttpStatus.valueOf(value.getHttpErrorCode()).getReasonPhrase());
        jsonObject.put("message", value.getLocalizedMessage());
        jsonObject.put("path", request.getServletPath());

        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonObject.put(key, add);
            }
        }
        baseResponse.setResData(jsonObject);

        gen.writeObject(baseResponse);
    }
}
