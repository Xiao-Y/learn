import requestUtils from '../../utils/requestUtils'

const baseUrl = 'public-job/coreAutoTaskApi';

/**
 * 请自动任务列表
 */
export const LoadDataJobList = scheduleJobFilter => requestUtils.post(baseUrl + '/findAutoTask', Object.assign(scheduleJobFilter));

/**
 * 保存/更新自动任务信息
 * @param data
 * @constructor
 */
export const SaveAutoTask = data => requestUtils.post(baseUrl + '/saveAutoTask', Object.assign(data));

