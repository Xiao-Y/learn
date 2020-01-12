package com.billow.email.service;

import com.billow.email.common.CustomPage;
import com.billow.email.pojo.vo.MailTemplateVo;

import java.util.List;
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
    MailTemplateVo genMailContent(String mailCode, Map<String, Object> parameter) throws Exception;

    /**
     * 根据条件查询邮件模板信息
     *
     * @param mailTemplateVo
     * @return com.billow.email.common.CustomPage
     * @author LiuYongTao
     * @date 2019/12/19 14:56
     */
    CustomPage<MailTemplateVo> findMailTemplateList(MailTemplateVo mailTemplateVo);


    /**
     * 通过 id 查询有效的邮件模板信息
     *
     * @param id
     * @return com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/9/26 11:45
     */
    MailTemplateVo findByIdAndValidIndIsTrue(Long id);

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
     * @param userCode
     * @return com.billow.system.com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 15:51
     */
    MailTemplateVo prohibitMailTemplateById(Long id, String userCode);

    /**
     * 添加邮件模板信息
     *
     * @param permissionVo
     * @param userCode
     * @return void
     * @author LiuYongTao
     * @date 2019/8/21 15:51
     */
    void saveMailTemplate(MailTemplateVo permissionVo, String userCode);

    /**
     * 更新邮件模板信息
     *
     * @param permissionVo
     * @param userCode
     * @return void
     * @author LiuYongTao
     * @date 2019/8/21 15:52
     */
    void updateMailTemplate(MailTemplateVo permissionVo, String userCode);

    /**
     * 查询 mailCode 的个数
     *
     * @param mailCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/8/21 18:21
     */
    Integer checkMailCode(String mailCode);

    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
    Map<String, Object> runSQLSingleResult(Map<String, Object> parameter, String runSql) throws Exception;

    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
    List<Map<String, Object>> runSQLResultList(Map<String, Object> parameter, String runSql) throws Exception;

    /**
     * 替换字符串中的参数
     *
     * @param content   字符串
     * @param parameter 参数和值
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/21 8:30
     */
    String replaceContent(String content, Map parameter, String rep);
}
