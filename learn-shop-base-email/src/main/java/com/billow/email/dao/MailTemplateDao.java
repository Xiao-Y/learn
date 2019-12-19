package com.billow.email.dao;

import com.billow.email.common.CustomPage;
import com.billow.email.pojo.po.MailTemplatePo;

import java.util.List;
import java.util.Map;

public interface MailTemplateDao {

    /**
     * 通过 mailCode 查询出有效的邮件模板
     *
     * @param mailCode
     * @return com.billow.system.com.billow.email.pojo.po.MailTemplatePo
     * @author LiuYongTao
     * @date 2019/8/20 21:15
     */
    MailTemplatePo findByMailCodeAndValidIndIsTrue(String mailCode);

    /**
     * 查询 mailCode 的个数
     *
     * @param mailCode
     * @return java.lang.Integer
     * @author LiuYongTao
     * @date 2019/8/21 18:23
     */
    Integer countByMailCodeIs(String mailCode);

    /**
     * 通过 id 查询有效的邮件模板信息
     *
     * @param id
     * @return com.billow.email.pojo.po.MailTemplatePo
     * @author LiuYongTao
     * @date 2019/9/26 11:44
     */
    MailTemplatePo findByIdAndValidIndIsTrue(Long id);

    /**
     * 通过id 主键查询
     *
     * @param id
     * @return com.billow.email.pojo.po.MailTemplatePo
     * @author LiuYongTao
     * @date 2019/12/19 10:41
     */
    MailTemplatePo findById(Long id);

    /**
     * 通过id 主键删除
     *
     * @param id
     * @return void
     * @author LiuYongTao
     * @date 2019/12/19 10:42
     */
    void deleteById(Long id);

    /**
     * 保存邮件模板
     *
     * @param mailTemplatePo
     * @return com.billow.email.pojo.po.MailTemplatePo
     * @author LiuYongTao
     * @date 2019/12/19 10:42
     */
    MailTemplatePo save(MailTemplatePo mailTemplatePo);

    /**
     * 分布查询
     *
     * @param mailTemplatePo
     * @param PageNo
     * @param PageSize
     * @return CustomPage<MailTemplatePo>
     * @author LiuYongTao
     * @date 2019/12/19 14:57
     */
    CustomPage<MailTemplatePo> findAll(MailTemplatePo mailTemplatePo, Integer PageNo, Integer PageSize);

    /**
     * 更新邮件模板
     *
     * @param mailTemplatePo
     * @return void
     * @author LiuYongTao
     * @date 2019/12/19 14:15
     */
    void updateById(MailTemplatePo mailTemplatePo);

    /**
     * 运行sql，返回单行数据
     *
     * @param runSql
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2019/12/19 14:46
     */
    Map<String, Object> runSqlSingleResult(String runSql);

    /**
     * 运行sql，返回多行数据
     *
     * @param runSql
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author LiuYongTao
     * @date 2019/12/19 14:46
     */
    List<Map<String, Object>> runSqlResultList(String runSql);
}
