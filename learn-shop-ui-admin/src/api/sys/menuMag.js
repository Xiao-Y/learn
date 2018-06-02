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

export function saveOrUpdateMenu({id,pid, title, titleCode, path, icon}) {

  return request({
    url: 'admin-system/menuApi/saveOrUpdateMenu',
    method: 'post',
    data: {
      id: id,
      pid:pid,
      permissionName: title,
      permissionCode: titleCode,
      url: path,
      icon: icon
    }
  });
}
