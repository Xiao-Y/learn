package com.billow.system.activiti;

import com.billow.system.activiti.exception.ActivitiException;
import com.billow.system.dao.RoleDao;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.po.UserRolePo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuyongtao
 * @create 2019-09-01 13:34
 */
@Slf4j
//@Component
public class CustomGroupEntityManager implements GroupEntityManager, Session {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    @Override
    public Group createNewGroup(String groupId) {
        throw new ActivitiException("添加新用户");
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        throw new ActivitiException("的 createNewGroupQuery 方法查询");
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new ActivitiException("的 findGroupByQueryCriteria 方法查询");
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new ActivitiException("的 findGroupCountByQueryCriteria 方法查询");
    }

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
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return null;
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        return 0;
    }

    @Override
    public boolean isNewGroup(Group group) {
        return false;
    }

    @Override
    public GroupEntity create() {
        return null;
    }

    @Override
    public GroupEntity findById(String entityId) {
        return null;
    }

    @Override
    public void insert(GroupEntity entity) {

    }

    @Override
    public void insert(GroupEntity entity, boolean fireCreateEvent) {

    }

    @Override
    public GroupEntity update(GroupEntity entity) {
        return null;
    }

    @Override
    public GroupEntity update(GroupEntity entity, boolean fireUpdateEvent) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(GroupEntity entity) {

    }

    @Override
    public void delete(GroupEntity entity, boolean fireDeleteEvent) {

    }
}
