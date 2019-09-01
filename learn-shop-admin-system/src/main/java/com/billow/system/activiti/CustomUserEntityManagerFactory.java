package com.billow.system.activiti;

import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuyongtao
 * @create 2019-09-01 13:37
 */
//@Component
public class CustomUserEntityManagerFactory implements SessionFactory {

    // 使用自定义的User管理类
    @Autowired
    private CustomUserEntityManager customUserEntityManager;

    @Override
    public Class<?> getSessionType() {
        //注意此处也必须为Activiti原生类
        return UserEntityManager.class;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        return customUserEntityManager;
    }
}
