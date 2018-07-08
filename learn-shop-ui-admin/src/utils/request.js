import axios from 'axios'
import {
  Message,
  MessageBox
} from 'element-ui'
import store from '../store'
import {
  getToken
} from '@/utils/auth'
import {showFullScreenLoading, tryHideFullScreenLoading} from '@/utils/loading'

// 创建axios实例
const service = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  showLoading: true,
  timeout: 30000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
  console.info('请求参数:', config)
  if (config.showLoading) {
    showFullScreenLoading();
  }
  if (store.getters.token) {
    config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  return config
}, error => {
  console.log(error) // for debug
  Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(response => {
    const res = response.data
    console.info('响应数据:', res)
    if (response.config.showLoading) {
      tryHideFullScreenLoading();
    }
    if (res.resCode !== '0000') {
      Message({
        message: res.resMsg,
        type: 'error',
        duration: 5 * 1000
      })

      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (res.resCode === 50008 || res.resCode === 50012 || res.resCode === 50014) {
        MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('FedLogOutActions').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    tryHideFullScreenLoading();
    console.log(error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
