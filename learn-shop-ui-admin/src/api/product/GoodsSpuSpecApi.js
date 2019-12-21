import requestUtils from '../../utils/requestUtils'

const baseUrl = 'core-product/goodsSpuSpecApi';

/**
 * 根据 spuId 查询 spu 规格Key数据
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const FindSpuSpecKey = id => requestUtils.get(baseUrl + '/findSpuSpecKey/' + id);
