import request from '../utils/request'

var RequestUtils = {

  /**
   * get 方法，用于查询
   * @param url
   * @constructor
   */
  get: function (url) {
    return request({
      url: url,
      method: 'get'
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
    return request({
      url: url,
      method: 'post',
      data: data
    })
  }

}

export default RequestUtils;
