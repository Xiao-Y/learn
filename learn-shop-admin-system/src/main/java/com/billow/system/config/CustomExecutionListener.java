package com.billow.system.config;

import com.billow.system.dao.ApplyInfoDao;
import com.billow.system.pojo.po.ApplyInfoPo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 运行流程监听
 *
 * @author liuyongtao
 * @create 2019-09-06 17:06
 */
@Slf4j
@Component
public class CustomExecutionListener implements ExecutionListener {

    @Autowired
    private ApplyInfoDao applyInfoDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        log.info("eventName:{}", eventName);
        // 监听结束事件
        if (ExecutionListener.EVENTNAME_END.equals(eventName)) {
            String businessKey = execution.getProcessInstanceBusinessKey();
            if (businessKey != null) {
                try {
                    Optional<ApplyInfoPo> optional = applyInfoDao.findById(new Long(businessKey));
                    if (optional.isPresent()) {
                        ApplyInfoPo applyInfoPo = optional.get();
                        applyInfoPo.setIsEnd(true);
                        applyInfoDao.save(applyInfoPo);
                    }
                } catch (Exception e) {
                    log.error("更新申请表 IsEnd 异常，", e);
                }
            }
        }
    }
}
