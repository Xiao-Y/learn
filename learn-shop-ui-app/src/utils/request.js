import axios from 'axios'

import {Toast} from 'vant';

import {
    getAccessToken,
    setToken
} from '../utils/localStorage';

import {
    showFullScreenLoading,
    tryHideFullScreenLoading
} from '../utils/loading';

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

    console.info('请求参数:', config);

    return config
}, error => {

    tryHideFullScreenLoading();

    console.log(error); // for debug
    Promise.reject(error)
});

// respone拦截器
service.interceptors.response.use(response => {

        console.info('响应数据response:', response);

        const res = response.data;

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
            };

            setToken(data.resData);

            return data;
        } else {
            var message = res.resCode + " " + res.resMsg;
            Toast.fail(message);
            return Promise.reject(res);
        }
    },
    error => {
        tryHideFullScreenLoading();
        console.log("erro:", error); // for debug
        var message = error;
        if (error.response) {
            message = error.response.status + " " + error.response.statusText;
            if (error.response.status === 412) {
                message = '用户信息修改，需要重新登陆';
            }
            if (error.response.status === 403) {
                message = '没有权限执行该操作！';
            }
        }
        Toast.fail(message);
        return Promise.reject(error)
    });

export default service
