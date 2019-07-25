import requestUtils from '../../utils/requestUtils'

const baseUrl = 'admin-system/menuApi';

/**
 * 获取父菜单信息
 * @param pid
 * @constructor
 */
export const findParentMenu = pid => requestUtils.get(baseUrl + '/findMenuById/' + pid);

/**
 * 获取父菜单信息
 * @param pid
 * @constructor
 */
export const findMenus = () => requestUtils.get(baseUrl + '/findMenus');

/**
 * 保存或者更新菜单信息
 * @param id
 * @param pid
 * @param title
 * @param titleCode
 * @param path
 * @param icon
 * @param validInd
 * @param display
 * @constructor
 */
export const saveOrUpdateMenu = (id, pid, title, titleCode, sortField, icon, validInd, display) => requestUtils.put(baseUrl + '/saveOrUpdateMenu', {
  id: id,
  pid: pid,
  menuName: title,
  menuCode: titleCode,
  sortField: sortField,
  icon: icon,
  validInd: validInd,
  display: display
});

/**
 * 删除菜单及关联信息
 * @param ids
 * @constructor
 */
export const delMenuByIds = ids => requestUtils.del(baseUrl + '/delMenuByIds', {ids});

/**
 * 通过token 获取菜单信息
 * @param token
 * @constructor
 */
export const getHomeMenus = token => requestUtils.get(baseUrl + '/homeMenus?token=' + token);

/**
 * 查询 menuCode 是否重复
 * @param menuCode
 * @constructor
 */
export const CheckMenuCode = menuCode => requestUtils.get(baseUrl + '/checkMenuCode/' + menuCode);
