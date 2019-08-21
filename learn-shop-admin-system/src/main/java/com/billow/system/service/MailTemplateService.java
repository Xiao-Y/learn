package com.billow.system.service;

import com.billow.system.pojo.vo.MailTemplateVo;

import java.util.Map;

/**
 * 邮件模板服务
 *
 * @author liuyongtao
 * @create 2019-08-20 21:07
 */
public interface MailTemplateService {

    /**
     * 构建邮件类内容
     *
     * @param mailCode
     * @param parameter
     * @return com.billow.system.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 9:29
     */
    MailTemplateVo genMailContent(String mailCode, Map<String, String> parameter);

}
