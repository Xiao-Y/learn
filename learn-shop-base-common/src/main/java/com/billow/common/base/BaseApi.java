package com.billow.common.base;

import com.billow.common.resData.BaseResponse;
import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.vo.sys.RoleVo;
import com.billow.pojo.vo.user.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 公用control
 *
 * @author liuyongtao
 * @create 2018-05-19 14:40
 */
public class BaseApi {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    protected String applicationName;
    @Value("${eureka.instance.instance-id}")
    protected String instanceId;

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    /**
     * 获取用户对象
     *
     * @return com.billow.pojo.vo.user.UserVo
     * @author LiuYongTao
     * @date 2018/5/26 9:34
     */
    protected UserVo findUserVo() {
        return new UserVo();
    }

    /**
     * 获取用户的角色信息
     *
     * @return List<RoleVo>
     * @author LiuYongTao
     * @date 2018/5/29 11:35
     */
    protected List<RoleVo> findRoleVos() {
        List<RoleVo> roleVos = new ArrayList<>();
        RoleVo roleVo = new RoleVo();
        roleVo.setId(1L);
        roleVos.add(roleVo);
        return roleVos;
    }

    /**
     * 获取用户的角色CODE信息
     *
     * @return List<RoleVo>
     * @author LiuYongTao
     * @date 2018/5/29 11:35
     */
    protected List<String> findRoleCodes() {
        List<String> roleCodes = new ArrayList<>();
        roleCodes.add("admin");
        roleCodes.add("billow");
        return roleCodes;
    }

    /**
     * 获取用户名
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/5/26 9:34
     */
    protected String findUserCode() {
        return "billow";
    }

    /**
     * 获取基类返回数据
     *
     * @return T
     * @author LiuYongTao
     * @date 2018/5/26 9:40
     */
    protected <T> BaseResponse<T> getBaseResponse() {
        BaseResponse<T> base = new BaseResponse<>();
        base.setTraceID(this.getApplicationName());
        return base;
    }

    protected void getErrorInfo(Exception e) {
        e.printStackTrace();
        logger.error("ApplicationName：{}，InstanceId：{}",
                this.getApplicationName(), this.getInstanceId(), e);
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
