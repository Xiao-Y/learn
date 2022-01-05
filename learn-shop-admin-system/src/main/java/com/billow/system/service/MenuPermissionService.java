
package com.billow.system.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.system.pojo.po.MenuPermissionPo;
import com.billow.system.pojo.search.MenuPermissionSearchParam;
import com.billow.system.pojo.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 * 菜单权限 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-26
 */
public interface MenuPermissionService extends HighLevelService<MenuPermissionPo, MenuPermissionSearchParam>
{

    /**
     * 查询菜单下的资源信息
     *
     * @param menuId
     * @return List<PermissionVo>
     * @author 千面
     * @date 2022/1/4 9:02
     */
    List<PermissionVo> findPermissionByMenuId(Long menuId);
}