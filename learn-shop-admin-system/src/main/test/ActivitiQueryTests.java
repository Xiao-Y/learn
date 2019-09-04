import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.diagram.ActUtils;
import com.billow.base.workflow.vo.ProcessDefinitionVo;
import com.billow.system.AdminSystemApp;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    @Autowired
    private ActUtils actUtils;

    @Test
    public void queryProcessDefinition() {
        ProcessDefinitionVo entity = new ProcessDefinitionVo();
//        entity.setDeploymentId("27501");
        entity.setKey("leave");
        List<ProcessDefinitionVo> processDefinitions = workFlowQuery.queryProcessDefinition(entity);
        for (ProcessDefinitionVo processDefinition : processDefinitions) {
            System.out.println("Id:" + processDefinition.getId());
            System.out.println("Key:" + processDefinition.getKey());

            System.out.println("DeploymentId:" + processDefinition.getDeploymentId());
            System.out.println("Version:" + processDefinition.getVersion());
            System.out.println("EngineVersion:" + processDefinition.getEngineVersion());
        }
    }

    @Test
    public void imag3() throws Exception {
        InputStream inputStream = actUtils.genActiveProccessImage("75002");
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\aa.png"));
        // 输出图片内容
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
        outputStream.close();

        inputStream = actUtils.genOriginalProcessImage("72501");
        outputStream = new FileOutputStream(new File("D:\\bb.png"));
        // 输出图片内容
        b = new byte[1024];
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
        outputStream.close();
    }
}
