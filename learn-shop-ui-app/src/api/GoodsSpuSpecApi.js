
import http from '../utils/requestUtils'

const baseUrl = '../core-product/goodsSpuSpecApp';

/**
 * 根据 spuId 查询 spu 规格表数据
 * @param spuId
 * @returns {*|Promise<AxiosResponse<T>>}
 * @constructor
 */
export const FindSpuSpec = spuId => http.get(baseUrl + '/findSpuSpec/' + spuId);