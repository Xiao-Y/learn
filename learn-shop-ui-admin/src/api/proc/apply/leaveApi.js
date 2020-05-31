import requestUtils from '../../../utils/requestUtils'

const baseUrl = '../admin-system/applyApi';

/**
 * 提交请假申请
 * @param id
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const SubmitLeave = leaveInfo => requestUtils.post(baseUrl + '/submitLeave', Object.assign(leaveInfo));

/************************* TODO 相关的 ******************************/
/**
 * 提交请假任务
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const CommitLeaveProcess = (leaveInfo, procInstId, taskId) => requestUtils.post(baseUrl
  + '/commitLeaveProcess/' + procInstId + "/" + taskId, Object.assign(leaveInfo));
