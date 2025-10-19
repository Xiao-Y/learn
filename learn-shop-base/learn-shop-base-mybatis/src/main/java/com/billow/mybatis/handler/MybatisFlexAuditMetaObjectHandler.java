
package com.billow.mybatis.handler;

import com.billow.mybatis.pojo.BasePo;
import com.billow.mybatis.utils.MybatisUserTools;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充处理器
 * 实现 mybatis-flex 的监听器接口
 */
@Component
public class MybatisFlexAuditMetaObjectHandler implements InsertListener, UpdateListener {

    @Autowired
    private MybatisUserTools mybatisUserTools;

    /**
     * 插入时自动填充
     */
    @Override
    public void onInsert(Object o) {
        Date now = new Date();
        // 使用反射设置字段值
        try {
            if (o instanceof BasePo entity) {
                // 设置创建时间
                entity.setCreateTime(now);
                // 设置更新时间
                entity.setUpdateTime(now);
                // 设置有效性标识
                entity.setValidInd(true);
                String username = mybatisUserTools.getCurrentUserCode();
                if (username != null && !username.isEmpty()) {
                    // 设置创建人
                    entity.setCreatorCode(username);
                    // 设置更新人
                    entity.setUpdaterCode(username);
                }
            }
        } catch (Exception e) {
            // 日志记录异常
            e.printStackTrace();
        }
    }

    /**
     * 更新时自动填充
     */
    @Override
    public void onUpdate(Object o) {
        Date now = new Date();
        // 使用反射设置字段值
        try {
            if (o instanceof BasePo entity) {
                // 设置更新时间
                entity.setUpdateTime(now);
                String username = mybatisUserTools.getCurrentUserCode();
                if (username != null && !username.isEmpty()) {
                    // 设置更新人
                    entity.setUpdaterCode(username);
                }
            }
        } catch (Exception e) {
            // 日志记录异常
            e.printStackTrace();
        }
    }
}