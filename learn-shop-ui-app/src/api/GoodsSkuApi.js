
import http from '../utils/requestUtils'

const baseUrl = '../core-product/goodsSkuApp';

/**
 * 通过 spuId 获取商品 sku 信息
 * @param spuId
 * @returns {*|Promise<AxiosResponse<T>>}
 * @constructor
 */
export const FindGoodsSku = spuId => http.get(baseUrl + '/findGoodsSku/' + spuId);