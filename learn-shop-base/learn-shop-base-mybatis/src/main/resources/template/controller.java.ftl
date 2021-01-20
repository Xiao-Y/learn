<#assign VO = table.entityName?substring(0,(table.entityName)?length-2) + "Vo">
<#assign Vo = (table.entityName?substring(0,(table.entityName)?length-2))?uncap_first + "Vo">
package ${package.Controller};

import com.billow.${package.ModuleName}.pojo.vo.${VO};
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.${package.ModuleName}.service.${table.serviceName};
import com.billow.tools.utlis.ConvertUtils;
import com.billow.${package.ModuleName}.pojo.po.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 * @version v1.0
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
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @ApiOperation(value = "查询分页${table.comment!}数据")
    @PostMapping(value = "/findListByPage")
    public IPage<${entity}> findListByPage(@RequestBody ${VO} ${Vo}){
        return ${table.serviceName?uncap_first}.findListByPage(${Vo});
    }

    @ApiOperation(value = "根据id查询${table.comment!}数据")
    @GetMapping(value = "/findById/{id}")
    public ${VO} findById(@PathVariable("id") String id){
        ${entity} po = ${table.serviceName?uncap_first}.getById(id);
        return ConvertUtils.convert(po, ${VO}.class);
    }

    @ApiOperation(value = "新增${table.comment!}数据")
    @PostMapping(value = "/add")
    public ${VO} add(@RequestBody ${VO} ${Vo}){
        ${entity} po = ConvertUtils.convert(${Vo}, ${entity}.class);
        ${table.serviceName?uncap_first}.save(po);
        return ConvertUtils.convert(po, ${VO}.class);
    }

    @ApiOperation(value = "删除${table.comment!}数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return ${table.serviceName?uncap_first}.removeById(id);
    }

    @ApiOperation(value = "更新${table.comment!}数据")
    @PutMapping(value = "/update")
    public ${VO} update(@RequestBody ${VO} ${Vo}){
        ${entity} po = ConvertUtils.convert(${Vo}, ${entity}.class);
        ${table.serviceName?uncap_first}.updateById(po);
        return ConvertUtils.convert(po, ${VO}.class);
    }

    @ApiOperation("根据ID禁用${table.comment!}数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return ${table.serviceName?uncap_first}.prohibitById(id);
    }
}
</#if>
