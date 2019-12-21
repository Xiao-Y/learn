import request from '@/utils/request'

import requestUtils from '../../utils/requestUtils'

const baseUrl = '../core-product/goodsSpuApi';

/**
 * 根据条件查询商品信息
 * @param queryFilter
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindListByPage = queryFilter => requestUtils.post(baseUrl + '/list', Object.assign(queryFilter));


/**
 * 保存商品信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const Add = data => requestUtils.post(baseUrl + '/add', Object.assign(data));

/**
 * 更新商品信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const Update = data => requestUtils.put(baseUrl + '/update', Object.assign(data));

/**
 * 根据id删除商品信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DelById = id => requestUtils.del(baseUrl + '/delById/' + id);

/**
 * 根据id禁用商品信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitById = id => requestUtils.put(baseUrl + '/prohibitById/' + id);

/**
 * 查询商品图片
 * @param productId 商品id
 * @constructor
 */
export function GetProuctImageByProductId({productId}) {
  return request({
    url: 'core-product/productApi/findProductImageByProductId/' + productId,
    method: 'get'
  });
}

/**
 * 通过图片id 删除商品图上
 * @param id 图片id
 * @constructor
 */
export function DeleteProductImageById(id) {
  return request({
    url: 'core-product/productApi/deleteProductImageById/' + id,
    method: 'delete'
  });
}
