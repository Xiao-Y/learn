import {
  asyncRouterMap,
  constantRouterMap
} from '@/router'

import types from '@/utils/mutationsType'

// 该用户的所有权限
var menuUrls = [];

/**
 * 递归出所有的url
 * @param menus
 */
function genMenuUrls(menus) {
  for (var j in menus) {
    if (menus[j].children) {
      genMenuUrls(menus[j].children)
    } else {
      menuUrls.push(menus[j].path);
    }
  }
}

function filterAsyncRouter(accessedRouters, menus) {
  //递归出所有的url
  genMenuUrls(menus);
  // console.info('menuUrls', menuUrls)
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
    routers: constantRouterMap,
    addRouters: [],
    menus: []
  },
  mutations: {
    [types.SET_ROUTERS]: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
      // console.log('state.routers', state.routers)
    },
    [types.SET_MENUS]: (state, menus) => {
      state.menus = menus
    }
  },
  actions: {
    GenRoutesActions({commit}, {menus}) {
      return new Promise(resolve => {
        // const roles = data.roles
        // const menus = data.menus
        // console.info("GenRoutesActions.menus", menus)
        const accessedRouters = filterAsyncRouter(asyncRouterMap, menus)
        // 最后添加404页面
        accessedRouters.push({
          path: '*',
          redirect: '/error/404'
        })
        // console.log('accessedRouters', accessedRouters)
        commit(types.SET_ROUTERS, accessedRouters)
        commit(types.SET_MENUS, menus)
        resolve()
      })
    }
  }
}

export default permission
