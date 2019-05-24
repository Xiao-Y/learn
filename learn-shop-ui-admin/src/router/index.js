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
  children: [{
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
}];

// 异步挂载的路由（动态需要根据权限加载的路由表）
export const asyncRouterMap = [{
  path: '/sys',
  component: Home,
  children: [{
    name: 'whiteListIndex',
    path: 'whiteList/index',
    component: resolve => require(['../views/sys/WhiteList.vue'], resolve)
  }, {
    name: 'menuListIndex',
    path: 'menuList/index',
    component: resolve => require(['../views/sys/MenuList.vue'], resolve)
  }]
}, {
  path: '/job',
  component: Home,
  children: [{
    name: 'autoTaskListIndex',
    path: 'autoTaskList/index',
    component: resolve => require(['../views/job/AutoTaskList.vue'], resolve)
  }]
}, {
  path: '/product',
  component: Home,
  children: [{
    path: 'productList/index',
    name: 'productListIndex',
    component: resolve => require(['../views/product/ProductList.vue'], resolve)
  }, {
    path: 'productList/productEdit',
    name: 'productEdit',
    component: resolve => require(['../views/product/components/ProductEdit.vue'], resolve)
  }, {
    path: 'productList/productImageEdit',
    name: 'productImageEdit',
    component: resolve => require(['../views/product/components/ProductImageEdit.vue'], resolve)
  }]
}]

export default new Router({
  routes: constantRouterMap
})
