/* eslint-disable semi */
/* eslint-disable indent */
import axios from 'axios'
import {
  Message,
  MessageBox
} from 'element-ui'
import store from '../store'
import {
  getAccessToken,
  setToken
} from './cookieUtils'
import {
  showFullScreenLoading,
  tryHideFullScreenLoading
} from '@/utils/loading'

// 创建axios实例
const service = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  showLoading: true,
  timeout: 20000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
  if (config.showLoading) {
    showFullScreenLoading();
  }
  if (getAccessToken()) {
    config.headers['Authorization'] = "Bearer " + getAccessToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  console.info('请求参数:', config)
  return config
}, error => {
  tryHideFullScreenLoading();
  console.log(error) // for debug
  Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(response => {
    console.info('响应数据response:', response)
    const res = response.data
    if (response.config.showLoading) {
      tryHideFullScreenLoading();
    }
    if (res.resCode === '0000') { // 获取正常数据
      return response.data;
    } else if (res.resCode == '1111') { // 获取token
      // 构建返回页面数据
      var data = {
        resCode: res.resCode,
        resData: {
          accessToken: res.resData.accessToken,
          refreshToken: res.resData.refreshToken
        }
      }
      setToken(data.resData);
      return data;
    } else {
      var message = res.resCode + " " + res.resMsg;
      Message({
        message: message,
        type: 'error',
        duration: 5 * 1000
      });

      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      // if (res.resCode === 50008 || res.resCode === 50012 || res.resCode === 50014) {
      //   MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
      //     confirmButtonText: '重新登录',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(() => {
      //     store.dispatch('FedLogOutActions').then(() => {
      //       location.reload(); // 为了重新实例化vue-router对象 避免bug
      //     })
      //   })
      // }
      return Promise.reject(res);
    }
  },
  error => {
    tryHideFullScreenLoading();
    console.log("erro:",error) // for debug
    var message = error;
    if(error.response){
      message = error.response.status + " " + error.response.statusText;
      if(error.response.status === 412){
        message = '用户信息修改，需要重新登陆';
      }
      if(error.response.status === 403){
        message = '没有权限执行该操作！';
      }
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
