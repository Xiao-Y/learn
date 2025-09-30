package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.CompanyAddressDao;
import com.billow.order.pojo.po.CompanyAddressPo;
import com.billow.order.pojo.search.CompanyAddressSearchParam;
import com.billow.order.service.CompanyAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Service
public class CompanyAddressServiceImpl extends HighLevelServiceImpl<CompanyAddressDao, CompanyAddressPo,CompanyAddressSearchParam> implements CompanyAddressService {

}

