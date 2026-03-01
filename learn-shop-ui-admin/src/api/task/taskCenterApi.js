import request from '../../utils/request';

// 启动任务
export function startTask(data) {
  return request({
    url: '/taskApi/start',
    method: 'post',
    data
  });
}

// 查询任务进度
export function queryTaskProgress(taskId) {
  return request({
    url: `/taskApi/progress/${taskId}`,
    method: 'get'
  });
}

// 查询任务组列表
export function queryTaskGroupList(params) {
  return request({
    url: '/taskApi/group/list',
    method: 'get',
    params
  });
}

// 查询任务详情列表
export function queryTaskDetailList(params) {
  return request({
    url: '/taskApi/detail/list',
    method: 'get',
    params
  });
}

// 重试子任务
export function retryTaskDetail(taskDetailId) {
  return request({
    url: `/taskApi/detail/retry/${taskDetailId}`,
    method: 'post'
  });
}
