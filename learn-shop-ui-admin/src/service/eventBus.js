import Vue from 'vue'

/**
 * 定义空的vue实例，作为 eventbus实现非父子组件之间的通信(vue2.x中去掉了broadcast)
 */
var eventBus = new Vue({});
export default eventBus;
