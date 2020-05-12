package com.billow.system.action;

import com.billow.base.workflow.component.WorkFlowQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 流程图相关
 *
 * @author liuyongtao
 * @create 2020-04-26 11:39
 */
@Slf4j
@RestController
@RequestMapping("/deployImageAction")
@Api(value = "流程图相关,不受权限控制")
public class DeployImageAction {

    @Autowired
    private WorkFlowQuery workFlowQuery;

    @ApiOperation(value = "查看活动的流程图（显示运行轨迹）")
    @GetMapping("/viewExecutionImgById/{executionId}")
    public void viewExecutionImgById(@PathVariable String executionId, HttpServletResponse response) throws Exception {
        workFlowQuery.genActiveProccessImage(executionId, response);
    }


    @ApiOperation(value = "根据id获取原始的流程图")
    @GetMapping("/viewDeployImgById/{deploymentId}")
    public void viewDeployImgById(@PathVariable String deploymentId, HttpServletResponse response) throws Exception {
        workFlowQuery.genOriginalProcessImage(deploymentId, response);
    }
}
