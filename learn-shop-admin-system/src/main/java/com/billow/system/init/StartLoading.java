package com.billow.system.init;

import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 启动加载数据
 *
 * @author liuyongtao
 * @create 2019-07-22 16:03
 */
@Component
public class StartLoading implements InitializingBean {

    @Autowired
    private Map<String, IStartLoading> startLoading;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Map.Entry<String, IStartLoading> entry : startLoading.entrySet()) {
            entry.getValue().init();
        }
    }
}
