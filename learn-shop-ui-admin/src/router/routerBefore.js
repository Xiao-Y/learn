import router from './index'
import store from '../store'
import {Message} from 'element-ui'
import {getAccessToken} from '../utils/cookieUtils' // 验权
import LayoutBase from "../components/LayoutBase";
import {LoadRouterList} from "../api/login";
import {asyncRouterMap} from "./asyncRouter";

const whiteList = ['/login', '/authRedirect'] // 不重定向白名单

router.beforeEach((to, from, next) => {
  if (getAccessToken()) { // 判断是否有token
    if (to.path === '/login') {
      next();
    } else {
      if (!store.getters.menus) {
        Message.error('该用户没有配置权限');
        next({path: '/login'})
      } else if (store.getters.menus.length === 0) {
        // 获取用户信息
        // store.dispatch('GetInfoActions').then(res => { // 拉取用户信息
        //   var data = res.resData;
        //   console.info("userInf", data);
        // }).catch(() => {
        //   store.dispatch('LogOutActions').then(() => {
        //     Message.error('验证失败,请重新登录')
        //     next({path: '/login1'})
        //   })
        // });

        // 根据roles权限生成可访问的路由表
        store.dispatch('GenMenusActions').then(() => {
          // 动态添加可访问路由表
          loadAccessedRouters();
          // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          // 加载按钮权限
          store.dispatch('GenButtonPerms');
          next({...to, replace: true});
        }).catch((error) => {
          console.info(error);
          store.dispatch('LogOutActions').then(() => {
            // Message.error('获取路由失败,请重新登录');
            next({path: '/login'})
          });
        });
      } else {
        next();
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next('/login');
    }
  }
});

/**
 * 加载有权限的路由
 */
function loadAccessedRouters() {
  return new Promise((resolve, reject) => {
    LoadRouterList().then(res => {
      // 异步过滤有权限的路由
      const accessedRouters = filterAsyncRouter(res.resData);
      router.addRoutes(accessedRouters);
      resolve(res);
    }).catch(error => {
      reject(error);
    });
  });
}

/**
 * 异步过滤有权限的路由
 * @param accessedRouters
 * @param menus
 * @returns {*}
 * @constructor
 */
function filterAsyncRouter(menus) {
  // asyncRouterList 转化为 map 方便取值
  let routerMap = asyncRouterMap();
  // 构建路由
  let accessedRouters = [{
    "path": "/main",
    "name": "main",
    "component": LayoutBase,
    "children": []
  }]
  for (let i = 0; i < menus.length; i++) {
    let menu = menus[i];
    let accessedRouter = genRouter(routerMap, menu);
    if (accessedRouter) {
      accessedRouters[0].children.push(accessedRouter);
    }
  }
  // 最后添加404页面
  accessedRouters[0].children.push({
    path: '*',
    redirect: '/error/404'
  });
  // console.info("accessedRouters:{}",accessedRouters);
  return accessedRouters
}

/**
 * 构建动态路由
 * @param routerMap 所有需要权限的路由
 * @param menu
 * @returns {{path, component: (function(*=): *), meta: {icon, title}, name: ([{validator: *, trigger: string}]|string|*)}}
 */
function genRouter(routerMap, menu) {
  // 通过 code 获取原始路由信息
  let router = routerMap.get(menu.menuCode);
  if (!router) {
    return null;
  }
  // 添加元数据
  let meta = {};
  Object.assign(meta, {
    "title": menu.menuName,
    "icon": menu.icon
  });
  Object.assign(router, {"meta": meta})
  return router;
}


