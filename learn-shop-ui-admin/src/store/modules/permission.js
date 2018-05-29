import {
  asyncRouterMap,
  constantRouterMap
} from '@/router'

import types from '@/utils/mutationsType'

function hasPermission(menus, url) {
  // console.info('url', url)
  // console.info('menus', menus)
  for (var j in menus) {
    // console.info('menus[j]', menus[j])
    // console.info('menus[j].children', menus[j].children)
    if (menus[j].children) {
      // console.info('hasPermission')
      hasPermission(menus[j].children, url)
    } else {
      // console.info('children.path', children.path)
      return url === menus[j].path
    }
  }
}

function filterAsyncRouter(asyncRouterMap, menus) {
  const accessedRouters = asyncRouterMap.filter(route => {
    var basePath = route.path
    for (var i in route.children) {
      var url = basePath + '/' + route.children[i].path
      return hasPermission(menus, url)
    }
  })
  return accessedRouters
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: [],
    menus:[]
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
    GenRoutesActions({ commit }, { menus }) {
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
        console.log('accessedRouters', accessedRouters)
        commit(types.SET_ROUTERS, accessedRouters)
        commit(types.SET_MENUS, menus)
        resolve()
      })
    }
  }
}

export default permission
