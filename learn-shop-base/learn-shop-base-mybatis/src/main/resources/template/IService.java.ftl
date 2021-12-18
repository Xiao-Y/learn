<#assign SP = (table.entityName?substring(0,(table.entityName)?length-2)) + "SearchParam">
<#assign Sp = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "SearchParam">
<#assign Parent = (package.Entity?substring(0,(package.Entity)?length-8))>

package ${Parent}.service;

import com.billow.mybatis.base.HighLevelService;
import ${package.Entity}.${entity};
import ${Parent}.pojo.search.${SP};

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