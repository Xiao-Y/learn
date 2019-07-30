package com.billow.zuul.config.security;


import com.billow.tools.constant.DictionaryType;
import com.billow.tools.utlis.ToolsUtils;
import com.billow.zuul.config.security.vo.DataDictionaryVo;
import com.billow.zuul.config.security.vo.PermissionVo;
import com.billow.zuul.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service("authService")
public class AuthService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final static String PERMISSION = "ROLE:PERMISSION:";
    private final static String DICTIONARY = "COMM:DICTIONARY";

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 判断是否有权限
     *
     * @param request
     * @param authentication
     * @return boolean
     * @author LiuYongTao
     * @date 2019/7/16 14:30
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean isPermission = false;

        Object principal = authentication.getPrincipal();
        logger.info("===>>> principal:{}", principal);

//        String contextPath = request.getContextPath();
//        logger.info("request.getContextPath:{}", contextPath);
        String targetURI = request.getRequestURI();
        logger.info("<<<=== targetURI:{}", targetURI);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && authorities.size() > 0) {
            // 获取 redis 中数据字典的数据
            Map<Long, String> dictionaryMap = redisUtils.getArray(DICTIONARY, DataDictionaryVo.class)
                    .stream()
                    .filter(f -> DictionaryType.SYS_MODEL_OCDE_SYSTEM_MODULE.equals(f.getSystemModule())
                            && DictionaryType.SYS_FIELD_OCDE_SYSTEM_MODULE.equals(f.getFieldType()))
                    .collect(Collectors.toMap(DataDictionaryVo::getId, DataDictionaryVo::getFieldDisplay));
            for (GrantedAuthority authority : authorities) {
                // 查询 redis 中的角色权限
                List<PermissionVo> permissionVos = redisUtils.getArray(PERMISSION + authority.getAuthority(), PermissionVo.class);
                for (PermissionVo permissionVo : permissionVos) {
                    if (ToolsUtils.isEmpty(permissionVo.getSystemModule())) {
                        String sourceURI = permissionVo.getUrl();
                        logger.info("===>>> sourceURI:{}", sourceURI);
                        if (antPathMatcher.match(sourceURI, targetURI)) {
                            logger.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
                            return true;
                        }
                    } else {
                        String[] split = permissionVo.getSystemModule().split(",");
                        for (String s : split) {
                            String sourceURI = "/" + dictionaryMap.get(new Long(s)) + permissionVo.getUrl();
                            logger.info("===>>> sourceURI:{}", sourceURI);
                            if (antPathMatcher.match(sourceURI, targetURI)) {
                                logger.info("\n===>>> sourceURI:{},targetURI:{} <<<===\n", sourceURI, targetURI);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
