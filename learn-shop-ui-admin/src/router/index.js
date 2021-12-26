import Vue from 'vue'
import Router from 'vue-router'
import LayoutBase from '../components/LayoutBase.vue'

Vue.use(Router)

/**
 * 默认路由
 * @type {any}
 */
export const defaultRouterMap = [{
  path: '/',
  redirect: '/login',
  hidden: true
}, {
  path: '/login',
  name: '登录页面',
  hidden: true,
  component: resolve => require(['../views/login/Login.vue'], resolve)
}, {
  path: '/home',
  index: 'home',
  name: 'home',
  component: LayoutBase,
  children: [{
    path: 'index',
    name: 'homeIndex',
    component: resolve => require(['../views/home/HomeIndex.vue'], resolve)
  }]
}, {
  path: '/error',
  name: 'error',
  component: LayoutBase,
  children: [{
    name: '404',
    path: '404',
    component: resolve => require(['../views/error/404.vue'], resolve)
  }]
}];

// 暴露默认路由
export default new Router({
  routes: defaultRouterMap
})
