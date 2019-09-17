//package com.billow.system.dao;
//
//import com.billow.email.pojo.po.MailTemplatePo;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//public interface MailTemplateDao extends JpaRepository<MailTemplatePo, Long>, JpaSpecificationExecutor<MailTemplatePo> {
//
//    /**
//     * 通过 mailCode 查询出有效的邮件模板
//     *
//     * @param mailCode
//     * @return com.billow.system.pojo.po.MailTemplatePo
//     * @author LiuYongTao
//     * @date 2019/8/20 21:15
//     */
//    MailTemplatePo findByMailCodeAndValidIndIsTrue(String mailCode);
//
//    /**
//     * 查询 mailCode 的个数
//     *
//     * @param mailCode
//     * @return java.lang.Integer
//     * @author LiuYongTao
//     * @date 2019/8/21 18:23
//     */
//    Integer countByMailCodeIs(String mailCode);
//}
