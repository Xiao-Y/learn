import request from '@/utils/request'

const baseUrl = 'admin-system/permissionApi';

/**
 * 查询所有权限信息
 * @constructor
 */
export function LoadDataPermissionListAll() {
  return request({
    url: baseUrl + '/findPermissionAll',
    method: 'get'
  })
}

/**
 * 根据条件查询权限信息
 * @param roleFilter
 * @constructor
 */
export function LoadDataPermissionList(permissionFilter) {
  return request({
    url: baseUrl + '/findPermissionList',
    method: 'post',
    data: Object.assign(permissionFilter)
  })
}

/**
 * 保存权限信息
 * @param data
 * @constructor
 */
export function SavePermission(data) {
  return request({
    url: baseUrl + '/savePermission',
    method: 'post',
    data: Object.assign(data)
  });
}

/**
 * 更新权限信息
 * @param data
 * @constructor
 */
export function UpdatePermission(data) {
  return request({
    url: baseUrl + '/updatePermission',
    method: 'put',
    data: Object.assign(data)
  });
}

/**
 * 根据id删除权限信息
 * @param id
 * @constructor
 */
export function DeletePermissionById(id) {
  return request({
    url: baseUrl + '/deletePermissionById/' + id,
    method: 'delete'
  });
}

/**
 * 根据id禁用权限信息
 * @param id
 * @constructor
 */
export function ProhibitPermissionById(id) {
  return request({
    url: baseUrl + '/prohibitPermissionById/' + id,
    method: 'put'
  });
}
