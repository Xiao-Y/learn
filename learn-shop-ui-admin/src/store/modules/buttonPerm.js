import {LoadDataMyPermissionList} from '../../api/sys/permissionMag'

import types from '../../store/mutationsType'

const buttonPerm = {
  state: {
    permissionCodes: []
  },
  mutations: {
    [types.SET_BUTTON_PERM]: (state, permissionCodes) => {
      state.permissionCodes = permissionCodes
    }
  },
  actions: {
    GenButtonPerms({commit}) {
      return new Promise((resolve, reject) => {
        LoadDataMyPermissionList().then(res => {
          commit(types.SET_BUTTON_PERM, res.resData);
          resolve(res);
        }).catch(error => {
          reject(error);
        });
      })
    }
  }
}

export default buttonPerm
