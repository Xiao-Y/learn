import Vue from 'vue'
import {getButtonPermission} from '../utils/cookieUtils'

/**
 * 权限指令
 */
Vue.directive('has', {
    inserted: function (el, bindings, vnode) {
        console.info("bindings.value:",bindings.value)
        let buttonPermission = getButtonPermission();
        if (!buttonPermission) {
            el.remove();
        } else if (bindings.value && buttonPermission.indexOf(bindings.value) == -1) {
            el.remove();
        }
    }
})
