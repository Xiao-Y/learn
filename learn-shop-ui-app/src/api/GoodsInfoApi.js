import http from '../utils/requestUtils'

const baseUrl = '../app/goodsInfoApp';


/**
 * 商品搜索
 * @param data
 */
export const Search = data => http.post(baseUrl + '/search', Object.assign(data));

