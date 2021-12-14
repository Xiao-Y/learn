
package com.billow.webflux.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.webflux.user.pojo.po.UserPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.webflux.user.pojo.search.UserSearchParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-14
 */
public interface UserService extends IService<UserPo> {

    /**
     * 分页查询
     *
     * @param userSearchParam 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.user.pojo.po.UserPo>
     * @author billow
     * @since 2021-12-14
     */
    IPage<UserPo> findListByPage(UserSearchParam userSearchParam);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2021-12-14
     */
    boolean prohibitById(String id);
}