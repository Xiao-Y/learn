import requestUtils from '../../utils/requestUtils'

const baseUrl = 'public-job';

/**
 * 请自动任务列表
 */
export const LoadDataJobList = scheduleJobFilter => requestUtils.post(baseUrl + '/coreAutoTaskApi/findAutoTask', Object.assign(scheduleJobFilter));

