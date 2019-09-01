package com.billow.system.activiti;

import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuyongtao
 * @create 2019-09-01 13:38
 */
//@Component
public class CustomGroupEntityManagerFactory implements SessionFactory {

    @Autowired
    private CustomGroupEntityManager customGroupEntityManager;

    @Override
    public Class<?> getSessionType() {
        //注意此处必须为Activiti原生的类，否则自定义类不会生效
        return GroupEntityManager.class;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        return customGroupEntityManager;
    }
}
