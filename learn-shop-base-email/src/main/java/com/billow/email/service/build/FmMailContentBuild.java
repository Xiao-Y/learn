package com.billow.email.service.build;

import com.billow.email.constant.MailCst;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailContentBuild;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ToolsUtils;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FreeMarker模板邮件构建器
 *
 * @author liuyongtao
 * @create 2019-09-29 9:12
 */
@Service
public class FmMailContentBuild implements MailContentBuild {

    @Autowired
    private MailTemplateService mailTemplateService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public String build(MailTemplateVo mailTemplateVo, Map<String, Object> parameter) throws Exception {
        // 模板名称
        String templateName = mailTemplateVo.getTemplateName();
        if (ToolsUtils.isEmpty(templateName)) {
            throw new RuntimeException("邮件模板路径为空。请去 sys_mail_template 表中配置 mailCode：[" + mailTemplateVo.getMailCode() + "] 的模板的 templateName");
        }

        if (parameter == null) {
            parameter = new HashMap<>();
        }

        // sql 执行结果是否是单行，默认是单行
        Boolean singleResult = mailTemplateVo.getSingleResult();
        singleResult = singleResult == null ? true : singleResult;
        // 数据来源，1-固定邮件，2-SQL查询，3-参数设置,4-混合（2、3都有）
        String dataSources = mailTemplateVo.getDataSources();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        switch (dataSources) {
            case MailCst.SYS_FC_DATA_SS_FIXED: // 1-固定邮件
                break;
            case MailCst.SYS_FC_DATA_SS_SQL: // 2-SQL查询
                if (!singleResult) {
                    resultList = mailTemplateService.runSQLResultList(parameter, mailTemplateVo.getRunSql());
                    result.put("root", resultList);
                } else {
                    result = mailTemplateService.runSQLSingleResult(parameter, mailTemplateVo.getRunSql());
                }
                break;
            case MailCst.SYS_FC_DATA_SS_PRO: // 3-参数设置
                result.putAll(parameter);
                break;
            case MailCst.SYS_FC_DATA_SS_MIX: // 4-混合（2、3都有）
                if (!singleResult) {
                    resultList = mailTemplateService.runSQLResultList(parameter, mailTemplateVo.getRunSql());
                } else {
                    result = mailTemplateService.runSQLSingleResult(parameter, mailTemplateVo.getRunSql());
                }
                // 合并参数，指定参数优先
                result.putAll(parameter);
                if (!singleResult) {
                    result.put("root", resultList);
                }
                break;
        }
        // 邮件模板
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, result);
    }
}
