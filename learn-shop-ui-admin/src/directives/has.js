import Vue from 'vue'
import store from "../store";

/**
 * 权限指令
 */
Vue.directive('has', {
    inserted: function (el, bindings, vnode) {
        // console.info("bindings.value:",bindings.value)
      // 获取按钮权限信息
      let buttonPermission = store.getters.buttonPerm;
      // console.info("buttonPermission:",buttonPermission)
        if (buttonPermission === null) {
            el.remove();
        } else if (bindings.value !== null && !buttonPermission.includes(bindings.value)) {
          // console.info("remove.value:",bindings.value)
            el.remove();
        }
    }
})
