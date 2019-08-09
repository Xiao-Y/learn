import request from '../utils/request'

var RequestUtils = {

  /**
   * get 方法，用于查询
   * @param url
   * @constructor
   */
  get: function (url) {
    return new Promise((resolve, reject) => {
      request({
        url: url,
        method: 'get'
      }).then(res => {
        resolve(res);
      }).catch(res => {
        reject(res)
      })
    })
  },
  /**
   * put 方法，用于更新
   * @param url
   * @param data
   * @constructor
   */
  put: function (url, data) {
    return request({
      url: url,
      method: 'put',
      data: data
    });
  },
  /**
   * delete 方法，用于删除
   *
   * @param url
   * @param data
   * @constructor
   */
  del: function (url, data) {
    return request({
      url: url,
      method: 'delete',
      data: data
    });
  },
  /**
   * post 方法，用于添加，查询
   * @param url
   * @param data
   * @constructor
   */
  post: function (url, data) {
    return new Promise((resolve, reject) => {
      request({
        url: url,
        method: 'post',
        data: data
      }).then(res => {
        resolve(res);
      }).catch(res => {
        reject(res)
      })
    })
  },
  upload: function (url, formdata) {
    return request({
      headers: {'Content-Type': 'multipart/form-data'},
      url: url,
      method: 'post',
      data: formdata
    });
  }

}

export default RequestUtils;
