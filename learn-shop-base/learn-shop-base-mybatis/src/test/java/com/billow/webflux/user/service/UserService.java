
package com.billow.webflux.user.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.webflux.user.pojo.po.UserPo;
import com.billow.webflux.user.pojo.search.UserSearchParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-14
 */
public interface UserService extends HighLevelService<UserPo,UserSearchParam> {

}