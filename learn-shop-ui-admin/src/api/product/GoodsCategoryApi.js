import requestUtils from '../../utils/requestUtils'

const baseUrl = 'core-product/goodsCategoryApi';

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
 * 查询分类下拉列表数据
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindCategorySelect = () => requestUtils.post(baseUrl + '/findCategorySelect',{});
