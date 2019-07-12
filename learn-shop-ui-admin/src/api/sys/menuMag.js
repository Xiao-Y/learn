import request from '@/utils/request'

// export function login1(username, password) {
//   return request({
//     url: '/user/login1',
//     method: 'post',
//     data: {
//       username,
//       password
//     }
//   })
// }

// export function getInfo(pid) {
//   return request({
//     url: '/menuApi/findParentMenu/' + pid,
//     method: 'get',
//     params: {token}
//   })
// }

/**
 * 获取父菜单信息
 * @param pid
 * @constructor
 */
export function findParentMenu(pid) {
  return request({
    url: 'admin-system/menuApi/findMenuById/' + pid,
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
    url: 'admin-system/menuApi/findMenus',
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
    url: 'admin-system/menuApi/saveOrUpdateMenu',
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
    url: 'admin-system/menuApi/delMenuByIds',
    method: 'delete',
    data: {
      ids
    }
  });
}
