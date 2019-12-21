
import http from '../utils/requestUtils'

const baseUrl = '../core-product/goodsSpuApp';

/**
 * 通过 spuId 获取商品 sku 信息
 * @param spuId
 * @returns {*|Promise<AxiosResponse<T>>}
 * @constructor
 */
export const GetById = spuId => http.get(baseUrl + '/getById/' + spuId);