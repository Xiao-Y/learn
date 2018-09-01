import {
  asyncRouterMap,
  constantRouterMap
} from '@/router'

import {getPermission} from '@/api/permission'
import VueUtils from '@/utils/vueUtils'

import types from '@/store/mutationsType'

// 该用户的所有权限
var menuUrls = [];

/**
 * 递归出所有的url
 * @param menus
 */
function genMenuUrls(menus) {
  for (var j in menus) {
    if (menus[j].children) {
      genMenuUrls(menus[j].children);
    } else {
      menuUrls.push(menus[j].path);
    }
  }
}

function filterAsyncRouter(accessedRouters, menus) {
  //递归出所有的url
  genMenuUrls(menus);
  // 反向移除，用户没有的权限从路由移除，定制新路由
  for (var i = accessedRouters.length - 1; i >= 0; i--) {
    var basePath = accessedRouters[i].path;
    if (accessedRouters[i].children) {
      for (var j = accessedRouters[i].children.length - 1; j >= 0; j--) {
        var url = basePath + '/' + accessedRouters[i].children[j].path;
        if (menuUrls.indexOf(url) == -1) {
          accessedRouters[i].children.splice(j, 1);
        }
      }
    } else {
      if (menuUrls.indexOf(basePath) == -1) {
        accessedRouters.splice(i, 1);
      }
    }
  }
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
    GenRoutesActions({commit, state}) {
      return new Promise((resolve, reject) => {
        getPermission(state.token).then(res => {
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
