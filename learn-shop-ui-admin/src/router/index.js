import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/home/Home.vue'

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
  path: '/error',
  name: 'error',
  component: Home,
  children: [{
    name: '404',
    path: '404',
    component: resolve => require(['../views/error/404.vue'], resolve)
  }]
}];

// 异步挂载的路由（动态需要根据权限加载的路由表,
// 子路由的 name = parentPath + childrenPath。必须要唯一，要与 sys_menu 中的 menu_code 一致，不然路由加载不出来）

export const asyncRouterMap = [{
  path: '/readme',
  index: 'readme',
  name: 'readme',
  component: Home,
  children: [{
    path: 'index',
    name: 'readmeIndex',
    meta: {title: 'Readme', icon: 'el-icon-menu'},
    component: resolve => require(['../views/test/readme.vue'], resolve)
  }]
},{
  path: '/sys',
  name:'sys',
  component: Home,
  children: [{
    name: 'sysWhiteListIndex',
    path: 'whiteList/index',
    component: resolve => require(['../views/sys/WhiteList.vue'], resolve)
  }, {
    name: 'sysMenuListIndex',
    path: 'menuList/index',
    component: resolve => require(['../views/sys/MenuList.vue'], resolve)
  }, {
    name: 'sysRoleListIndex',
    path: 'roleList/index',
    meta:{
      keepAlive: true
    },
    component: resolve => require(['../views/sys/RoleList.vue'], resolve)
  }, {
    name: 'sysRoleEdit',
    path: 'roleList/roleEdit',
    component: resolve => require(['../views/sys/components/RoleEdit.vue'], resolve)
  }, {
    name: 'sysPermissionListIndex',
    path: 'permissionList/index',
    meta:{
      keepAlive: true
    },
    component: resolve => require(['../views/sys/PermissionList.vue'], resolve)
  }, {
    name: 'sysPermissionEdit',
    path: 'permissionList/permissionEdit',
    component: resolve => require(['../views/sys/components/PermissionEdit.vue'], resolve)
  }]
}, {
  path: '/job',
  name: 'job',
  component: Home,
  children: [{
    name: 'jobAutoTaskListIndex',
    path: 'autoTaskList/index',
    component: resolve => require(['../views/job/AutoTaskList.vue'], resolve)
  }]
}, {
  path: '/pro',
  name: 'pro',
  component: Home,
  children: [{
    name: 'proProductListIndex',
    path: 'productList/index',
    component: resolve => require(['../views/product/ProductList.vue'], resolve)
  }, {
    name: 'proProductEdit',
    path: 'productList/productEdit',
    component: resolve => require(['../views/product/components/ProductEdit.vue'], resolve)
  }, {
    name: 'proProductImageEdit',
    path: 'productList/productImageEdit',
    component: resolve => require(['../views/product/components/ProductImageEdit.vue'], resolve)
  }]
}, {
  path: '/user',
  name: 'user',
  component: Home,
  children: [{
    name: 'userPermissionListIndex',
    path: 'permissionList/index',
    component: resolve => require(['../views/sys/PermissionList.vue'], resolve)
  }]
}]

export default new Router({
  routes: constantRouterMap
})
