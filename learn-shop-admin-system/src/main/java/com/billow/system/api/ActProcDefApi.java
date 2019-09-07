package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.CustomPage;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 流程定义API
 *
 * @author liuyongtao
 * @create 2019-08-29 16:31
 */
@Slf4j
@RestController
@RequestMapping("/actProcDefApi")
@Api(value = "工作流部署API")
public class ActProcDefApi {

    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;

    @ApiOperation(value = "查询流程定义列表")
    @PostMapping("/findProcDefList")
    public CustomPage<ProcessDefinitionVo> findProcDefList(@RequestBody ProcessDefinitionVo vo) {
        CustomPage<ProcessDefinitionVo> definitionPage = workFlowQuery.queryProcessDefinition(vo, vo.getOffset(), vo.getPageSize());
        return definitionPage;
    }

    @ApiOperation(value = "挂起流程定义")
    @PutMapping("/suspendProcess/{processDefinitionId}/{cascade}")
    public void suspendProcess(@PathVariable String processDefinitionId, @PathVariable(required = false) boolean cascade) throws Exception {
        if (cascade) {
            workFlowExecute.suspendProcessCascade(processDefinitionId);
        } else {
            workFlowExecute.suspendProcess(processDefinitionId);
        }
    }

    @ApiOperation(value = "激活流程定义")
    @PutMapping("/activateProcess/{processDefinitionId}/{cascade}")
    public void activateProcess(@PathVariable String processDefinitionId, @PathVariable(required = false) boolean cascade) throws Exception {
        if (cascade) {
            workFlowExecute.activateProcessCascade(processDefinitionId);
        } else {
            workFlowExecute.activateProcess(processDefinitionId);
        }
    }

    @ApiOperation(value = "通过 key 查询到最新的一个流程定义")
    @GetMapping("/findDefByKey/{key}")
    public ProcessDefinitionVo findDefByKey(@PathVariable String key) {
        ProcessDefinitionVo processDefinitionVo = workFlowQuery.queryProcessDefinitionByKey(key);
        return processDefinitionVo;
    }
}
