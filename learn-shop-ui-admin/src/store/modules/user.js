import {GetUserInfo} from '../../api/user/userMag'
// import {getToken, setToken, removeToken} from '@/utils/auth'
import types from '@/store/mutationsType'

const user = {
  state: {},

  mutations: {},

  actions: {
    // 获取用户信息
    GetInfoActions({commit, state}) {
      return new Promise((resolve, reject) => {
        GetUserInfo(state.token).then(res => {
          resolve(res);
        }).catch(error => {
          reject(error);
        });
      });
    }
  }
}

export default user
