package com.billow.email.service.build;

import com.billow.email.constant.MailCst;
import com.billow.email.dao.MailTemplateDao;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 千面
 * @date 2025-08-08 10:06:09
 */
@Slf4j
public class BaseContentBuild {

    @Autowired
    private MailTemplateDao mailTemplateDao;


    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
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

    /**
     * 运行sql 得到结果
     *
     * @param parameter
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/8/21 8:33
     */
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

    /**
     * 替换字符串中的参数
     *
     * @param content   字符串
     * @param parameter 参数和值
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/21 8:30
     */
    public String replaceContent(String content, Map<String, Object> parameter, String rep) {
        // 替换参数
        Set<String> keys = parameter.keySet();
        for (String key : keys) {
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
