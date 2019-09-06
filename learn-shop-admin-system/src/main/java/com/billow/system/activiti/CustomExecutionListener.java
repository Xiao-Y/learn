package com.billow.system.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 运行流程监听
 *
 * @author liuyongtao
 * @create 2019-09-06 17:06
 */
@Component
public class CustomExecutionListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        // 监听结束事件
        if (ExecutionListener.EVENTNAME_END.equals(eventName)) {
            String businessKey = execution.getProcessInstanceBusinessKey();
        }
    }
}
