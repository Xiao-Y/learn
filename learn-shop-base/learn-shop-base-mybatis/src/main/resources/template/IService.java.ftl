<#assign VO = table.entityName?substring(0,(table.entityName)?length-2) + "Vo">
<#assign Vo = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "Vo">

package com.billow.${package.ModuleName}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.${package.ModuleName}.pojo.po.${entity};
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.${package.ModuleName}.pojo.vo.${VO};

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
     * @param ${Vo} 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.${package.ModuleName}.pojo.po.${entity}>
     * @author ${author}
     * @since ${date}
     */
    IPage<${entity}> findListByPage(${VO} ${Vo});

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