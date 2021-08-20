<#assign SP = (table.entityName?substring(0,(table.entityName)?length-2)) + "SearchParam">
<#assign Sp = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "SearchParam">
package com.billow.${package.ModuleName}.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.${package.ModuleName}.dao.${table.mapperName};
import com.billow.${package.ModuleName}.pojo.search.${SP};
import com.billow.${package.ModuleName}.pojo.po.${entity};
import com.billow.${package.ModuleName}.service.${table.serviceName};
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @version v2.0
 * @since ${date}
 */
@Service
public class ${table.serviceImplName} extends HighLevelServiceImpl<${table.mapperName}, ${entity},${SP}> implements ${table.serviceName} {

}

