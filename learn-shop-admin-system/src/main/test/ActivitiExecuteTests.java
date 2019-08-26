import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.system.AdminSystemApp;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 工作流执行测试
 *
 * @author liuyongtao
 * @create 2019-08-26 15:40
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminSystemApp.class})
public class ActivitiExecuteTests {

    @Autowired
    private WorkFlowExecute workFlowExecute;


    @Test
    public void deploy() {
        Deployment leave = workFlowExecute.deploy("leave");
        System.out.println("id:" + leave.getId());
        System.out.println("key:" + leave.getKey());
    }
}
