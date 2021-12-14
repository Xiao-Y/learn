<#assign SP = (table.entityName?substring(0,(table.entityName)?length-2)) + "SearchParam">
<#assign Sp = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "SearchParam">
<#assign Parent = (package.Entity?substring(0,(package.Entity)?length-8))>
package ${Parent}.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import ${Parent}.dao.${table.mapperName};
import ${Parent}.pojo.search.${SP};
import ${Parent}.pojo.po.${entity};
import ${Parent}.service.${table.serviceName};
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

