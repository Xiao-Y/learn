<#assign VO = table.entityName?substring(0,(table.entityName)?length-2) + "BuildParam">
<#assign Parent = (package.Entity?substring(0,(package.Entity)?length-8))>
package ${Parent}.pojo.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * ${table.comment!} 信息
 * </p>
 *
 * @author ${author}
 * @version v1.0
 * @since ${date}
 */
@Data
@Accessors(chain = true)
public class ${VO} implements Serializable {
<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.comment!?length gt 0>
        <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#-- -----   存在字段填充设置   ----->
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->

}
