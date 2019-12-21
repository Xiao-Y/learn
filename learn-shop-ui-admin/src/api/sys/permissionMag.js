import requestUtils from '../../utils/requestUtils'

const baseUrl = '../admin-system/permissionApi';

/**
 * 查询所有权限信息
 */
export const LoadDataPermissionListAll = () => requestUtils.get(baseUrl + '/findPermissionAll');

/**
 * 根据条件查询权限信息
 * @param permissionFilter
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const LoadDataPermissionList = permissionFilter => requestUtils.post(baseUrl + '/findPermissionList', Object.assign(permissionFilter));

/**
 * 保存权限信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SavePermission = data => requestUtils.post(baseUrl + '/savePermission', Object.assign(data));

/**
 * 更新权限信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const UpdatePermission = data => requestUtils.put(baseUrl + '/updatePermission', Object.assign(data));

/**
 * 根据id删除权限信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DeletePermissionById = id => requestUtils.del(baseUrl + '/deletePermissionById/' + id);

/**
 * 根据id禁用权限信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitPermissionById = id => requestUtils.put(baseUrl + '/prohibitPermissionById/' + id);
