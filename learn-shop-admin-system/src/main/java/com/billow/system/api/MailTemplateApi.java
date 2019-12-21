package com.billow.system.api;

import com.billow.email.common.CustomPage;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailTemplateService;
import com.billow.tools.utlis.UserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件模板管理
 *
 * @author liuyongtao
 * @create 2019-08-21 10:30
 */
@Slf4j
@RestController
@RequestMapping("/mailTemplateApi")
@Api(value = "邮件模板管理API")
public class MailTemplateApi {

    @Autowired
    private MailTemplateService mailTemplateService;

    @Autowired
    private UserTools userTools;

    @ApiOperation("根据条件查询邮件模板信息")
    @PostMapping("/findMailTemplateList")
    public CustomPage<MailTemplateVo> findMailTemplateList(@RequestBody MailTemplateVo mailTemplateVo) {
        return mailTemplateService.findMailTemplateList(mailTemplateVo);
    }

    @ApiOperation("根据id获取邮件模板信息")
    @GetMapping("/findMailTemplateById/{id}")
    public MailTemplateVo findMailTemplateById(@PathVariable("id") Long id) {
        return mailTemplateService.findMailTemplateById(id);
    }

    @ApiOperation("根据ID删除邮件模板")
    @DeleteMapping("/deleteMailTemplateById/{id}")
    public MailTemplateVo deleteMailTemplateById(@PathVariable Long id) {
        MailTemplateVo permissionVo = mailTemplateService.deleteMailTemplateById(id);
        return permissionVo;
    }

    @ApiOperation("根据ID禁用邮件模板")
    @PutMapping("/prohibitMailTemplateById/{id}")
    public MailTemplateVo prohibitMailTemplateById(@PathVariable Long id) {
        MailTemplateVo permissionVo = mailTemplateService.prohibitMailTemplateById(id, userTools.getCurrentUserCode());
        return permissionVo;
    }

    @ApiOperation("添加邮件模板信息")
    @PostMapping("/saveMailTemplate")
    public MailTemplateVo saveMailTemplate(@RequestBody MailTemplateVo permissionVo) {
        mailTemplateService.saveMailTemplate(permissionVo, userTools.getCurrentUserCode());
        return permissionVo;
    }

    @ApiOperation("更新邮件模板信息")
    @PutMapping("/updateMailTemplate")
    public MailTemplateVo updateMailTemplate(@RequestBody MailTemplateVo permissionVo) {
        permissionVo.setUpdaterCode(userTools.getCurrentUserCode());
        mailTemplateService.updateMailTemplate(permissionVo, userTools.getCurrentUserCode());
        return permissionVo;
    }

    @ApiOperation(value = "查询 mailCode 的个数")
    @GetMapping("/checkMailCode/{mailCode}")
    public Integer checkMailCode(@PathVariable("mailCode") String mailCode) {
        return mailTemplateService.checkMailCode(mailCode);
    }
}
