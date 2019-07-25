import requestUtils from '../../utils/requestUtils'


const baseUrl = 'admin-system/roleApi';

/**
 * 根据条件查询商品信息
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
 * 保存商品信息
 * @param data
 * @constructor
 */
export const SaveRole = data => requestUtils.post(baseUrl + '/saveRole', Object.assign(data));

/**
 * 根据id删除商品信息
 * @param id
 * @constructor
 */
export const DeleteRoleById = id => requestUtils.del('core-product/productApi/deleteProductById/' + id);
