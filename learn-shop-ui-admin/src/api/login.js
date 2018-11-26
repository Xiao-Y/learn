import request from '@/utils/request'

export function login(username, password) {
  return request({
    // baseURL: 'https://easy-mock.com/mock/5af2c3514b7b62162e8fc3cd/shop',
    url: 'uaa/oauth/token',
    method: 'post',
    params: {
      "username": username,
      "password": password,
      "grant_type": "password",
      "scope": "app"
    },
    auth: {
      username: 'webapp',
      password: 'webapp'
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
