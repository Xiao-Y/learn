import request from '@/utils/request'

/**
 * 请自动任务列表
 */
export function LoadDataJobList(scheduleJobFilter) {
  return request({
    url: 'public-job/coreAutoTask/findAutoTask',
    method: 'post',
    data: Object.assign(scheduleJobFilter)
  })
}
