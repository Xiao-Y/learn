package com.billow.order.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.order.pojo.build.CompanyAddressBuildParam;
import com.billow.order.pojo.po.CompanyAddressPo;
import com.billow.order.pojo.search.CompanyAddressSearchParam;
import com.billow.order.pojo.vo.CompanyAddressVo;
import com.billow.order.service.CompanyAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Slf4j
@Tag(name = "CompanyAddressApi", description = "")
@RestController
@RequestMapping("/companyAddressApi")
public class CompanyAddressApi extends HighLevelV2Api<CompanyAddressService, CompanyAddressPo, CompanyAddressSearchParam> {

}
