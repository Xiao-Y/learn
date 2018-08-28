import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/common/Home.vue'

Vue.use(Router)

// 静态路由
export const constantRouterMap = [{
  path: '/',
  redirect: '/login',
  hidden: true
}, {
  path: '/login',
  name: '登录页面',
  hidden: true,
  component: resolve => require(['../views/login/Login.vue'], resolve)
}, {
  path: '/Readme',
  index: 'Readme',
  meta: {
    title: 'Readme',
    icon: 'el-icon-menu'
  },
  component: Home,
  children: [
    {
      name: 'Readme',
      path: '/',
      meta: {title: 'Readme', icon: 'el-icon-menu'},
      component: resolve => require(['../views/test/readme.vue'], resolve)
    }]
}, {
  path: '/error',
  name: 'error',
  component: Home,
  children: [{
    name: '404',
    path: '404',
    component: resolve => require(['../views/error/404.vue'], resolve)
  }]
}]

// 异步挂载的路由（动态需要根据权限加载的路由表）
export const asyncRouterMap = [{
  path: '/sys',
  component: Home,
  children: [{
    name: 'sysinfo',
    path: 'sysinfo',
    component: resolve => require(['../views/test/permission.vue'], resolve)
  }, {
    name: 'role',
    path: 'role',
    component: resolve => require(['../views/test/permission2.vue'], resolve)
  }, {
    name: 'redisMag',
    path: 'redisMag',
    component: resolve => require(['../views/test/permission3.vue'], resolve)
  }, {
    name: 'args',
    path: 'args',
    component: resolve => require(['../views/sys/MenuMag.vue'], resolve)
  }, {
    name: 'whiteListApiWhiteListMag',
    path: 'whiteListApi/whiteListMag',
    component: resolve => require(['../views/sys/WhiteListMag.vue'], resolve)
  }, {
    name: 'menuApiFindMenus',
    path: 'menuApi/findMenus',
    component: resolve => require(['../views/sys/MenuMag.vue'], resolve)
  }]
}, {
  path: '/job',
  component: Home,
  children: [{
    name: 'autoTaskList',
    path: 'autoTaskList',
    component: resolve => require(['../views/job/autoTaskList.vue'], resolve)
  }]
}, {
  path: '/product',
  component: Home,
  children: [{
    name: 'findProductList',
    path: 'findProductList',
    component: resolve => require(['../views/product/productList.vue'], resolve)
  }]
}]

export default new Router({
  routes: constantRouterMap
})
