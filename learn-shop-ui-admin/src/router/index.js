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
  name: 'Readme',
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
      component: resolve => require(['../components/page/readme.vue'], resolve)
    }]
}, {
  path: '/error',
  name: 'error',
  component: Home,
  children: [{
    name: '404',
    path: '404',
    component: resolve => require(['../components/error/404.vue'], resolve)
  }]
}]

// 异步挂载的路由（动态需要根据权限加载的路由表）
export const asyncRouterMap = [{
  path: '/sys',
  component: Home,
  children: [{
    name: 'sysinfo',
    path: 'sysinfo',
    component: resolve => require(['../components/page/permission.vue'], resolve)
  }, {
    name: 'role',
    path: 'role',
    component: resolve => require(['../components/page/permission2.vue'], resolve)
  }, {
    name: 'auth',
    path: 'auth',
    component: resolve => require(['../components/page/permission3.vue'], resolve)
  }, {
    name: 'args',
    path: 'args',
    component: resolve => require(['../components/page/permission4.vue'], resolve)
  }, {
    name: 'dictionary',
    path: 'dictionary',
    component: resolve => require(['../components/page/permission.vue'], resolve)
  }]
}]

export default new Router({
  routes: constantRouterMap
})
