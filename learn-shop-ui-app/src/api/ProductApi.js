
import http from '../utils/requestUtils'

const baseUrl = 'core-product/productApi';

/**
 * 通过 productId 获取商品 sku 信息
 * @param productId
 * @returns {*|Promise<AxiosResponse<T>>}
 * @constructor
 */
export const FindProductSku = productId => http.get(baseUrl + '/findProductSku/' + productId);