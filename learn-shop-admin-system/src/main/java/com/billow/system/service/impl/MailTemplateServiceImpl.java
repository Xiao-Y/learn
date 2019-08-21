package com.billow.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.system.dao.MailTemplateDao;
import com.billow.system.pojo.po.MailTemplatePo;
import com.billow.system.pojo.vo.MailTemplateVo;
import com.billow.system.service.MailTemplateService;
import com.billow.tools.constant.DictionaryType;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

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
    public MailTemplateVo genMailContent(String mailCode, Map<String, String> parameter) {
        MailTemplatePo mailTemplatePo = mailTemplateDao.findByMailCodeAndValidIndIsTrue(mailCode);
        MailTemplateVo mailTemplateVo = ConvertUtils.convert(mailTemplatePo, MailTemplateVo.class);
        if (mailTemplateVo == null) {
            log.error("MailCode：{}，没有查询到对应的邮件模板", mailCode);
            return null;
        }


        // 邮件模板
        String mailTemp = mailTemplateVo.getMailTemp();
        if (ToolsUtils.isEmpty(mailTemp)) {
            log.error("MailCode：{}，查询到对应的邮件模板为空", mailCode);
            return null;
        }

        // 数据来源，1-固定邮件，2-SQL查询，3-参数设置,4-混合（2、3都有）
        String dataSources = mailTemplateVo.getDataSources();
        Map<String, Object> result = null;
        switch (dataSources) {
            case DictionaryType.SYS_FC_DATA_SS_FIXED: // 1-固定邮件
                mailTemplateVo.setMailContent(mailTemp);
                return mailTemplateVo;
            case DictionaryType.SYS_FC_DATA_SS_SQL: // 2-SQL查询
                result = runSQLResult(parameter, mailTemplateVo.getRunSql());
                mailTemplateVo.setMailContent(this.replaceContent(mailTemp, result, SQL_PLACEHOLDER));
                return mailTemplateVo;
            case DictionaryType.SYS_FC_DATA_SS_PRO: // 3-参数设置
                mailTemplateVo.setMailContent(this.replaceContent(mailTemp, parameter, PRO_PLACEHOLDER));
                return mailTemplateVo;
            case DictionaryType.SYS_FC_DATA_SS_MIX: // 4-混合（2、3都有）
                String content = this.replaceContent(mailTemp, parameter, PRO_PLACEHOLDER);
                result = this.runSQLResult(parameter, mailTemplateVo.getRunSql());
                mailTemplateVo.setMailContent(this.replaceContent(content, result, SQL_PLACEHOLDER));
                return mailTemplateVo;
            default:
                return mailTemplateVo;
        }
    }

    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param mailTemplateVo
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
    private Map<String, Object> runSQLResult(Map<String, String> parameter, String runSql) {
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
            Object value = parameter.get(key);
            String param = rep + "{" + key + "}";
            content = content.replace(param, value.toString());
        }
        return content;
    }
}
