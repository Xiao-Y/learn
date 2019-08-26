import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.system.AdminSystemApp;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 工作流查询测试
 *
 * @author liuyongtao
 * @create 2019-08-26 15:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminSystemApp.class})
public class ActivitiQueryTests {

    @Autowired
    private WorkFlowQuery workFlowQuery;

    @Test
    public void queryProcessDefinition() {
        ProcessDefinitionEntity entity = new ProcessDefinitionEntityImpl();
//        entity.setDeploymentId("27501");
        entity.setKey("leave");
        List<ProcessDefinition> processDefinitions = workFlowQuery.queryProcessDefinition(entity);
        for (ProcessDefinition processDefinition : processDefinitions) {
            System.out.println("Id:" + processDefinition.getId());
            System.out.println("Key:" + processDefinition.getKey());

            System.out.println("DeploymentId:" + processDefinition.getDeploymentId());
            System.out.println("Version:" + processDefinition.getVersion());
            System.out.println("EngineVersion:" + processDefinition.getEngineVersion());
        }
    }
}
