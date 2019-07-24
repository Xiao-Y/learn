import request from '@/utils/request'

const baseUrl = 'admin-system/menuApi';

/**
 * 获取父菜单信息
 * @param pid
 * @constructor
 */
export function findParentMenu(pid) {
  return request({
    url: baseUrl + '/findMenuById/' + pid,
    method: 'get'
  })
}

/**
 * 获取父菜单信息
 * @param pid
 * @constructor
 */
export function findMenus() {
  return request({
    url: baseUrl + '/findMenus',
    method: 'get'
  })
}

/**
 * 保存或者更新菜单信息
 * @param id
 * @param pid
 * @param title
 * @param titleCode
 * @param path
 * @param icon
 * @param validInd
 * @param display
 * @constructor
 */
export function saveOrUpdateMenu({id, pid, title, titleCode, path, icon, validInd, display}) {
  return request({
    url: baseUrl + '/saveOrUpdateMenu',
    method: 'put',
    data: {
      id: id,
      pid: pid,
      menuName: title,
      menuCode: titleCode,
      url: path,
      icon: icon,
      validInd: validInd,
      display: display
    }
  });
}

/**
 * 删除菜单及关联信息
 * @param ids
 * @constructor
 */
export function delMenuByIds(ids) {
  return request({
    url: baseUrl + '/delMenuByIds',
    method: 'delete',
    data: {
      ids
    }
  });
}

/**
 * 通过token 获取菜单信息
 * @param token
 * @constructor
 */
export function getHomeMenus(token) {
  return request({
    url: baseUrl + '/homeMenus',
    method: 'get',
    params: {
      token
    }
  })
}

/**
 * 查询 menuCode 是否重复
 * @param menuCode
 * @constructor
 */
export function CheckMenuCode(menuCode) {
  return request({
    url: baseUrl + '/checkMenuCode/' + menuCode,
    method: 'get'
  })
}