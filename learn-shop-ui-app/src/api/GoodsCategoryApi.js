import http from '../utils/requestUtils'

const baseUrl = '../app/goodsCategoryApp';

/**
 * 通过父ID查询分类树
 * @param parentId 父ID
 * @returns {Promise<unknown>}
 * @constructor
 */
export const FindCategoryTree = parentId => http.get(baseUrl + '/findCategoryTree/' + parentId);

