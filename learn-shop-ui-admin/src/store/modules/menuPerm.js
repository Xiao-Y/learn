import {LoadHomeMenus} from '../../api/sys/menuMag'
import types from '../../store/mutationsType'

const menuPerm = {
  state: {
    menus: [] // 需要显示的菜单
  },
  mutations: {
    [types.SET_MENUS]: (state, menus) => {
      state.menus = menus
    }
  },
  actions: {
    GenMenusActions({commit}) {
      return new Promise((resolve, reject) => {
        LoadHomeMenus().then(res => {
          let menus = res.resData.menus;
          commit(types.SET_MENUS, menus);
          resolve(res);
        }).catch(error => {
          reject(error);
        });
      })
    }
  }
}

export default menuPerm
