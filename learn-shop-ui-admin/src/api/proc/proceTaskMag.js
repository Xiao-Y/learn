import requestUtils from '../../utils/requestUtils'

const baseUrl = 'admin-system/actTaskApi';

/**
 * 查询个人任务数量
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const QueryOwnerTaskCount = () => requestUtils.post(baseUrl + '/queryOwnerTaskCount');

