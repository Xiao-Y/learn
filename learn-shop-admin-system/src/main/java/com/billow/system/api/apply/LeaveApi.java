package com.billow.system.api.apply;

import com.billow.system.pojo.ex.LeaveEx;
import com.billow.system.service.ApplyInfoService;
import com.billow.tools.enums.ApplyTypeEnum;
import com.billow.common.utils.UserTools;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请假申请Api
 *
 * @author liuyongtao
 * @create 2020-04-30 10:31
 */
@Slf4j
@RestController
@RequestMapping("/leaveApi")
@Tag(name = "LeaveApi", description = "请假申请Api")
public class LeaveApi {

    @Autowired
    private UserTools userTools;
    @Autowired
    private ApplyInfoService applyInfoService;

    @Operation(summary = "提交请假申请")
    @PostMapping("/submitLeave")
    public void submitLeave(@RequestBody LeaveEx leaveEx) {
        String operator = userTools.getCurrentUserCode();
        applyInfoService.submitApplyInfo(operator, ApplyTypeEnum.LEAVE, leaveEx);
    }

    @Operation(summary = "提交请假任务")
    @PostMapping("/commitLeaveProcess/{procInstId}/{taskId}")
    public void commitLeaveProcess(@PathVariable("procInstId") String procInstId,
                                   @PathVariable("taskId") String taskId,
                                   @RequestBody LeaveEx leaveEx) {
        String operator = userTools.getCurrentUserCode();
        applyInfoService.commitLeaveProcess(operator, ApplyTypeEnum.LEAVE, leaveEx, procInstId, taskId);
    }
}
