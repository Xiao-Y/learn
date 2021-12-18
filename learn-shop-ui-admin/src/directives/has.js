import Vue from 'vue'
import {getButtonPermission} from '../utils/cookieUtils'

/**
 * 权限指令
 */
Vue.directive('has', {
    inserted: function (el, bindings, vnode) {
        // console.info("bindings.value:",bindings.value)
        let buttonPermission = getButtonPermission();
        // console.info("buttonPermission:",buttonPermission)
        if (buttonPermission === null) {
            el.remove();
        } else if (bindings.value !== null && !buttonPermission.includes(bindings.value)) {
          // console.info("remove.value:",bindings.value)
            el.remove();
        }
    }
})
