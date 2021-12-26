import Vue from 'vue'
import Vuex from 'vuex'

// import app from './modules/app'
import user from './modules/user'
import loginHandle from './modules/login'
import menuPerm from './modules/menuPerm'
import buttonPerm from './modules/buttonPerm'

import getters from './getters'

import createLogger from 'vuex/dist/logger'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

const store = new Vuex.Store({
  modules: {
    // app,
    user,
    menuPerm,
    buttonPerm,
    loginHandle
  },
  getters,
  strict: debug,
  plugins: debug ? [createLogger()] : []
})

export default store
