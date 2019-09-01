package com.billow.system.activiti;

import com.billow.system.activiti.exception.ActivitiException;
import com.billow.system.dao.RoleDao;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.feign.AdminUserFeign;
import com.billow.system.pojo.ex.UserEx;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.tools.resData.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于对接activiti用户信息
 *
 * @author liuyongtao
 * @create 2019-09-01 12:46
 */
@Slf4j
//@Component
public class CustomUserEntityManager implements UserEntityManager, Session {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AdminUserFeign adminUserFeign;

    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Group> groups = new ArrayList<>();
        List<UserRolePo> roleIdByUserId = userRoleDao.findRoleIdByUserId(new Long(userId));
        roleIdByUserId.stream().forEach(f -> {
            RolePo one = roleDao.findOne(f.getRoleId());
            Group group = new GroupEntityImpl();
            group.setId(one.getId().toString());
            group.setName(one.getRoleCode());
            group.setType("assignment");
            groups.add(group);
        });
        return groups;
    }

    @Override
    public UserEntity findById(String entityId) {
        BaseResponse<UserEx> re = adminUserFeign.findUserInfoById(new Long(entityId));
        log.info("ResCode:{}", re.getResCode());
        log.info("ResMsg:{}", re.getResMsg());
        UserEx userEx = re.getResData();
        log.info("ResData:{}", userEx);

        UserEntity userEntity = new UserEntityImpl();
        userEntity.setId(userEx.getId().toString());
        userEntity.setFirstName(userEx.getUsername());
        userEntity.setLastName(userEx.getUsername());
        userEntity.setPassword(userEx.getPassword());
        return userEntity;
    }

    @Override
    public User createNewUser(String userId) {
        throw new ActivitiException("添加新用户");
    }

    @Override
    public void updateUser(User updatedUser) {
        throw new ActivitiException("更新新用户");
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new ActivitiException("的 findUserByQueryCriteria 方法查询");
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new ActivitiException("的 findUserByQueryCriteria 方法查询");
    }

    @Override
    public UserQuery createNewUserQuery() {
        return new UserQueryImpl();
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        throw new ActivitiException("的 checkPassword 方法查询");
    }

    @Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new ActivitiException("的 findUsersByNativeQuery 方法查询");
    }

    @Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new ActivitiException("的 findUserCountByNativeQuery 方法查询");
    }

    @Override
    public boolean isNewUser(User user) {
        throw new ActivitiException("的 isNewUser 方法查询");
    }

    @Override
    public Picture getUserPicture(String userId) {
        throw new ActivitiException("的 getUserPicture 方法");
    }

    @Override
    public void setUserPicture(String userId, Picture picture) {
        throw new ActivitiException("的 setUserPicture 方法");
    }

    @Override
    public void deletePicture(User user) {
        throw new ActivitiException("的 deletePicture 方法");
    }

    @Override
    public UserEntity create() {
        throw new ActivitiException("的 create 方法");
    }

    @Override
    public void insert(UserEntity entity) {
        throw new ActivitiException("的 insert 方法");
    }

    @Override
    public void insert(UserEntity entity, boolean fireCreateEvent) {
        throw new ActivitiException("的 insert 方法");
    }

    @Override
    public UserEntity update(UserEntity entity) {
        throw new ActivitiException("的 update 方法");
    }

    @Override
    public UserEntity update(UserEntity entity, boolean fireUpdateEvent) {
        throw new ActivitiException("的 update 方法");
    }

    @Override
    public void delete(String id) {
        throw new ActivitiException("的 delete 方法");
    }

    @Override
    public void delete(UserEntity entity) {
        throw new ActivitiException("的 delete 方法");
    }

    @Override
    public void delete(UserEntity entity, boolean fireDeleteEvent) {
        throw new ActivitiException("的 delete 方法");
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
