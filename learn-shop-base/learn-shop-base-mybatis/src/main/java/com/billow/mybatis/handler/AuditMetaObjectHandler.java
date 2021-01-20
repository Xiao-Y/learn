package com.billow.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.billow.tools.utlis.UserTools;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 自动填充
 *
 * @author liuyongtao
 * @create 2019-11-01 9:31
 */
public class AuditMetaObjectHandler implements MetaObjectHandler {

    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";
    public static final String CREATOR_CODE = "creatorCode";
    public static final String UPDATER_CODE = "updaterCode";
    public static final String VALID_IND = "validInd";

    @Autowired
    private UserTools userTools;

    @Override
    public void insertFill(MetaObject metaObject) {

        Date now = new Date();
        if (metaObject.hasSetter(CREATE_TIME)) {
            this.setFieldValByName(CREATE_TIME, now, metaObject);
        }
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, now, metaObject);
        }
        if (metaObject.hasSetter(VALID_IND)) {
            this.setFieldValByName(VALID_IND, true, metaObject);
        }

        String username = userTools.getCurrentUserCode();
        if (metaObject.hasSetter(CREATOR_CODE)) {
            this.setFieldValByName(CREATOR_CODE, username, metaObject);
        }
        if (metaObject.hasSetter(UPDATER_CODE)) {
            this.setFieldValByName(UPDATER_CODE, username, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
        if (metaObject.hasSetter(UPDATER_CODE)) {
            String username = userTools.getCurrentUserCode();
            this.setFieldValByName(UPDATER_CODE, username, metaObject);
        }
    }

}
