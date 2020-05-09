import requestUtils from '../../utils/requestUtils'

const baseUrl = '../admin-system/applyApi';

/************************ 申请相关 ****************************/
// // 定义申请页面映射
// const apply = new Map();
// apply.set("leave", "workbenchApplyLeave");
//
// /**
//  * 获取申请页面
//  * @param key
//  * @returns {V}
//  * @constructor
//  */
// export const FindApplyPage = (key) => {
//   return apply.get(key);
// };

/**
 * 删除已经结束的申请
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DeleteApplyInfoById = (id) => requestUtils.del(baseUrl + '/deleteApplyInfoById/' + id);

/**
 * 根据id获取流程部署图片
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ViewExecutionImgById = id => {
  return '../admin-system/deployImageAction/viewExecutionImgById/' + id;
};

/**
 * 认领任务
 * @param id
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const ClaimTask = taskId => requestUtils.post(baseUrl + '/claimTask/' + taskId);

/************************* TODO 相关的 ******************************/
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
export const OngoingCount = () => requestUtils.get(baseUrl + '/ongoingCount');

/**************************** 列表相关 **********************************/
/**
 * 我发起的流程（所有的）
 * @param applyInfo
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const MyStartProdeList = applyInfo => requestUtils.post(baseUrl + '/myStartProdeList', Object.assign(applyInfo));


/**
 * 加载我的任务任务列表（已签收和未签收的）
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const LoadDataTaskList = taskInfo => requestUtils.post(baseUrl + '/queryMyTaskList', Object.assign(taskInfo));

/*********************************** 请假相关 **************************************/
/**
 * 提交任务
 * @param taskInfo
 * @param procInstId
 * @param taskId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const CommitLeaveProcess = (taskInfo, procInstId, taskId) => requestUtils.post(baseUrl + '/commitLeaveProcess/' + procInstId + '/' + taskId, Object.assign(taskInfo));

/**
 * 通过流程实例id 查询批注信息
 * @param procInstId
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindCommentListByProcInstId = procInstId => requestUtils.get(baseUrl + '/findCommentListByProcInstId/' + procInstId);
