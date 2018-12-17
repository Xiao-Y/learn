package com.billow.auth.security.config;

import com.billow.tools.enums.ResCodeEnum;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.Serializable;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2018-12-12 11:32
 */
public class CustomOAuth2AccessToken<T> extends DefaultOAuth2AccessToken implements Serializable {

    private String resTimestamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
    private String resCode;
    private String resMsg;
    private String traceID;
    private String spanID;
    private String signature;
    private T resData;
    private Map<String, String> ext;

    public CustomOAuth2AccessToken(OAuth2AccessToken accessToken) {
        super(accessToken);
    }

    public Map<String, String> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

    public String getResTimestamp() {
        return this.resTimestamp;
    }

    public void setResTimestamp(String resTimestamp) {
        this.resTimestamp = resTimestamp;
    }

    public String getResCode() {
        return this.resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
        this.resMsg = ResCodeEnum.getResCodeEnum(resCode);
    }

    public String getResMsg() {
        return this.resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getTraceID() {
        return this.traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getSpanID() {
        return this.spanID;
    }

    public void setSpanID(String spanID) {
        this.spanID = spanID;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public T getResData() {
        return this.resData;
    }

    public void setResData(T resData) {
        this.resData = resData;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resTimestamp='" + resTimestamp + '\'' +
                ", resCode='" + resCode + '\'' +
                ", resMsg='" + resMsg + '\'' +
                ", traceID='" + traceID + '\'' +
                ", spanID='" + spanID + '\'' +
                ", signature='" + signature + '\'' +
                ", resData=" + resData +
                ", ext=" + ext +
                '}';
    }

}
