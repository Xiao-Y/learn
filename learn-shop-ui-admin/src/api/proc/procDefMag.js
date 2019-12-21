import requestUtils from '../../utils/requestUtils'

const baseUrl = '../admin-system/actProcDefApi';

/**
 * 根据条件查询流程定义信息
 * @param procDefFilter
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const LoadDataProcDefList = procDefFilter => requestUtils.post(baseUrl + '/findProcDefList', Object.assign(procDefFilter));

/**
 * 挂起流程定义
 * @param processDefinitionId
 * @param cascade 是否级联
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SuspendProcess = (processDefinitionId, cascade) => requestUtils.put(baseUrl + '/suspendProcess/' + processDefinitionId + '/' + cascade);

/**
 * 激活流程定义
 * @param processDefinitionId
 * @param cascade 是否级联
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ActivateProcess = (processDefinitionId, cascade) => requestUtils.put(baseUrl + '/activateProcess/' + processDefinitionId + '/' + cascade);

/**
 * 通过 key 查询到最新的一个流程定义
 * @param key
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const FindDefByKey = key => requestUtils.get(baseUrl + '/findDefByKey/' + key);
