import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'vant/lib/icon/local.css';

Vue.use(Vuex, VueAxios, axios);

import {
    Button, Dialog, Icon, Tabbar, TabbarItem, Panel, Grid, GridItem, Collapse, CollapseItem, Divider, Image,
    AddressList, AddressEdit, Area
} from 'vant';

Vue.use(Button)
    .use(Dialog)
    .use(Icon)
    .use(Panel)
    .use(Divider)
    .use(Image)
    .use(AddressList)
    .use(AddressEdit)
    .use(Area)
    .use(Tabbar).use(TabbarItem)
    .use(Collapse).use(CollapseItem)
    .use(Grid).use(GridItem);

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');

