import request from '@/utils/request'

export function login(username, password) {
  return request({
    // baseURL: 'https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop',
    url: 'uaa/login',
    method: 'post',
    data: {
      username: username,
      password: password
    }
  })
}

export function logout() {
  return request({
    baseURL: 'https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop',
    url: '/user/logout',
    method: 'post'
  })
}
