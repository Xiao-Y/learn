import {LoadDataMyPermissionList} from '../../api/sys/permissionMag'

import types from '../../store/mutationsType'
import VueUtils from "../../utils/vueUtils";

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
                    let resData = VueUtils.deepClone3(res.resData)
                    commit(types.SET_BUTTON_PERM, resData);
                    resolve(res);
                }).catch(error => {
                    reject(error);
                });
            })
        }
    }
}

export default buttonPerm
