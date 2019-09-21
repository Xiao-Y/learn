package com.billow.email.service.impl;

import com.billow.email.constant.MailCst;
import com.billow.email.dao.MailTemplateDao;
import com.billow.email.pojo.po.MailTemplatePo;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ConvertUtils;
import com.billow.email.utils.ToolsUtils;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
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

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private TemplateEngine templateEngine;

    private static final String SQL_PLACEHOLDER = "#";
    private static final String PRO_PLACEHOLDER = "$";

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
    public MailTemplateVo genMailContent(String mailCode, Map<String, String> parameter) throws Exception {
        if (ToolsUtils.isEmpty(mailCode)) {
            throw new RuntimeException("使用模板邮件时，mailCode不能为空。请去 sys_mail_template 表中配置邮件模板");
        }
        MailTemplatePo mailTemplatePo = mailTemplateDao.findByMailCodeAndValidIndIsTrue(mailCode);
        MailTemplateVo mailTemplateVo = ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
        if (mailTemplateVo == null) {
            throw new RuntimeException("没有查询到对应的邮件模板。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板");
        }

        // 邮件类型
        String mailType = mailTemplatePo.getMailType();
        // 邮件模板
        String mailTemp = mailTemplateVo.getMailTemp();
        if (ToolsUtils.isEmpty(mailTemp) && !(MailCst.SYS_FC_DATA_MAIL_THF.equals(mailType) || MailCst.SYS_FC_DATA_MAIL_FM.equals(mailType))) {
            throw new RuntimeException("邮件内容为空。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 mailTemp");
        }

        // 使用 Thymeleaf 或者 Freemarker 时,需要指定模板路径
        String templatePath = mailTemplateVo.getTemplatePath();
        if (MailCst.SYS_FC_DATA_MAIL_THF.equals(mailType) || MailCst.SYS_FC_DATA_MAIL_FM.equals(mailType)) {
            if (ToolsUtils.isEmpty(templatePath)) {
                throw new RuntimeException("邮件模板路径为空。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 templatePath");
            }
        }

        // 数据来源，1-固定邮件，2-SQL查询，3-参数设置,4-混合（2、3都有）
        String dataSources = mailTemplateVo.getDataSources();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        switch (dataSources) {
            case MailCst.SYS_FC_DATA_SS_FIXED: // 1-固定邮件
                break;
            case MailCst.SYS_FC_DATA_SS_SQL: // 2-SQL查询
                if (!mailTemplateVo.getSingleResult()) {
                    resultList = this.runSQLResultList(parameter, mailTemplateVo.getRunSql());
                } else {
                    result = this.runSQLSingleResult(parameter, mailTemplateVo.getRunSql());
                }
                if (MailCst.SYS_FC_DATA_MAIL_THF.equals(mailType)) {// Thymeleaf 模板
                    Context context = new Context();
                    if (!mailTemplateVo.getSingleResult()) {
                        context.setVariable("root", resultList);
                    } else {
                        context.setVariables(result);
                    }
                    mailTemp = templateEngine.process(templatePath, context);
                } else if (MailCst.SYS_FC_DATA_MAIL_FM.equals(mailType)) {// FreeMarker 模板
                    Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
                    if (!mailTemplateVo.getSingleResult()) {
                        result.put("root", resultList);
                    }
                    mailTemp = FreeMarkerTemplateUtils.processTemplateIntoString(template, result);
                } else {// html 模板
                    mailTemp = this.replaceContent(mailTemp, result, SQL_PLACEHOLDER);
                }
                break;
            case MailCst.SYS_FC_DATA_SS_PRO: // 3-参数设置
                if (MailCst.SYS_FC_DATA_MAIL_THF.equals(mailType)) {// Thymeleaf 模板
                    Context context = new Context();
                    context.setVariables(parameter);
                    mailTemp = templateEngine.process(templatePath, context);
                } else if (MailCst.SYS_FC_DATA_MAIL_FM.equals(mailType)) {// FreeMarker 模板
                    Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
                    mailTemp = FreeMarkerTemplateUtils.processTemplateIntoString(template, parameter);
                } else {// html 模板
                    mailTemp = this.replaceContent(mailTemp, parameter, PRO_PLACEHOLDER);
                }
                break;
            case MailCst.SYS_FC_DATA_SS_MIX: // 4-混合（2、3都有）
                if (!mailTemplateVo.getSingleResult()) {
                    resultList = this.runSQLResultList(parameter, mailTemplateVo.getRunSql());
                } else {
                    result = this.runSQLSingleResult(parameter, mailTemplateVo.getRunSql());
                }
                if (MailCst.SYS_FC_DATA_MAIL_THF.equals(mailType)) {
                    Context context = new Context();
                    if (!mailTemplateVo.getSingleResult()) {
                        context.setVariable("root", resultList);
                    } else {
                        // 合并参数，指定参数优先
                        result.putAll(parameter);
                        context.setVariables(result);
                    }
                    mailTemp = templateEngine.process(templatePath, context);
                } else if (MailCst.SYS_FC_DATA_MAIL_FM.equals(mailType)) {
                    // 合并参数，指定参数优先
                    result.putAll(parameter);
                    if (!mailTemplateVo.getSingleResult()) {
                        result.put("root", resultList);
                    }
                    Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
                    mailTemp = FreeMarkerTemplateUtils.processTemplateIntoString(template, result);
                } else {
                    mailTemp = this.replaceContent(mailTemp, parameter, PRO_PLACEHOLDER);
                    mailTemp = this.replaceContent(mailTemp, result, SQL_PLACEHOLDER);
                }
                break;
        }

        if (ToolsUtils.isEmpty(mailTemp)) {
            throw new RuntimeException("mailCode:[" + mailCode + "],mailType:[" + mailType + "],dataSources:[" + dataSources + "]。邮件内容为空，请查证!");
        }
        mailTemplateVo.setMailContent(mailTemp);
        return mailTemplateVo;
    }

    @Override
    public Page<MailTemplateVo> findMailTemplateList(MailTemplateVo mailTemplateVo) {
        MailTemplatePo mailTemplatePo = ConvertUtils.convert(mailTemplateVo, MailTemplatePo.class);
        Example<MailTemplatePo> example = Example.of(mailTemplatePo);
        Pageable pageable = new PageRequest(mailTemplateVo.getPageNo(), mailTemplateVo.getPageSize());
        return mailTemplateDao.findAll(example, pageable).map(this::coverMailTemplateVo);
    }

    @Override
    public MailTemplateVo findMailTemplateById(Long id) {
        MailTemplatePo mailTemplatePo = mailTemplateDao.findOne(id);
        return ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MailTemplateVo deleteMailTemplateById(Long id) {
        mailTemplateDao.delete(id);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MailTemplateVo prohibitMailTemplateById(Long id) {
        MailTemplatePo mailTemplatePo = mailTemplateDao.findOne(id);
        mailTemplatePo.setValidInd(false);
        mailTemplateDao.save(mailTemplatePo);
        return ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMailTemplate(MailTemplateVo permissionVo) {
        String toEmails = permissionVo.getToEmails();
        if (ToolsUtils.isNotEmpty(toEmails)) {
            permissionVo.setToEmails(toEmails.replaceAll("\\s*|\t|\r|\n", ""));
        }
        MailTemplatePo mailTemplatePo = ConvertUtils.convert(permissionVo, MailTemplatePo.class);
        MailTemplatePo save = mailTemplateDao.save(mailTemplatePo);
        ConvertUtils.convert(save, permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateMailTemplate(MailTemplateVo permissionVo) {
        this.saveMailTemplate(permissionVo);
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


    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
    private Map<String, Object> runSQLSingleResult(Map<String, String> parameter, String runSql) {
        if (ToolsUtils.isEmpty(runSql)) {
            throw new RuntimeException("查询SQL不能为空");
        }
        // 替换参数
        runSql = this.replaceContent(runSql, parameter, SQL_PLACEHOLDER);
        Query nativeQuery = entityManager.createNativeQuery(runSql);
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) nativeQuery.getSingleResult();
    }

    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
    private List<Map<String, Object>> runSQLResultList(Map<String, String> parameter, String runSql) {
        if (ToolsUtils.isEmpty(runSql)) {
            throw new RuntimeException("查询SQL不能为空");
        }
        // 替换参数
        runSql = this.replaceContent(runSql, parameter, SQL_PLACEHOLDER);
        Query nativeQuery = entityManager.createNativeQuery(runSql);
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (List<Map<String, Object>>) nativeQuery.getResultList();
    }

    /**
     * 替换字符串中的参数
     *
     * @param content   字符串
     * @param parameter 参数和值
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/21 8:30
     */
    private String replaceContent(String content, Map parameter, String rep) {
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
