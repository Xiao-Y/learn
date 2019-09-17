package com.billow.email.service;

import com.billow.email.pojo.vo.MailTemplateVo;
import org.springframework.data.domain.Page;

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
     * @param mailCode  模板CODE
     * @param parameter 模板或者查询参数
     * @return com.billow.system.com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 9:29
     */
    MailTemplateVo genMailContent(String mailCode, Map<String, String> parameter) throws Exception;

    /**
     * 根据条件查询邮件模板信息
     *
     * @param mailTemplateVo
     * @return org.springframework.data.domain.Page<com.billow.system.com.billow.email.pojo.vo.MailTemplateVo>
     * @author LiuYongTao
     * @date 2019/8/21 14:47
     */
    Page<MailTemplateVo> findMailTemplateList(MailTemplateVo mailTemplateVo);

    /**
     * 根据id获取邮件模板信息
     *
     * @param id
     * @return com.billow.system.com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 15:40
     */
    MailTemplateVo findMailTemplateById(Long id);

    /**
     * 根据ID删除邮件模板
     *
     * @param id
     * @return com.billow.system.com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 15:51
     */
    MailTemplateVo deleteMailTemplateById(Long id);

    /**
     * 根据ID禁用邮件模板
     *
     * @param id
     * @return com.billow.system.com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 15:51
     */
    MailTemplateVo prohibitMailTemplateById(Long id);

    /**
     * 添加邮件模板信息
     *
     * @param permissionVo
     * @return void
     * @author LiuYongTao
     * @date 2019/8/21 15:51
     */
    void saveMailTemplate(MailTemplateVo permissionVo);

    /**
     * 更新邮件模板信息
     *
     * @param permissionVo
     * @return void
     * @author LiuYongTao
     * @date 2019/8/21 15:52
     */
    void updateMailTemplate(MailTemplateVo permissionVo);

    /**
     * 查询 mailCode 的个数
     *
     * @param mailCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/8/21 18:21
     */
    Integer checkMailCode(String mailCode);
}
