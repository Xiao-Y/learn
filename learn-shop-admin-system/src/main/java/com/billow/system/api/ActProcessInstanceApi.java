package com.billow.system.api;

import com.billow.system.pojo.ex.ProcessInstanceEx;
import com.billow.tools.utlis.ToolsUtils;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @Autowired
    private RuntimeService runtimeService;


    /**
     * 启动流程实例
     *
     * @param processType
     * @param pk
     * @param businessKey
     * @return org.activiti.engine.runtime.ProcessInstance
     * @author billow
     * @date 2019/8/24 15:46
     */
    @ApiOperation(value = "启动流程实例")
    @PostMapping("/startProcessInstance/{processType}/{pk}/{businessKey}")
    public ProcessInstanceEx startProcessInstance(@PathVariable("processType") String processType,
                                                  @PathVariable("pk") String pk,
                                                  @PathVariable("businessKey") String businessKey,
                                                  @RequestBody Map<String, Object> variables) {
        if (ToolsUtils.isEmpty(variables)) {
            variables = new HashMap<>();
        }
        ProcessInstance processInstance = null;
        if ("id".equals(processType)) {
            processInstance = runtimeService.startProcessInstanceById(pk, businessKey, variables);
        } else if ("key".equals(processType)) {
            processInstance = runtimeService.startProcessInstanceByKey(pk, businessKey, variables);
        }
        ProcessInstanceEx ex = new ProcessInstanceEx();
        ex.setProcessInstanceId(processInstance.getProcessInstanceId());
        return ex;
    }
}
