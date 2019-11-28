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
    path: '/category',
    name: 'category',
    component: () => import('../views/Category.vue')
}, {
    path: '/cart',
    name: 'cart',
    component: () => import('../views/Cart.vue')
}, {
    path: '/friends',
    name: 'friends',
    component: () => import('../views/Friends.vue')
}, {
    path: '/profile',
    name: 'profile',
    component: () => import('../views/Profile.vue')
}, {
    path: '/addressList',
    name: 'addressList',
    component: () => import('../views/AddressList.vue')
}, {
    path: '/addressEdit',
    name: 'addressEdit',
    component: () => import('../views/AddressEdit.vue')
}, {
    path: '/goods',
    name: 'goods',
    component: () => import('../views/Goods.vue')
}]

const router = new VueRouter({
    routes
})

export default router
