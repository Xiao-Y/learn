package com.billow.system.api;

import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流任务API
 *
 * @author liuyongtao
 * @create 2019-08-24 15:52
 */
@Slf4j
@RestController
@RequestMapping("/actTaskApi")
@Api(value = "工作流任务API")
public class ActTaskApi {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "提交任务")
    @PostMapping("/commitProcess/{taskId}")
    public void commitProcess(@PathVariable("taskId") String taskId,
                              @RequestBody Map<String, Object> variables) {
        if (ToolsUtils.isEmpty(variables)) {
            variables = new HashMap<>();
        }
        taskService.complete(taskId, variables);
    }
}
