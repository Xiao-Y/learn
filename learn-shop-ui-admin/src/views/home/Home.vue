<template>
  <div class="wrapper">
    <v-head></v-head>
    <v-sidebar :routes="routes"></v-sidebar>
    <div class="head-top">
      <head-top></head-top>
    </div>

    <div class="content">
      <transition name="move" mode="out-in">
        <keep-alive>
          <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
      </transition>
      <transition name="move" mode="out-in">
        <router-view v-if="!$route.meta.keepAlive"></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
  import vHead from '../../components/header/Header.vue'
  import vSidebar from '../../components/navmenu/Sidebar.vue'
  import store from '../../store'
  import HeadTop from '../../components/header/HeadTop.vue';

  export default {
    components: {
      vHead, vSidebar, HeadTop
    },
    computed: {
      routes() {
        return store.getters.routers
        // return this.$router.options.routes
      }
    }
  }
</script>
<style scoped>
  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 3px; /*滚动条宽度*/
    height: 3px; /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px; /*滚动条的背景区域的圆角*/
    background-color: white; /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px; /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
    background-color: #2E363F; /*滚动条的背景颜色*/
  }
</style>
