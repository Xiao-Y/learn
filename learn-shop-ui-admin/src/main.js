// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import router from './router'
import store from './store'
import i18n from './lang' // Internationalization
import { global } from '@/global/global'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueUtils from '@/utils/vueUtils'
import './static/icon/iconfont.css'
// 按钮权限
import './directives/has'

import VueBus from 'vue-bus' // 消息总线

import './router/routerBefore' // premission control

Vue.use(Vuex);
Vue.use(ElementUI, {
  size: 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
});
Vue.use(VueBus);

Vue.prototype.VueUtils = VueUtils;


// 加载用户主题
if (localStorage.getItem('themeValue')) {
  global.changeTheme(localStorage.getItem('themeValue'))
} else {
  global.changeTheme('default')
}
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  components: { App },
  template: '<App/>'
})
