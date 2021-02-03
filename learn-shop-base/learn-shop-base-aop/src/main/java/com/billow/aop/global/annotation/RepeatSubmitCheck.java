package com.billow.aop.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验重复提交
 *
 * @author liuyongtao
 * @since 2021-2-2 14:31
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmitCheck {

}