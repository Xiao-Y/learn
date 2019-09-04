import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.diagram.ActUtils;
import com.billow.base.workflow.vo.ProcessInstanceVo;
import com.billow.system.AdminSystemApp;
import org.activiti.engine.IdentityService;
import org.activiti.engine.repository.Deployment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
    @Autowired
    private IdentityService identityService;

    @Before
    public void setAss() {
//        identityService.setAuthenticatedUserId("admin");
    }

    @Test
    public void deploy() {
        Deployment leave = workFlowExecute.deploy("leave");
        System.out.println("id:" + leave.getId());
        System.out.println("key:" + leave.getKey());
    }

    @Test
    public void startProcessInstance() {
        ProcessInstanceVo processInstanceVo = workFlowExecute
                .startProcessInstance("admin", "key", "test", "1111", null);
        System.out.println(processInstanceVo.getProcessInstanceId());
    }

    @Test
    public void commitProcess() {
        workFlowExecute.commitProcess("37514", null);
    }


    @Autowired
    private ActUtils actUtils;

    @Test
    public void flowImg() throws Exception {
        InputStream inputStream = actUtils.genActiveProccessImage("32505");
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\aa.png"));
        // 输出图片内容
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            outputStream.write(b, 0, len);
        }
    }
}
