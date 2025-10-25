package com.billow.system.service.impl;

import com.billow.common.utils.UserTools;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.RoleDao;
import com.billow.system.dao.UserDao;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.po.UserPo;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.pojo.search.UserSearchParam;
import com.billow.system.pojo.vo.UserVo;
import com.billow.system.service.UserService;
import com.billow.system.service.redis.UserRedisKit;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:28
 */
@Slf4j
@Service
public class UserServiceImpl extends HighLevelServiceImpl<UserDao, UserPo, UserSearchParam> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRedisKit userRedisKit;
    @Autowired
    private UserTools userTools;

    @Override
    public Page<UserVo> findUserList(UserSearchParam userVo) {
        return this.findListByPage( userVo).map(this::convertToUserVo);
    }

    /**
     * userPo 转化为 userVo
     *
     * @param userPo
     * @return com.billow.user.pojo.vo.UserVo
     * @author LiuYongTao
     * @date 2019/8/5 13:49
     */
    private UserVo convertToUserVo(UserPo userPo) {
        UserVo userVo = ConvertUtils.convert(userPo, UserVo.class);
        if (ToolsUtils.isNotEmpty(userVo.getAddress())) {
            userVo.setCasAddress(userVo.getAddress().split(","));
        }
        return userVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo saveUser(UserVo userVo) {
        UserPo oldUser = null;
        boolean isEncryption = true;
        UserPo userPo = ConvertUtils.convert(userVo, UserPo.class);
        Long userId = userVo.getId();

        if (userId != null) {// 更新
            // 查询出旧的数据
            UserPo po = this.getById(userId);
            oldUser = ConvertUtils.convert(po, UserPo.class);
            // 删除用户角色关联，重新保存
            userRoleDao.deleteByUserId(userId);
            // 修改时，如果没有修改密码，则不加密密码
            String passwordPage = userVo.getPassword();
            if (ToolsUtils.isEmpty(passwordPage)) {
                isEncryption = false;
            }
        }

        // 未加密前(默认密码为用户名)
        String passwordSource = userVo.getUsercode();
        // 如果不为空则修改密码
        if (ToolsUtils.isNotEmpty(userVo.getPassword())) {
            passwordSource = userVo.getPassword();
        }

        if (isEncryption) {// 需要加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userPo.setPassword(bCryptPasswordEncoder.encode(passwordSource));
        } else {// 不需要加密，直接取旧的密码
            userPo.setPassword(oldUser.getPassword());
        }
        String address = null;
        if (ToolsUtils.isNotEmpty(userVo.getCasAddress())) {
            address = StringUtils.join(userVo.getCasAddress(), ",");
        }
        userPo.setAddress(address);
        // 更新/保存
        userDao.insert(userPo);
        // 保存用户的角色
        List<Long> roleIds = userVo.getRoleIds();
        List<UserRolePo> userRolePos = roleIds.stream()
                .map(m -> {
                    UserRolePo po = new UserRolePo();
                    po.setUserId(userId);
                    po.setRoleId(m);
                    po.setValidInd(true);
                    return po;
                }).collect(Collectors.toList());
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            userRoleDao.insertBatch(userRolePos);
        }
        UserVo vo = ConvertUtils.convert(userPo, UserVo.class);
        vo.setPassword(null);
        vo.setRoleIds(roleIds);
        vo.setCasAddress(userVo.getCasAddress());
        // 修改用户，需要重新登陆（redis 中插入 用户名-角色CODE）
        List<RolePo> rolePoList = roleDao.selectListByIds(roleIds);
        if (CollectionUtils.isNotEmpty(rolePoList)) {
            List<String> roleCodes = rolePoList.stream()
                    .map(RolePo::getRoleCode)
                    .collect(Collectors.toList());
            // 新添加的用户不管
            if (userId != null) {
                // 修改 usercode 放入redis中
                userRedisKit.setBlacklistOnEditUser(oldUser.getUsercode(), userPo.getUsercode(), roleCodes);
            }
        }
        userRedisKit.updateUserInfoCache(userPo.getUsercode());
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo deleteUserById(Long id) {
        UserPo userPo = this.getById(id);
        if (userPo != null) {
            userDao.deleteById(id);
        }
        userRedisKit.updateUserInfoCache(userPo.getUsercode());
        return ConvertUtils.convert(userPo, UserVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo prohibitUserById(Long id) {
        UserPo userPo = this.getById(id);
        if (userPo != null) {
            userPo.setValidInd(false);
            userDao.insert(userPo);
        }
        userRedisKit.updateUserInfoCache(userPo.getUsercode());
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

    @Override
    public UserVo getUserInfo() {
        String currentUserCode = userTools.getCurrentUserCode();
        if (ToolsUtils.isEmpty(currentUserCode)) {
            return null;
        }

        UserPo userPo = userDao.findByUsercode(currentUserCode);
        userPo.setPassword(null);
        return ConvertUtils.convert(userPo, UserVo.class);
    }

    @Override
    public Integer checkUserCode(String userCode) {
        return userDao.countByUsercodeIsAndValidIndIsTrue(userCode);
    }

    @Override
    public UserVo checkPassWord(String currentUserCode, String oldPassWord) {
        UserPo userPo = userDao.findByUsercode(currentUserCode);
        if (userPo == null) {
            return null;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(oldPassWord, userPo.getPassword())) {
            return ConvertUtils.convert(userPo, UserVo.class);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void editPassWord(UserVo oldUser) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        oldUser.setPassword(bCryptPasswordEncoder.encode(oldUser.getPassword()));
        userDao.insert(ConvertUtils.convert(oldUser, UserPo.class));
        userRedisKit.updateUserInfoCache(oldUser.getUsercode());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateUserIcon(UserVo userVo) {
        String usercode = userVo.getUsercode();
        UserPo userPo = userDao.findByUsercode(usercode);
        if (userPo == null) {
            return false;
        }
        userPo.setIconUrl(userVo.getIconUrl());
        userDao.insert(userPo);
        userRedisKit.updateUserInfoCache(userPo.getUsercode());
        return true;
    }

    @Override
    public UserVo findUserInfoById(Long id) {
        UserPo userPo = this.getById(id);
        UserVo userVo = this.convertToUserVo(userPo);
        List<UserRolePo> userRolePos = userRoleDao.findByUserIdIsAndValidIndIsTrue(id);
        if (ToolsUtils.isNotEmpty(userRolePos)) {
            List<Long> collect = userRolePos.stream().map(m -> m.getRoleId()).collect(Collectors.toList());
            userVo.setRoleIds(collect);
        }
        return userVo;
    }
}
