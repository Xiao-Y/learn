package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.CommentVo;
import com.billow.base.workflow.vo.TaskVo;
import com.billow.common.utils.UserTools;
import com.billow.system.pojo.po.ApplyInfoPo;
import com.billow.system.pojo.po.MytasklistPo;
import com.billow.system.pojo.search.ApplyInfoSearchParam;
import com.billow.system.pojo.search.MytasklistSearchParam;
import com.billow.system.pojo.vo.ApplyInfoVo;
import com.billow.system.service.ApplyInfoService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 申请操作
 *
 * @author liuyongtao
 * @create 2019-09-06 11:22
 */
@Slf4j
@RestController
@RequestMapping("/applyApi")
@Tag(name = "ApplyApi", description = "申请操作API")
public class ApplyApi {

    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private UserTools userTools;
    @Autowired
    private ApplyInfoService applyInfoService;

    @Operation(summary = "查询个人任务列表")
    @PostMapping("/queryMyTaskList")
    public Page<MytasklistPo> queryMyTaskList(@RequestBody MytasklistSearchParam mytasklistSearchParam) {
        String currentUserCode = userTools.getCurrentUserCode();
        mytasklistSearchParam.setAssignee(currentUserCode);
        Page<MytasklistPo> applyInfoVoPage = applyInfoService.queryMyTaskList(mytasklistSearchParam);
        return applyInfoVoPage;
    }

    @Operation(summary = "查询个人任务数量")
    @GetMapping("/queryAssigneeTaskCount")
    public long queryAssigneeTaskCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        TaskVo taskVo = new TaskVo();
        taskVo.setAssignee(currentUserCode);
        long count = workFlowQuery.queryAssigneeTaskCount(taskVo);
        return count;
    }

    @Operation(summary = "我发起的流程（所有的）")
    @PostMapping("/myStartProdeList")
    public Page<ApplyInfoPo> myStartProdeList(@RequestBody ApplyInfoSearchParam applyInfoVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        applyInfoVo.setApplyUserCode(currentUserCode);
        Page<ApplyInfoPo> page = applyInfoService.myStartProdeList(applyInfoVo);
        return page;
    }

    @Operation(summary = "我发起的流程数量（所有的）")
    @GetMapping("/myStartProdeCount")
    public long myStartProdeCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        long count = workFlowQuery.queryMyStartProdeAllCount(currentUserCode);
        return count;
    }

    @Operation(summary = "运行中的的流程")
    @GetMapping("/ongoingCount")
    public long ongoingCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        long count = workFlowQuery.queryMyStartProdeActiveCount(currentUserCode);
        return count;
    }

    @Operation(summary = "认领任务")
    @PostMapping("/claimTask/{taskId}")
    public void claimTask(@PathVariable String taskId) {
        String currentUserCode = userTools.getCurrentUserCode();
        workFlowExecute.claim(taskId, currentUserCode);
    }

//    @Operation(summary = "放弃认领任务")
//    @PostMapping("/unclaimTask/{taskId}")
//    public void unclaimTask(@PathVariable String taskId) {
//        workFlowExecute.unclaim(taskId);
//    }

//    @Operation(summary = "查询任务列表")
//    @PostMapping("/queryTaskList")
//    public Page<TaskVo> queryTaskList(@RequestBody TaskVo taskVo) {
//        Page<TaskVo> taskVos = workFlowQuery.queryTaskList(taskVo, taskVo.getOffset(), taskVo.getPageSize());
//        return taskVos;
//    }

    @Operation(summary = "删除已经结束的申请")
    @DeleteMapping("/deleteApplyInfoById/{id}")
    public void submitLeave(@PathVariable Long id) {
        applyInfoService.deleteApplyInfoById(id);
    }

    @Operation(summary = "根据ID查询申请信息")
    @GetMapping("/findApplyById/{id}")
    public ApplyInfoVo findApplyById(@PathVariable Long id) {
        ApplyInfoVo applyInfoVo = applyInfoService.findLeaveById(id);
        return applyInfoVo;
    }

    @Operation(summary = "通过流程实例id 查询批注信息")
    @GetMapping("/findCommentListByProcInstId/{procInstId}")
    public List<CommentVo> findCommentListByProcInstId(@PathVariable("procInstId") String procInstId) {
        List<CommentVo> commentVos = workFlowQuery.findCommentListByProcInstId(procInstId);
        return commentVos;
    }
}
