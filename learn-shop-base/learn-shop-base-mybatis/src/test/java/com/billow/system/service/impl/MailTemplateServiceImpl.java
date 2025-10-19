package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.MailTemplateDao;
import com.billow.system.pojo.search.MailTemplateSearchParam;
import com.billow.system.pojo.po.MailTemplatePo;
import com.billow.system.service.MailTemplateService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2025-10-19
 */
@Service
public class MailTemplateServiceImpl extends HighLevelServiceImpl<MailTemplateDao, MailTemplatePo,MailTemplateSearchParam> implements MailTemplateService {

}

