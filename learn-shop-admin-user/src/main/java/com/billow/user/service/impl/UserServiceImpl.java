package com.billow.user.service.impl;

import com.billow.common.jpa.DefaultSpec;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.user.dao.UserDao;
import com.billow.user.dao.UserRoleDao;
import com.billow.user.pojo.ex.RoleEx;
import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.po.UserRolePo;
import com.billow.user.pojo.vo.UserVo;
import com.billow.user.remote.AdminSystemRemote;
import com.billow.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:28
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private AdminSystemRemote adminSystemRemote;

    @Override
    public Page<UserPo> findUserList(UserVo userVo) {
        UserPo convert = ConvertUtils.convert(userVo, UserPo.class);
        DefaultSpec<UserPo> defaultSpec = new DefaultSpec<>(convert);
        Pageable pageable = new PageRequest(userVo.getPageNo(), userVo.getPageSize());
        return userDao.findAll(defaultSpec, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo saveUser(UserVo userVo) {
        UserPo convert = ConvertUtils.convert(userVo, UserPo.class);
        UserPo userPo = userDao.save(convert);
        Long userId = userVo.getId();
        if (userId != null) {
            // 删除用户角色关联，重新保存
            userRoleDao.deleteByUserId(userId);
        }
        // 保存用户的角色
        List<Long> roleIds = userVo.getRoleIds();
        List<UserRolePo> userRolePos = roleIds.stream().map(m -> {
            UserRolePo po = new UserRolePo();
            po.setUserId(userId);
            po.setRoleId(m);
            po.setValidInd(true);
            return po;
        }).collect(Collectors.toList());
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            userRoleDao.save(userRolePos);
        }
        UserVo vo = ConvertUtils.convert(userPo, UserVo.class);
        vo.setRoleIds(roleIds);
        // 修改用户，需要重新登陆（redis 中插入 用户名-角色CODE）
        BaseResponse<List<RoleEx>> base = adminSystemRemote.findRoleById(roleIds);
        if (base.getResCode().equals(ResCodeEnum.OK)) {
            List<RoleEx> roleExes = base.getResData();
            if (ToolsUtils.isNotEmpty(roleExes)) {
                List<String> roleCodes = roleExes.stream().map(m -> m.getRoleCode()).collect(Collectors.toList());
                logger.info("----------->>>>>>>>>{}", roleCodes);
            }
        } else {
            throw new RuntimeException(base.getResMsg());
        }
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo deleteUserById(Long id) {
        UserPo userPo = userDao.findOne(id);
        if (userPo != null) {
            userDao.delete(userPo);
        }
        return ConvertUtils.convert(userPo, UserVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo prohibitUserById(Long id) {
        UserPo userPo = userDao.findOne(id);
        if (userPo != null) {
            userPo.setValidInd(false);
            userDao.save(userPo);
        }
        return ConvertUtils.convert(userPo, UserVo.class);
    }

    @Override
    public UserVo findRoleIdsByUserId(Long id) {
        UserVo userVo = new UserVo();
        userVo.setId(id);
        List<UserRolePo> userRolePos = userRoleDao.findByUserIdIsAndValidIndIsTrue(id);
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            List<Long> collect = userRolePos.stream().map(m -> m.getRoleId()).collect(Collectors.toList());
            userVo.setRoleIds(collect);
        }
        return userVo;
    }
}
