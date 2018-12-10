package com.billow.auth.security.exception;

import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义验证不通过异常信息
 *
 * @author liuyongtao
 * @create 2018-11-20 16:20
 */
public class TokenOauthExceptionSerializer extends StdSerializer<TokenOauthException> {
    public TokenOauthExceptionSerializer() {
        super(TokenOauthException.class);
    }

    @Override
    public void serialize(TokenOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        baseResponse.setResCode(ResCodeEnum.RESCODE_SIGNATURE_ERROR.getStatusCode());
        baseResponse.setResMsg(ResCodeEnum.RESCODE_SIGNATURE_ERROR.getStatusName());

        Map<String, Object> jsonObject = new HashMap<>();
//        jsonObject.put("httpStatus", HttpStatus.BAD_REQUEST);
//        jsonObject.put("errorCode", HttpStatus.BAD_REQUEST.value());
//        jsonObject.put("message", value.getLocalizedMessage());
//        jsonObject.put("path", request.getServletPath());

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
