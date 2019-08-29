package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.Page;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<ProcessDefinitionVo> findProcDefList(@RequestBody ProcessDefinitionVo vo) {
        Page<ProcessDefinitionVo> definitionPage = workFlowQuery.queryProcessDefinition(vo, vo.getOffset(), vo.getPageSize());
        return definitionPage;
    }
}
