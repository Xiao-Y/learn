package com.billow.email.service.impl;

import com.billow.email.common.CustomPage;
import com.billow.email.constant.MailCst;
import com.billow.email.constant.MailTypeEnum;
import com.billow.email.dao.MailTemplateDao;
import com.billow.email.pojo.po.MailTemplatePo;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailContentBuild;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ConvertUtils;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 邮件服务器类
 *
 * @author liuyongtao
 * @create 2019-08-20 21:09
 */
@Slf4j
@Service
public class MailTemplateServiceImpl implements MailTemplateService {

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private Map<String, MailContentBuild> mailContentBuilds;

    /**
     * 邮件内容生成器：<br/>
     * 使用方法：<br/>
     * 0、参数为map :{ id:1, jobGroup:"public-job" } <br/>
     * 1、sql 模板为 select job_name as jobName from sys_schedule_job_log r where r.id = #{id}。 <br/>
     * 替换后结果为：select job_name as jobName from sys_schedule_job_log r where r.id = 1。（结果为：测试任务） <br/>
     * 2、邮件模板为：XXXXX#{jobName}，XXXXXX${jobGroup}。VVVV#{id} <br/>
     * 替换后结果为：XXXXX测试任务，XXXXXXpublic-job。VVVV#{id} <br/>
     * 注意： <br/>
     * 邮件模板中：#{jobName} 是要替换成sql 查询出来的结果，jobName为字段名。${jobGroup}是要替换成参数中的值，jobGroup为传入参数的key <br/>
     *
     * @param mailCode  邮件code
     * @param parameter 传入参数
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/21 8:35
     */
    @Override
    public MailTemplateVo genMailContent(String mailCode, Map<String, Object> parameter) throws Exception {
        if (ToolsUtils.isEmpty(mailCode)) {
            throw new RuntimeException("使用模板邮件时，mailCode不能为空。请去 sys_mail_template 表中配置邮件模板");
        }
        MailTemplatePo mailTemplatePo = mailTemplateDao.findByMailCodeAndValidIndIsTrue(mailCode);
        MailTemplateVo mailTemplateVo = ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
        if (mailTemplateVo == null) {
            throw new RuntimeException("没有查询到对应的邮件模板。请去 sys_mail_template 表中配置 mailCode：[" + mailCode + "] 的模板");
        }

        // 邮件类型，1-普通邮件，2-html邮件,4-FreeMarker 模板邮件,5-Thymeleaf 模板邮件
        String mailType = mailTemplatePo.getMailType();
        if (ToolsUtils.isEmpty(mailType)) {
            throw new RuntimeException("邮件内容为空。请去 sys_mail_template 表中配置 mailCode：[" + mailCode + "] 的模板的 mailType");
        }
        // 数据来源，1-固定内容，2-SQL查询，3-参数设置,4-混合（2、3都有）
        String dataSources = mailTemplateVo.getDataSources();
        if (ToolsUtils.isEmpty(dataSources)) {
            throw new RuntimeException("邮件内容为空。请去 sys_mail_template 表中配置 mailCode：[" + mailCode + "] 的模板的 dataSources");
        }

        if (parameter == null) {
            parameter = new HashMap<>();
        }

        // 获取内容构建器
        String contentBuild = MailTypeEnum.getMailTypeEnum(mailType) + MailContentBuild.class.getSimpleName();
        MailContentBuild mailContentBuild = mailContentBuilds.get(contentBuild);
        if (mailContentBuild == null) {
            throw new RuntimeException("没有找到相应的邮件内容构建器：" + contentBuild);
        }
        // 构建邮件内容
        String mailContent = mailContentBuild.build(mailTemplateVo, parameter);
        if (ToolsUtils.isEmpty(mailContent)) {
            throw new RuntimeException("mailCode:[" + mailCode + "],mailType:[" + mailType + "],dataSources:[" + dataSources + "]。邮件内容为空，请查证!");
        }
        mailTemplateVo.setMailContent(mailContent);
        return mailTemplateVo;
    }

    @Override
    public CustomPage<MailTemplateVo> findMailTemplateList(MailTemplateVo mailTemplateVo) {
        MailTemplatePo mailTemplatePo = ConvertUtils.convert(mailTemplateVo, MailTemplatePo.class);
        if (mailTemplatePo.getMailCode() == "") {
            mailTemplatePo.setMailCode(null);
        }
        CustomPage<MailTemplateVo> customPage = mailTemplateDao.findAll(mailTemplatePo, mailTemplateVo.getPageNo(), mailTemplateVo.getPageSize())
                .map(this::coverMailTemplateVo);
        return customPage;
    }

