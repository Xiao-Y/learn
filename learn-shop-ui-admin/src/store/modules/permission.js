import {
  asyncRouterMap,
  constantRouterMap
} from '@/router'

import { getHomeMenus } from '../../api/sys/menuMag'
import VueUtils from '@/utils/vueUtils'

import types from '@/store/mutationsType'

// 该用户的所有权限
var menuCodes = [];

/**
 * 递归出所有的Code
 * @param menus
 * @constructor
 */
function genMenuCodes(menus) {
  for (var j in menus) {
    menuCodes.push(menus[j].titleCode);
    if (menus[j].children) {
      genMenuCodes(menus[j].children);
    }
  }
}

/**
 * 递归设置访问的URL
 * @param menus
 * @constructor
 */
function setMenuUrl(menus, router, routerPath) {
  for (var j in menus) {
    // 设置路由的 title 和 icon
    if (menus[j].titleCode === router.name) {
      var meta = router.meta ? router.meta : {};
      Object.assign(meta, { title: menus[j].title, icon: menus[j].icon });
      Object.assign(router, { meta: meta });
    }
    // 设置非顶级菜单的
    if (menus[j].titleCode === router.name && menus[j].pid != null) {
      menus[j].path = routerPath;
      break;
    } else if (menus[j].children) { // 设置有子菜单的
      setMenuUrl(menus[j].children, router, routerPath);
    } else if ((menus[j].children == null || menus[j].children.length < 1) && menus[j].titleCode === router.name) {// 设置没有子菜单的
      menus[j].path = routerPath;
      break;
    }
  }
}

/**
 * 异步过滤路由
 * @param accessedRouters
 * @param menus
 * @returns {*}
 * @constructor
 */
function filterAsyncRouter(accessedRouters, menus) {
  //递归出所有的Code
  genMenuCodes(menus);
  // 反向移除，用户没有的权限从路由移除，定制新路由
  for (var i = accessedRouters.length - 1; i >= 0; i--) {
    // var basePath = accessedRouters[i].path;
    var baseRouterName = accessedRouters[i].name;
    if (accessedRouters[i].children && accessedRouters[i].children.length > 0) {
      for (var j = accessedRouters[i].children.length - 1; j >= 0; j--) {
        // var url = basePath + '/' + accessedRouters[i].children[j].path;
        var routerName = accessedRouters[i].children[j].name;
        if (menuCodes.indexOf(routerName) == -1) {
          accessedRouters[i].children.splice(j, 1);
        } else {
          var url = accessedRouters[i].path + '/' + accessedRouters[i].children[j].path;
          setMenuUrl(menus, accessedRouters[i].children[j], url);
        }
      }
    } else {
      if (menuCodes.indexOf(baseRouterName) == -1) {
        accessedRouters.splice(i, 1);
      } else {
        setMenuUrl(menus, accessedRouters[i], accessedRouters[i].path);
      }
    }
  }
  console.info(accessedRouters);
  return accessedRouters
}

const permission = {
  state: {
    routers: VueUtils.deepClone(constantRouterMap),
    addRouters: [],
    menus: []
  },
  mutations: {
    [types.SET_ROUTERS]: (state, routers) => {
      state.addRouters = routers
      state.routers = VueUtils.deepClone(constantRouterMap.concat(routers))
    },
    [types.SET_MENUS]: (state, menus) => {
      state.menus = menus
    }
  },
  actions: {
    GenRoutesActions({ commit, state }) {
      return new Promise((resolve, reject) => {
        getHomeMenus(state.token).then(res => {
          var menus = VueUtils.deepClone(res.resData.menus);
          const accessedRouters = filterAsyncRouter(asyncRouterMap, menus);
          // 最后添加404页面
          accessedRouters.push({
            path: '*',
            redirect: '/error/404'
          });
          commit(types.SET_ROUTERS, VueUtils.deepClone3(accessedRouters));
          commit(types.SET_MENUS, menus);
          resolve(res);
        }).catch(error => {
          reject(error);
        });
      })
    }
  }
}

export default permission
