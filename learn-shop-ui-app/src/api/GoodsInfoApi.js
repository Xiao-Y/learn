import http from '../utils/requestUtils'

const baseUrl = '../app/goodsInfoApp';


/**
 * 商品搜索
 * @param data 查询条件
 * @param pageSize 查询的条数
 * @param pageNo 当前页码
 */
export const Search = (data, pageNo = 1, pageSize = 10) => http.post(baseUrl + '/search?pageSize=' + pageSize + "&pageNo=" + pageNo, Object.assign(data));

