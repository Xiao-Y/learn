package com.billow.auth.supper;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Map;


public class BaseResponse<T> implements Serializable {

    private String resTimestamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
    private String resCode;
    private String resMsg;
    private String traceID;
    private String spanID;
    private String signature;
    private T resData;
    private Map<String, String> ext;

    public BaseResponse() {
        this.resCode = ResCodeEnum.OK;
        this.resMsg = ResCodeEnum.OK_NAME;
    }

    public BaseResponse(String resCode, String resMsg, String traceID, String spanID) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.traceID = traceID;
        this.spanID = spanID;
    }

    public BaseResponse(String resCode, String traceID, String spanID) {
        this(resCode, ResCodeEnum.getResCodeEnum(resCode), traceID, spanID);
    }

    public BaseResponse(String resCode, String resMsg, T resData) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resData = resData;
    }

    public BaseResponse(String resCode) {
        this.resCode = resCode;
        this.resMsg = ResCodeEnum.getResCodeEnum(resCode);
    }

    public BaseResponse(String resCode, T resData) {
        this.resCode = resCode;
        this.resMsg = ResCodeEnum.getResCodeEnum(resCode);
        this.resData = resData;
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
