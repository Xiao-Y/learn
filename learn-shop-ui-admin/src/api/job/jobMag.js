import requestUtils from '../../utils/requestUtils'

// const baseUrl = 'public-job/coreAutoTaskApi';
const baseUrl = '../admin-system/coreAutoTaskApi';

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

/**
 * 测试Cron表达式下次运行的时间
 * @param cron 表达式
 * @param times 运行次数
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const TestRunCron = data => requestUtils.post(baseUrl + '/testRunCron', Object.assign(data));

/**
 * 启用、停止自动任务
 * @param jobId id
 * @param jobStatus 任务状态，0-停止，1-启用
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const UpdateJobStatus = (jobId, jobStatus) => requestUtils.put(baseUrl + '/updateJobStatus/' + jobId + '/' + jobStatus);

/**
 * 删除自动任务
 * @param jobId
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DeleteAutoTask = jobId => requestUtils.del(baseUrl + '/deleteAutoTask/' + jobId);

/**
 * 禁用自动任务
 * @param jobId id
 * @param validInd
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const UpdateJobInd = (jobId, validInd) => requestUtils.put(baseUrl + '/updateJobValidInd/' + jobId + '/' + validInd);

/**
 * 立即执行自动任务
 * @param jobName
 * @param jobGroup
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ImmediateExecutionTask = (jobName, jobGroup) => requestUtils.post(baseUrl + '/immediateExecutionTask', {
  jobName: jobName,
  jobGroup: jobGroup
});


/**
 * 检查自动任务参数是否正确
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const CheckAutoTask = data => requestUtils.post(baseUrl + '/checkAutoTask', Object.assign(data));

/**
 * 查询执行日志
 * @param data
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const LoadDataJobLogList = data => requestUtils.post(baseUrl + '/findAutoTaskLog', Object.assign(data));
