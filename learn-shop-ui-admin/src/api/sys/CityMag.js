import requestUtils from '../../utils/requestUtils'

const baseUrl = 'admin-system/cityApi';

/**
 * 查询省市区
 * @param parentCityId
 * @constructor
 */
export const LoadCityData = (parentCityId) => requestUtils.get(baseUrl + '/findCity/' + parentCityId);
