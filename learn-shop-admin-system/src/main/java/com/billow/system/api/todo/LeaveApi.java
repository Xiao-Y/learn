package com.billow.system.api.todo;

import com.billow.system.pojo.vo.LeaveVo;
import com.billow.system.service.LeaveService;
import com.billow.tools.utlis.UserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请假API
 *
 * @author liuyongtao
 * @create 2019-09-01 14:10
 */
@Slf4j
@RestController
@RequestMapping("/leaveApi")
@Api(value = "请假API")
public class LeaveApi {

    @Autowired
    private UserTools userTools;
    @Autowired
    private LeaveService leaveService;

    @ApiOperation(value = "提交请假申请")
    @PostMapping("/submitLeave")
    public void submitLeave(@RequestBody LeaveVo leaveVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        leaveService.submitLeave(currentUserCode, leaveVo);
    }
}
