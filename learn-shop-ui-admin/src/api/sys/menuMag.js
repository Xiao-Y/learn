import request from '@/utils/request'

// export function login(username, password) {
//   return request({
//     url: '/user/login',
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
 */
export function saveOrUpdateMenu({id, pid, title, titleCode, path, icon}) {
  return request({
    url: 'admin-system/menuApi/saveOrUpdateMenu',
    method: 'post',
    data: {
      id: id,
      pid: pid,
      permissionName: title,
      permissionCode: titleCode,
      url: path,
      icon: icon
    }
  });
}

/**
 * 删除菜单及关联信息
 * @param ids
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
