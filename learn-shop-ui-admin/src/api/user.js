import request from '@/utils/request'

export function getInfo(token) {
  return request({
    // baseURL: 'https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop',
    // url: '/user/info',
    url: 'admin-system/menuApi/homeMenus',
    method: 'get',
    params: {
      token
    }
  })
}
