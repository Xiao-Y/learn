import {LoadHomeMenus} from '../../api/sys/menuMag'
import types from '../../store/mutationsType'
import {asyncRouterMap} from "../../router/asyncRouter";

const menuPerm = {
  state: {
    menus: [] // 需要显示的菜单
  },
  mutations: {
    [types.SET_MENUS]: (state, menus) => {
      state.menus = menus
    }
  },
  actions: {
    GenMenusActions({commit}) {
      return new Promise((resolve, reject) => {
        LoadHomeMenus().then(res => {
          let menus = res.resData.menus;
          // asyncRouterList 转化为 map 方便取值
          let routerMap = asyncRouterMap();
          // 从路由中获取 path 填充到菜单对象中
          setMenuPath(routerMap, menus);
          commit(types.SET_MENUS, menus);
          resolve(res);
        }).catch(error => {
          reject(error);
        });
      })
    }
  }
}

/**
 * 从路由中获取 path 填充到菜单对象中
 * @param routerMap
 * @param menus
 */
function setMenuPath(routerMap, menus) {
  menus.forEach((menu) => {
    let path = "";
    let router = routerMap.get(menu.titleCode);
    if (router) {
      path = router.path;
    }
    Object.assign(menu, {"path": path});
    if (menu.children) {
      setMenuPath(routerMap, menu.children);
    }
  });
}

export default menuPerm
