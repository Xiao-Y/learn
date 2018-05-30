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
    // baseURL: 'https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop',
    // url: '/menuApi/findParentMenu/' + pid,
    url: 'menuApi/findParentMenu/' + pid,
    method: 'get'
  })
}

/**
 * 获取父菜单信息
 * @param pid
 */
export function findMenus() {
  // var url = 'api/menuApi/findMenus'
  // axios.get(url).then((response) => {
  //   return response.data
  // })
  return request({
    // baseURL: 'https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop',
    // baseURL: 'http://localhost:8080',
    url: 'menuApi/findMenus',
    method: 'get'
  })
}
