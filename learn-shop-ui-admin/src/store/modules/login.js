import {
  login,
  logout
} from '@/api/login'
import {
  removeToken
} from '../../utils/cookieUtils'
import types from '@/store/mutationsType'
import VueUtils from '@/utils/vueUtils'

const loginHandle = {
  state: {
    token: [],
    name: '',
    avatar: '',
    roles: []
  },

  mutations: {
    [types.SET_TOKEN]: (state, token) => {
      state.token = token
    },
    [types.SET_NAME]: (state, name) => {
      state.name = name
    },
    [types.SET_AVATAR]: (state, avatar) => {
      state.avatar = avatar
    },
    [types.SET_ROLES]: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 登录
    LoginActions({
      commit
    }, userInfo) {
      const username = userInfo.username.trim();
      const password = userInfo.password;
      //   const rawPassword = VueUtils.md5(password);
      return new Promise((resolve, reject) => {
        login(username, password).then(res => {
          const data = res.resData;
          commit(types.SET_NAME, username);
          commit(types.SET_AVATAR, username);
          commit(types.SET_TOKEN, data);
          commit(types.SET_ROLES, data.roleCodes);
          resolve();
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOutActions({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        commit(types.SET_TOKEN, '');
        commit(types.SET_ROLES, []);
        removeToken();
        resolve();
        // logout(state.token).then(() => {
        //   commit(types.SET_TOKEN, '');
        //   commit(types.SET_ROLES, []);
        //   removeToken();
        //   resolve();
        // }).catch(error => {
        //   reject(error);
        // })
      })
    },

    // 前端 登出
    FedLogOutActions({
      commit
    }) {
      return new Promise(resolve => {
        commit(types.SET_TOKEN, '');
        removeToken();
        resolve();
      })
    }
  }
}

export default loginHandle
