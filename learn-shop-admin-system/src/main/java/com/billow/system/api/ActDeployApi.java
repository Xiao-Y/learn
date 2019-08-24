package com.billow.system.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作流部署API
 *
 * @author liuyongtao
 * @create 2019-08-24 15:22
 */
@Slf4j
@RestController
@RequestMapping("/actDeployApi")
@Api(value = "工作流部署API")
public class ActDeployApi {

    @Autowired
    private RepositoryService repositoryService;

    @ApiOperation(value = "部署流程")
    @PostMapping("/deploy/{processName}")
    public Deployment deploy(@PathVariable("processName") String processName) {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/test.bpmn20.xml")
                .deploy();
        return deploy;
    }
}
