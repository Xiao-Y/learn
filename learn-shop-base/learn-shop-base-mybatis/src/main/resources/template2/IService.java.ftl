<#assign SC = (table.entityName?substring(0,(table.entityName)?length-2)) + "SearchParam">
<#assign Sc = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "SearchParam">
<#assign Parent = (package.Entity?substring(0,(package.Entity)?length-8))>

package ${Parent}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.extension.service.IService;
import ${Parent}.pojo.search.${SC};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @version v1.0
 * @since ${date}
 */
public interface ${table.serviceName} extends IService<${entity}> {

    /**
     * 分页查询
     *
     * @param ${Sc} 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.${package.ModuleName}.pojo.po.${entity}>
     * @author ${author}
     * @since ${date}
     */
    IPage<${entity}> findListByPage(${SC} ${Sc});

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author ${author}
     * @since ${date}
     */
    boolean prohibitById(String id);
}