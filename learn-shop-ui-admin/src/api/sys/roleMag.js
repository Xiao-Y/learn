import requestUtils from '../../utils/requestUtils'


const baseUrl = '../admin-system/roleApi';

/**
 * 根据条件查询角色信息
 * @param roleFilter
 * @constructor
 */
export const LoadDataRoleList = roleFilter => requestUtils.post(baseUrl + '/findRoleList', Object.assign(roleFilter));

/**
 * 根据角色ID查询权限ID
 * @param roleFilter
 * @constructor
 */
export const LoadDataPermissionIdList = roleId => requestUtils.get(baseUrl + '/findPermissionByRoleId/' + roleId);

/**
 * 根据角色ID查询菜单ID
 * @param roleFilter
 * @constructor
 */
export const LoadDataMenuIdList = roleId => requestUtils.get(baseUrl + '/findMenuByRoleId/' + roleId);

/**
 * 保存角色信息
 * @param data
 * @constructor
 */
export const SaveRole = data => requestUtils.post(baseUrl + '/saveRole', Object.assign(data));

/**
 * 根据id禁用角色信息
 * @param id
 * @constructor
 */
export const ProhibitRoleById = id => requestUtils.put(baseUrl + '/prohibitRoleById/' + id);

/**
 * 根据id删除角色信息
 * @param id
 * @constructor
 */
export const DeleteRoleById = id => requestUtils.del(baseUrl + '/deleteRoleById/' + id);

/**
 * 加载下拉列表角色信息
 * @constructor
 */
export const LoadSelectRoleList = () => requestUtils.get(baseUrl + '/findSelectRole');
/**
 * 查询 userCode 的个数
 * @param userCode
 * @constructor
 */
export const CheckRoleCode = roleCode => requestUtils.get(baseUrl + '/checkRoleCode/' + roleCode);
