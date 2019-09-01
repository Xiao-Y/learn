package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.Page;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 流程运行API
 *
 * @author liuyongtao
 * @create 2019-08-24 15:34
 */
@Slf4j
@RestController
@RequestMapping("/actProcessInstanceApi")
@Api(value = "流程运行API")
public class ActProcessInstanceApi {

//    @Autowired
//    private WorkFlowExecute workFlowExecute;
//    @Autowired
//    private WorkFlowQuery workFlowQuery;
//
//    /**
//     * 启动流程实例
//     *
//     * @param processType 流程启动类型，id 或者 key(与 pk 对应)
//     * @param pk          processDefinitionId 或者 processDefinitionKey
//     * @param businessKey 业务id
//     * @param variables   启动参数
//     * @return org.activiti.engine.runtime.ProcessInstance
//     * @author billow
//     * @date 2019/8/24 15:46
//     */
//    @ApiOperation(value = "启动流程实例")
//    @PostMapping("/startProcessInstance/{processType}/{pk}/{businessKey}")
//    public ProcessInstanceVo startProcessInstance(@PathVariable("processType") String processType,
//                                                  @PathVariable("pk") String pk,
//                                                  @PathVariable("businessKey") String businessKey,
//                                                  @RequestBody Map<String, Object> variables) {
//        return workFlowExecute.startProcessInstance(processType, pk, businessKey, variables);
//    }


}
