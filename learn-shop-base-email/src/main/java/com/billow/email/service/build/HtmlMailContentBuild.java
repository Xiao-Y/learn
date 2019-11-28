package com.billow.email.service.build;

import com.billow.email.constant.MailCst;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailContentBuild;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 1-普通邮件,2-html邮件 内容构建
 *
 * @author liuyongtao
 * @create 2019-09-29 8:28
 */
@Service
public class HtmlMailContentBuild implements MailContentBuild {

    @Autowired
    private MailTemplateService mailTemplateService;

    @Override
    public String build(MailTemplateVo mailTemplateVo, Map<String, Object> parameter) throws Exception {

        // 邮件CODE
        String mailCode = mailTemplateVo.getMailCode();
        // 邮件模板
        String mailTemp = mailTemplateVo.getMailTemp();
        if (ToolsUtils.isEmpty(mailTemp)) {
            throw new RuntimeException("邮件内容为空。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 mailTemp");
        }

        // sql 执行结果是否是单行，默认是单行
        // 数据来源，1-固定邮件，2-SQL查询，3-参数设置,4-混合（2、3都有）
        String dataSources = mailTemplateVo.getDataSources();
        Map<String, Object> result = new HashMap<>();
        switch (dataSources) {
            case MailCst.SYS_FC_DATA_SS_FIXED: // 1-固定邮件
                break;
            case MailCst.SYS_FC_DATA_SS_SQL: // 2-SQL查询
                result = mailTemplateService.runSQLSingleResult(parameter, mailTemplateVo.getRunSql());
                mailTemp = mailTemplateService.replaceContent(mailTemp, result, MailCst.SQL_PLACEHOLDER);
                break;
            case MailCst.SYS_FC_DATA_SS_PRO: // 3-参数设置
                mailTemp = mailTemplateService.replaceContent(mailTemp, parameter, MailCst.PRO_PLACEHOLDER);
                break;
            case MailCst.SYS_FC_DATA_SS_MIX: // 4-混合（2、3都有）
                result = mailTemplateService.runSQLSingleResult(parameter, mailTemplateVo.getRunSql());
                mailTemp = mailTemplateService.replaceContent(mailTemp, parameter, MailCst.PRO_PLACEHOLDER);
                mailTemp = mailTemplateService.replaceContent(mailTemp, result, MailCst.SQL_PLACEHOLDER);
                break;
        }
        return mailTemp;
    }
}
