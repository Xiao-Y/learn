<#assign VO = table.entityName?substring(0,(table.entityName)?length-2) + "Vo">
<#assign Vo = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "Vo">
<#assign BP = table.entityName?substring(0,(table.entityName)?length-2) + "BuildParam">
<#assign Bp = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "BuildParam">
<#assign SP = (table.entityName?substring(0,(table.entityName)?length-2)) + "SearchParam">
<#assign Sp = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "SearchParam">
package ${package.Controller};

import com.billow.seckill.app.HighLevelApi;
import com.billow.${package.ModuleName}.pojo.build.${BP};
import com.billow.${package.ModuleName}.pojo.vo.${VO};
import com.billow.${package.ModuleName}.pojo.search.${SP};
import com.billow.${package.ModuleName}.pojo.po.${entity};
import com.billow.${package.ModuleName}.service.${table.serviceName};
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version v2.0
 */
@Slf4j
<#if restControllerStyle>
@Api(tags = {"${table.controllerName}"},value = "${table.comment!}")
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if table.controllerName??>/${table.controllerName?uncap_first}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
public class ${table.controllerName} extends HighLevelApi<${table.serviceName}, ${entity}, ${VO}, ${BP}, ${SP}> {

}
</#if>
