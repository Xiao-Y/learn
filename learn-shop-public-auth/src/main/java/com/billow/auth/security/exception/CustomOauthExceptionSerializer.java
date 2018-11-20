package com.billow.auth.security.exception;

import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
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

        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        baseResponse.setResCode(ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusCode());


        gen.writeStartObject();
        gen.writeStringField("resCode", ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusCode());
        gen.writeStringField("resMsg", ResCodeEnum.RESCODE_NOT_FOUND_USER.getStatusName());

        gen.writeStringField("message", value.getLocalizedMessage());

        gen.writeStringField("error", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("timestamp", String.valueOf(new Date().getTime()));
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
