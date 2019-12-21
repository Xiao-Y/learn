import requestUtils from '../../utils/requestUtils'

const baseUrl = 'core-product/goodsSpecKeyApi';

/**
 * 通过 CategoryId 查询出所有的规格 KEY
 * @param categoryId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindKeyListByCategoryId = categoryId => requestUtils.get(baseUrl + '/findListByCategoryId/' + categoryId);

/**
 * 根据id删除规格信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DelById = id => requestUtils.del(baseUrl + '/delById/' + id);

/**
 * 根据id禁用规格信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitById = id => requestUtils.put(baseUrl + '/prohibitById/' + id);

/**
 * 保存一组规格信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SaveSpecKeyList = data => requestUtils.post(baseUrl + '/saveList', Object.assign(data));
