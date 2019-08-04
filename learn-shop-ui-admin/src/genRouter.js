import router from './router'
import store from './store'
import {Message} from 'element-ui'
import {getAccessToken} from './utils/cookieUtils' // 验权

import types from '@/store/mutationsType'

const whiteList = ['/login', '/authredirect'] // 不重定向白名单

router.beforeEach((to, from, next) => {
  // console.info("to:",to);
  // console.info("from:",from);
  // console.info("next:",next);
  if (getAccessToken()) { // 判断是否有token
    if (to.path === '/login') {
      store.commit(types.SET_ROUTERS);
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
        //   store.dispatch('FedLogOutActions').then(() => {
        //     Message.error('验证失败,请重新登录')
        //     next({path: '/login1'})
        //   })
        // });

        // 根据roles权限生成可访问的路由表
        store.dispatch('GenRoutesActions').then(() => {
          // 动态添加可访问路由表
          router.addRoutes(store.getters.addRouters);
          // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          // console.info(store.getters.addRouters);
          next({...to, replace: true});
        }).catch((error) => {
          console.info(error);
          store.dispatch('FedLogOutActions').then(() => {
            Message.error('获取路由失败,请重新登录');
            store.commit(types.SET_ROUTERS);
            next({path: '/login'})
          });
        });
      } else {
        // 当有用户权限的时候，说明所有可访问路由已生成 如访问没权限的全面会自动进入404页面
        store.commit(types.SET_ROUTERS);
        next();
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      store.commit(types.SET_ROUTERS);
      next();
    } else {
      store.commit(types.SET_ROUTERS);
      next('/login');
    }
  }
});

