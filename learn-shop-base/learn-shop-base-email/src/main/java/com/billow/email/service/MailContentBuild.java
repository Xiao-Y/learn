package com.billow.email.service;

import com.billow.email.pojo.vo.MailTemplateVo;

import java.util.Map;

public interface MailContentBuild {

    String build(MailTemplateVo mailTemplateVo, Map<String, Object> parameter) throws Exception;
}
