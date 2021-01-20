package com.billow.aop.global.advice;

import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by billow.
 *
 * @author: bilow
 * @version: 1.0
 * @date: 2019/5/25 12:40
 * @apiNote: 统一返回异常数据格式
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s ,Detail: %s";

    //运行时异常
    @ExceptionHandler(GlobalException.class)
    public BaseResponse runtimeExceptionHandler(GlobalException ex) {
        return resultFormat(ex.getResCodeEnum(), ex);
    }

//    //运行时异常
//    @ExceptionHandler(RuntimeException.class)
//    public BaseResponse runtimeExceptionHandler(RuntimeException ex) {
//        return resultFormat(1, ex);
//    }
//
//    //空指针异常
//    @ExceptionHandler(NullPointerException.class)
//    public BaseResponse nullPointerExceptionHandler(NullPointerException ex) {
//        return resultFormat(2, ex);
//    }
//
//    //类型转换异常
//    @ExceptionHandler(ClassCastException.class)
//    public BaseResponse classCastExceptionHandler(ClassCastException ex) {
//        return resultFormat(3, ex);
//    }
//
//    //IO异常
//    @ExceptionHandler(IOException.class)
//    public BaseResponse iOExceptionHandler(IOException ex) {
//        return resultFormat(4, ex);
//    }
//
//    //未知方法异常
//    @ExceptionHandler(NoSuchMethodException.class)
//    public BaseResponse noSuchMethodExceptionHandler(NoSuchMethodException ex) {
//        return resultFormat(5, ex);
//    }
//
//    //数组越界异常
//    @ExceptionHandler(IndexOutOfBoundsException.class)
//    public BaseResponse indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
//        return resultFormat(6, ex);
//    }
//
//    //400错误
//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    public BaseResponse requestNotReadable(HttpMessageNotReadableException ex) {
//        log.info("400..requestNotReadable");
//        return resultFormat(7, ex);
//    }
//
//    //400错误
//    @ExceptionHandler({TypeMismatchException.class})
//    public BaseResponse requestTypeMismatch(TypeMismatchException ex) {
//        log.info("400..TypeMismatchException");
//        return resultFormat(8, ex);
//    }
//
//    //400错误
//    @ExceptionHandler({MissingServletRequestParameterException.class})
//    public BaseResponse requestMissingServletRequest(MissingServletRequestParameterException ex) {
//        log.info("400..MissingServletRequest");
//        return resultFormat(9, ex);
//    }
//
//    //405错误
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    public BaseResponse request405(HttpRequestMethodNotSupportedException ex) {
//        return resultFormat(10, ex);
//    }
//
//    //406错误
//    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
//    public BaseResponse request406(HttpMediaTypeNotAcceptableException ex) {
//        log.info("406...");
//        return resultFormat(11, ex);
//    }
//
//    //500错误
//    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
//    public BaseResponse server500(RuntimeException ex) {
//        log.info("500...");
//        return resultFormat(12, ex);
//    }
//
//    //栈溢出
//    @ExceptionHandler({StackOverflowError.class})
//    public BaseResponse requestStackOverflow(StackOverflowError ex) {
//        return resultFormat(13, ex);
//    }

    //其他错误
    @ExceptionHandler({Exception.class})
    public BaseResponse exception(Exception ex) {
        return resultFormat(ResCodeEnum.RESCODE_OTHER_ERROR, ex);
    }

    private <T extends Throwable> BaseResponse resultFormat(ResCodeEnum resCodeEnum, T ex) {
        log.error(String.format(logExceptionFormat, resCodeEnum.getStatusCode(), resCodeEnum.getStatusName()));
        log.error("异常信息：", ex);
        BaseResponse baseResponse = new BaseResponse(resCodeEnum, ex.getMessage());
        return baseResponse;
    }

}
