import requestUtils from '../utils/requestUtils'


const baseUrl = '../public-auth';

const baseUrlApp = '../admin-system/loginApp';

/**
 * 登陆
 * @param username
 * @param password
 * @returns {Promise | Promise<unknown>}
 */
export const login = (username, password) => requestUtils.post('../userApi/login', {
  username: username,
  password: password
});

/**
 * 登出
 * @returns {Promise | Promise<unknown>}
 */
export const logout = () => requestUtils.post(baseUrl + '/logout');

/**
 * 数据恢复
 * @returns {Promise | Promise<unknown>}
 * @constructor
 */
export const DataRecovery = () => requestUtils.get('../dataRecovery/initData');

// /**
//  * 通过token 获取菜单信息
//  * @constructor
//  */
// export const LoadHomeMenus = () => requestUtils.get(baseUrl + '/homeMenus');

/**
 * 通过token 获取菜单信息
 * @constructor
 */
// export const LoadHomeMenus = () => requestUtils.get(baseUrlApp + '/homeMenus');
export const LoadHomeMenus = () => requestUtils.get(baseUrlApp + '/findHomeMenu');

/**
 * 通过token 获取角色的路由信息
 * @constructor
 */
export const LoadRouterList = () => requestUtils.get(baseUrlApp + '/findRouterList');
