package com.billow.user.service.impl;

import com.billow.jpa.DefaultSpec;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.tools.utlis.UserTools;
import com.billow.user.dao.UserDao;
import com.billow.user.dao.UserRoleDao;
import com.billow.user.pojo.ex.RoleEx;
import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.po.UserRolePo;
import com.billow.user.pojo.vo.UserVo;
import com.billow.user.remote.AdminSystemRemote;
import com.billow.user.service.UserService;
import com.billow.user.service.redis.CommonUserRedis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    @Autowired
    private CommonUserRedis commonUserRedis;
    @Autowired
    private UserTools userTools;

    @Override
    public Page<UserVo> findUserList(UserVo userVo) {
        UserPo convert = ConvertUtils.convert(userVo, UserPo.class);
        DefaultSpec<UserPo> defaultSpec = new DefaultSpec<>(convert);
        Pageable pageable = new PageRequest(userVo.getPageNo(), userVo.getPageSize());
        return userDao.findAll(defaultSpec, pageable).map(this::convertToUserVo);
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
        UserPo convert = ConvertUtils.convert(userVo, UserPo.class);
        Long userId = userVo.getId();

        if (userId != null) {// 更新
            // 查询出旧的数据
            Optional<UserPo> po = userDao.findById(userId);
            oldUser = ConvertUtils.convert(po.get(), UserPo.class);
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
            convert.setPassword(bCryptPasswordEncoder.encode(passwordSource));
        } else {// 不需要加密，直接取旧的密码
            convert.setPassword(oldUser.getPassword());
        }
        String address = null;
        if (ToolsUtils.isNotEmpty(userVo.getCasAddress())) {
            address = StringUtils.join(userVo.getCasAddress(), ",");
        }
        convert.setAddress(address);
        // 更新/保存
        UserPo userPo = userDao.save(convert);
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
            userRoleDao.saveAll(userRolePos);
        }
        UserVo vo = ConvertUtils.convert(userPo, UserVo.class);
        vo.setPassword(null);
        vo.setRoleIds(roleIds);
        vo.setCasAddress(userVo.getCasAddress());
        // 修改用户，需要重新登陆（redis 中插入 用户名-角色CODE）
        BaseResponse<List<RoleEx>> base = adminSystemRemote.findRoleById(roleIds);
        if (base.getResCode().equals(ResCodeEnum.OK)) {
            List<RoleEx> roleExes = base.getResData();
            if (ToolsUtils.isNotEmpty(roleExes)) {
                List<String> roleCodes = roleExes.stream().map(m -> m.getRoleCode()).collect(Collectors.toList());
                // 新添加的用户不管
                if (userId != null) {
                    // 修改 usercode 放入redis中
                    commonUserRedis.setBlacklistOnEditUser(oldUser.getUsercode(), userPo.getUsercode(), roleCodes);
                }
            }
        } else {
            throw new RuntimeException(base.getResMsg());
        }
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo deleteUserById(Long id) {
        Optional<UserPo> userPo = userDao.findById(id);
        if (userPo.isPresent()) {
            userDao.delete(userPo.get());
        }
        return ConvertUtils.convert(userPo, UserVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserVo prohibitUserById(Long id) {
        Optional<UserPo> optional = userDao.findById(id);
        UserPo userPo = optional.orElse(new UserPo());
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
        userDao.save(ConvertUtils.convert(oldUser, UserPo.class));
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
        userDao.save(userPo);
        return true;
    }

    @Override
    public UserPo findUserInfoById(Long id) {
        return userDao.getOne(id);
    }
}
