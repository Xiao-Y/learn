package com.billow.security.auth.token.extractor;

/**
 * 实现这个接口应该返回原Base-64编码
 * 表示Token
 */
public interface TokenExtractor {
    /**
     * 返回去掉Bearer的token
     *
     * @param payload 原token
     * @return java.lang.String 去掉Bearer的token
     * @author LiuYongTao
     * @date 2018/5/16 17:56
     */
    String extract(String payload);
}