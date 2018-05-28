import {
  asyncRouterMap,
  constantRouterMap
} from '@/router'

// /**
//  * 通过meta.role判断是否与当前用户权限匹配
//  * @param roles
//  * @param route
//  */
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     return roles.some(role => route.meta.roles.indexOf(role) >= 0)
//   } else {
//     return true
//   }
// }

// /**
//  * 递归过滤异步路由表，返回符合用户角色权限的路由表
//  * @param asyncRouterMap
//  * @param roles
//  */
// function filterAsyncRouter(asyncRouterMap, roles) {
//   const accessedRouters = asyncRouterMap.filter(route => {
//     if (hasPermission(roles, route)) {
//       if (route.children && route.children.length) {
//         route.children = filterAsyncRouter(route.children, roles)
//       }
//       return true
//     }
//     return false
//   })
//   return accessedRouters
// }

function hasPermission(menus, url) {
  console.info('url', url)
  console.info('menus', menus)
  for (var j in menus) {
    console.info('menus[j]', menus[j])
    console.info('menus[j].children', menus[j].children)
    if (menus[j].children) {
      console.info('hasPermission')
      hasPermission(menus[j].children, url)
    } else {
      var children = menus[j]
      console.info('children.path', children.path)
      if (url === children.path) {
        return true
      }
      return false
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
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
      console.log('state.routers', state.routers)
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    }
  },
  actions: {
    GenerateRoutes({
      commit
    }, data) {
      return new Promise(resolve => {
        const roles = data.roles
        const menus = data.menus
        console.info('menus----->', menus)
        // let accessedRouters
        // if (roles.indexOf('admin') >= 0) {
        //   console.log('admin>=0')
        //   accessedRouters = asyncRouterMap(menus)
        // } else {
        //   console.log('admin<0')
        //   accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        //   // accessedRouters = ''
        //   // accessedRouters = asyncRouterMap
        // }
        let accessedRouters = filterAsyncRouter(asyncRouterMap, menus)
        // 最后添加404页面
        accessedRouters.push({
          path: '*',
          redirect: '/error/404'
        });
        console.log('accessedRouters', accessedRouters)
        commit('SET_ROUTERS', accessedRouters)
        commit('SET_MENUS', menus)
        resolve()
      })
    }
  }
}

export default permission
