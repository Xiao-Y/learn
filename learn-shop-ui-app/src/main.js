import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import router from './router'
import store from './store'
// import axios from 'axios'
// import vueAxios from 'vue-axios'
import 'vant/lib/icon/local.css';

import * as filter from '@/filter/CustomFilters'

Vue.use(Vuex);

// 注册全局过滤器
Object.keys(filter).map(key => {
    Vue.filter(key, filter[key])
});

// Vue.use(vueAxios,axios);//Vue.axios/this.axios/this.$http使用axios，一次封装方便协作规范

// Vue.prototype.$axios = axios;   //全局注册，使用方法为:this.$axios

import {
    Button, Dialog, Icon, Tabbar, TabbarItem, Panel, Grid, GridItem, Collapse, CollapseItem, Divider, Image,
    AddressList, AddressEdit, Area, NavBar, Swipe, SwipeItem, Lazyload, Search, Card, Tag, Col, Cell, CellGroup,
    GoodsAction, GoodsActionIcon, GoodsActionButton, Checkbox, SubmitBar, CheckboxGroup, CouponCell, CouponList,
    Popup, Stepper, Sku, TreeSelect, DropdownMenu, DropdownItem, Skeleton,List,ImagePreview,ActionSheet
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
    .use(NavBar)
    .use(Lazyload)
    .use(Search)
    .use(Card)
    .use(Tag)
    .use(Col)
    .use(Checkbox)
    .use(SubmitBar)
    .use(Popup)
    .use(Stepper)
    .use(CheckboxGroup)
    .use(Sku)
    .use(CouponCell).use(CouponList)
    .use(Cell).use(CellGroup)
    .use(GoodsAction).use(GoodsActionIcon).use(GoodsActionButton)
    .use(Swipe).use(SwipeItem)
    .use(Tabbar).use(TabbarItem)
    .use(Collapse).use(CollapseItem)
    .use(DropdownMenu).use(DropdownItem)
    .use(TreeSelect)
    .use(Skeleton)
    .use(List)
    .use(ImagePreview)
    .use(ActionSheet)
    .use(Grid).use(GridItem);

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');

