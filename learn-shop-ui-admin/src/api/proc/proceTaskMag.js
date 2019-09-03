import requestUtils from '../../utils/requestUtils'

const baseUrl = 'admin-system/actTaskApi';

/**
 * 我发起的流程（所有的）
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const MyStartCount = () => requestUtils.get(baseUrl + '/myStartProdeCount');

/**
 * 查询个人任务数量
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const QueryAssigneeTaskCount = () => requestUtils.get(baseUrl + '/queryAssigneeTaskCount');

/**
 * 进行中的的流程（运行中的）
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const OngoingCount = () => requestUtils.get(baseUrl + '/auditProgressProdeCount');

/**
 * 加载我的任务任务列表（已签收和未签收的）
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const LoadDataTaskList = taskInfo => requestUtils.post(baseUrl + '/queryMyTaskList', Object.assign(taskInfo));

