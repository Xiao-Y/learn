import requestUtils from '../../utils/requestUtils'

const baseUrl = 'core-product/goodsSpecValueApi';

/**
 * 保存规格值信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const Add = data => requestUtils.post(baseUrl + '/add', Object.assign(data));

/**
 * 更新规格值信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const Update = data => requestUtils.put(baseUrl + '/update', Object.assign(data));

/**
 * 根据id删除规格值信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DelById = id => requestUtils.del(baseUrl + '/delById/' + id);

/**
 * 根据id禁用规格值信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitById = id => requestUtils.put(baseUrl + '/prohibitById/' + id);

/**
 * 通过 SpecKeyId 查询出所有的规格 Value
 * @param categoryId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindValueListBySpecKeyId = specKeyId => requestUtils.get(baseUrl + '/findListBySpecKeyId/' + specKeyId);