    @Override
    public MailTemplateVo findByIdAndValidIndIsTrue(Long id) {
        MailTemplatePo mailTemplatePo = mailTemplateDao.findByIdAndValidIndIsTrue(id);
        MailTemplateVo mailTemplateVo = ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
        return mailTemplateVo;
    }

    @Override
    public MailTemplateVo findMailTemplateById(Long id) {
        MailTemplatePo mailTemplatePo = mailTemplateDao.findById(id);
        return ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MailTemplateVo deleteMailTemplateById(Long id) {
        mailTemplateDao.deleteById(id);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MailTemplateVo prohibitMailTemplateById(Long id, String userCode) {
        MailTemplatePo mailTemplatePo = mailTemplateDao.findById(id);
        mailTemplatePo.setValidInd(false);
        mailTemplatePo.setUpdateTime(new Date());
        mailTemplatePo.setUpdaterCode(userCode);
        mailTemplateDao.updateById(mailTemplatePo);
        return ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMailTemplate(MailTemplateVo permissionVo, String userCode) {
        String toEmails = permissionVo.getToEmails();
        if (ToolsUtils.isNotEmpty(toEmails)) {
            permissionVo.setToEmails(toEmails.replaceAll("\\s*|\t|\r|\n", ""));
        }
        permissionVo.setCreateTime(new Date());
        permissionVo.setUpdateTime(new Date());
        permissionVo.setUpdaterCode(userCode);
        permissionVo.setCreatorCode(userCode);
        MailTemplatePo mailTemplatePo = ConvertUtils.convert(permissionVo, MailTemplatePo.class);
        MailTemplatePo save = mailTemplateDao.save(mailTemplatePo);
        ConvertUtils.convert(save, permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateMailTemplate(MailTemplateVo permissionVo, String userCode) {
        String toEmails = permissionVo.getToEmails();
        if (ToolsUtils.isNotEmpty(toEmails)) {
            permissionVo.setToEmails(toEmails.replaceAll("\\s*|\t|\r|\n", ""));
        }
        permissionVo.setUpdateTime(new Date());
        permissionVo.setUpdaterCode(userCode);
        MailTemplatePo mailTemplatePo = ConvertUtils.convert(permissionVo, MailTemplatePo.class);
        mailTemplateDao.updateById(mailTemplatePo);
    }

    @Override
    public Integer checkMailCode(String mailCode) {
        return mailTemplateDao.countByMailCodeIs(mailCode);
    }

    /**
     * 分页结果集转换
     *
     * @param po
     * @return com.billow.system.com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2019/8/21 15:58
     */
    private MailTemplateVo coverMailTemplateVo(MailTemplatePo po) {
        MailTemplateVo convert = ConvertUtils.convert(po, MailTemplateVo.class);
        convert.setMailContent(null);
        convert.setMailTemp(null);
        convert.setMailMarkdown(null);
        return convert;
    }

    @Override
    public Map<String, Object> runSQLSingleResult(Map<String, Object> parameter, String runSql) throws Exception {
        log.debug("原始SQL:{}", runSql);
        if (ToolsUtils.isEmpty(runSql)) {
            throw new RuntimeException("查询SQL不能为空");
        }
        if (parameter != null && parameter.size() > 0) {
            // 替换参数
            runSql = this.replaceContent(runSql, parameter, MailCst.SQL_PLACEHOLDER);
        }
        log.debug("执行SQL:{}", runSql);
        try {
            return mailTemplateDao.runSqlSingleResult(runSql);
        } catch (Exception e) {
            throw new RuntimeException("SQL执行异常或者没有查询到数据，请查询sql:" + runSql + "\r\n异常信息：" + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> runSQLResultList(Map<String, Object> parameter, String runSql) throws Exception {
        log.debug("原始SQL:{}", runSql);
        if (ToolsUtils.isEmpty(runSql)) {
            throw new RuntimeException("查询SQL不能为空");
        }
        if (parameter != null && parameter.size() > 0) {
            // 替换参数
            runSql = this.replaceContent(runSql, parameter, MailCst.SQL_PLACEHOLDER);
        }
        log.debug("执行SQL:{}", runSql);
        try {
            return mailTemplateDao.runSqlResultList(runSql);
        } catch (Exception e) {
            throw new RuntimeException("SQL执行异常或者没有查询到数据，请查询sql:" + runSql + "\r\n异常信息：" + e.getMessage());
        }
    }

    @Override
    public String replaceContent(String content, Map parameter, String rep) {
        // 替换参数
        Set keys = parameter.keySet();
        for (Object key : keys) {
            if (key == null) {
                continue;
            }
            Object value = parameter.get(key);
            String param = rep + "{" + key + "}";
            value = value == null ? "" : value;
            content = content.replace(param, value.toString());
        }
        return content;
    }
}
