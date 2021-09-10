
import http from '../utils/requestUtils'

const baseUrl = '../app/goodsSkuApp';

/**
 * 通过 spuId 获取商品 sku 信息
 * @param spuId
 * @returns {*|Promise<AxiosResponse<T>>}
 * @constructor
 */
export const FindGoodsSku = spuId => http.get(baseUrl + '/findGoodsSku/' + spuId);

/**
 * 根据 spuId 查询 sku 规格表数据
 * @param spuId
 * @returns {*|Promise<AxiosResponse<T>>}
 * @constructor
 */
export const FindSkuSpec = spuId => http.get(baseUrl + '/findSkuSpec/' + spuId);