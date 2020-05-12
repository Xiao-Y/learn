import requestUtils from '../../../utils/requestUtils'

const baseUrl = '../admin-system/leaveApi';

/*********************************** 请假相关 **************************************/
/**
 * 提交请假申请
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SubmitLeave = (leaveInfo) => requestUtils.post(baseUrl + '/submitLeave', Object.assign(leaveInfo));

/**
 * 提交任务
 * @param taskInfo
 * @param procInstId
 * @param taskId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const CommitLeaveProcess = (taskInfo, procInstId, taskId) => requestUtils.post(baseUrl + '/commitLeaveProcess/' + procInstId + '/' + taskId, Object.assign(taskInfo));
