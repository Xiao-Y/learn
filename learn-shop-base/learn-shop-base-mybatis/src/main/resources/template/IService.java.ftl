<#assign SP = (table.entityName?substring(0,(table.entityName)?length-2)) + "SearchParam">
<#assign Sp = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "SearchParam">

package com.billow.${package.ModuleName}.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.${package.ModuleName}.pojo.po.${entity};
import com.billow.${package.ModuleName}.pojo.search.${SP};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @version v2.0
 * @since ${date}
 */
public interface ${table.serviceName} extends HighLevelService<${entity},${SP}> {

}