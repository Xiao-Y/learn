import request from '@/utils/request'

/**
 * 版任务列表
 */
export function requestDataList(scheduleJobFilter) {
  return request({
    url: 'public-job/coreAutoTask/findAutoTask',
    method: 'post',
    data: Object.assign(scheduleJobFilter)
  // data: {
  //   username,
  //   password
  // }
})
}
