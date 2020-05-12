package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.CustomPage;
import com.billow.base.workflow.vo.DeploymentVo;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import com.billow.common.base.BaseApi;
import com.billow.tools.utlis.StringUtils;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
public class ActDeployApi extends BaseApi {

    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;

    @ApiOperation(value = "部署流程（文件）")
    @PostMapping("/deploy/file")
    public boolean deploy(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        log.info("文件名：{}", originalFilename);
        try {
            String fileName = StringUtils.substringBeforeLast(originalFilename, ".");
            Deployment deploy = workFlowExecute.deploy(originalFilename, file.getInputStream());
            if (deploy == null) {
                log.error("流程部署失败：deploy is null");
                return false;
            }
            // 查询流程定义是否存在，确定是否部署成功
            ProcessDefinitionVo vo = new ProcessDefinitionVo();
            vo.setDeploymentId(deploy.getId());
            List<ProcessDefinitionVo> definitions = workFlowQuery.queryProcessDefinition(vo);
            return ToolsUtils.isNotEmpty(definitions);
        } catch (Exception e) {
            log.error("流程部署失败：", e);
            return false;
        }
    }

    @ApiOperation(value = "查询流程部署列表")
    @PostMapping("/findProcDeployList")
    public CustomPage<DeploymentVo> findProcDeployList(@RequestBody DeploymentVo vo) {
        CustomPage<DeploymentVo> deploymentPage = workFlowQuery.queryDeployment(vo, vo.getOffset(), vo.getPageSize());
        return deploymentPage;
    }

    @ApiOperation(value = "根据id删除流程部署,(cascade：是否级联删除)")
    @DeleteMapping("/delProceDeployById/{deploymentId}/{cascade}")
    public void delProceDeployById(@PathVariable String deploymentId, @PathVariable boolean cascade) {
        workFlowExecute.deleteDeployment(deploymentId, cascade);
    }
}
