import requestUtils from '../../utils/requestUtils'

const baseUrl = 'admin-system/actDeployApi';

/**
 * 根据条件查询流程定义信息
 * @param procDefFilter
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const LoadDataProcDefList = procDefFilter => requestUtils.post(baseUrl + '/findProcDefList', Object.assign(procDefFilter));

/**
 * 保存流程定义信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SaveProcDef = data => requestUtils.post(baseUrl + '/saveProcDef', Object.assign(data));

/**
 * 更新流程定义信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const UpdateProcDef = data => requestUtils.put(baseUrl + '/updateProcDef', Object.assign(data));

/**
 * 根据id删除流程定义信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DeleteProcDefById = id => requestUtils.del(baseUrl + '/deleteProcDefById/' + id);

/**
 * 根据id禁用流程定义信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitProcDefById = id => requestUtils.put(baseUrl + '/prohibitProcDefById/' + id);

/**
 * 根据id获取流程定义信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const FindProcDefById = id => requestUtils.get(baseUrl + '/findProcDefById/' + id);

/**
 * 查询 mailCode 的个数
 * @param userCode
 * @constructor
 */
export const CheckMailCode = mailCode => requestUtils.get(baseUrl + '/checkMailCode/' + mailCode);
