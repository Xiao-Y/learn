import requestUtils from '../../utils/requestUtils'

const baseUrl = '../core-product/goodsSkuApi';

/**
 * 根据 spuId 查询 sku 信息
 * @param spuId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindGoodsSku = spuId => requestUtils.get(baseUrl + '/findGoodsSku/' + spuId);

/**
 * 根据id查询sku表数据
 * @param skuId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const GetById = skuId => requestUtils.get(baseUrl + '/getById/' + skuId);

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
