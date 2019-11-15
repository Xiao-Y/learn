import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    redirect: '/home',
    component: Home
}, {
    path: '/home',
    name: 'home',
    component: Home
}, {
    path: '/search',
    name: 'search',
    component: () => import('../views/Search.vue')
}, {
    path: '/setting',
    name: 'setting',
    component: () => import('../views/Setting.vue')
}, {
    path: '/friends',
    name: 'friends',
    component: () => import('../views/Friends.vue')
}, {
    path: '/my',
    name: 'my',
    component: () => import('../views/My.vue')
}, {
    path: '/addressList',
    name: 'addressList',
    component: () => import('../views/AddressList.vue')
}, {
    path: '/addressEdit',
    name: 'addressEdit',
    component: () => import('../views/AddressEdit.vue')
}]

const router = new VueRouter({
    routes
})

export default router
