package com.billow.gateway.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 查询登陆用户信息
 *
 * @author LiuYongTao
 * @date 2018/11/20 15:19
 */
public class CustomUserDetailsService implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private UserRoleDao userRoleDao;
//    @Autowired
//    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        logger.info("查询用户：{} 的信息...", usercode);
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        if (ToolsUtils.isEmpty(usercode)) {
//            logger.error("用户名不能为空！");
//            throw new UsernameNotFoundException("用户名不能为空！");
//        }
//
//        // 查询用户信息
////        UserPo userPo = userDao.findUserInfoByUsercodeAndValidIndIsTrue(usercode);
//        UserPo userPo = userDao.findUserInfoByUsercode(usercode);
//        if (userPo == null) {
//            logger.error("用户：{}，不存在！", usercode);
//            throw new UsernameNotFoundException("用户：" + usercode + "，不存在");
//        }
//        if (!userPo.getValidInd()) {
//            logger.error("用户：{}，被锁定！", usercode);
//            throw new UsernameNotFoundException("用户：" + usercode + "，被锁定");
//        }
//        // 查询角色信息
//        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserIdAndValidIndIsTrue(userPo.getId());
//        if (CollectionUtils.isEmpty(userRolePos)) {
//            logger.error("用户：{}，未分配角色！", usercode);
//            throw new UsernameNotFoundException("用户：" + usercode + "，未分配角色");
//        }
//        userRolePos.stream().forEach(ur -> {
//            Long roleId = ur.getRoleId();
//            Optional<RolePo> rolePo = roleDao.findById(roleId);
//            if (!rolePo.isPresent()) {
//                logger.error("用户：{}，roleId:{},未查询到信息！", usercode, roleId);
//                return;
//            }
//            authorities.add(new SimpleGrantedAuthority(rolePo.get().getRoleCode()));
//        });
//        if (CollectionUtils.isEmpty(authorities)) {
//            logger.error("用户：{}，未分配权限！", usercode);
//            throw new UsernameNotFoundException("用户：" + usercode + "，未分配权限");
//        }
//        SecurityUser securityUser = new SecurityUser(userPo, authorities);
//        return securityUser;
        return null;
    }
}
