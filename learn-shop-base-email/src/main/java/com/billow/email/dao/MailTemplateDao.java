package com.billow.email.dao;

import com.billow.email.pojo.po.MailTemplatePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTemplateDao extends JpaRepository<MailTemplatePo, Long> {

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
}
