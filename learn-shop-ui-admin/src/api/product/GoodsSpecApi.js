import requestUtils from '../../utils/requestUtils'

const baseUrl = '../core-product';

/**
 * 通过 CategoryId 查询出所有的规格 KEY
 * @param categoryId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindListByCategoryId = categoryId => requestUtils.get(baseUrl + '/goodsSpecKeyApi/findListByCategoryId/' + categoryId);

/**
 * 通过 SpecKeyId 查询出所有的规格 Value
 * @param categoryId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindListBySpecKeyId = specKeyId => requestUtils.get(baseUrl + '/goodsSpecValueApi/findListBySpecKeyId/' + specKeyId);

