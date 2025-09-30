<template>
  <div class="sidebar">
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" unique-opened router>
      <nav-menu :navMenus="menusDisplay"></nav-menu>
    </el-menu>
  </div>
</template>

<script>
  import store from "@/store";
  import NavMenu from "./NavMenu";

  export default {
    data() {
      return {
        menusDisplay: store.getters.menus
      };
    },
    components: {
      NavMenu
    },
    methods: {}
  };
</script>

<style scoped>
  .sidebar {
    display: block;
    position: absolute;
    width: 250px;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-x: hidden;
    overflow-y: scroll;
    /*background: #2E363F;*/
  }

  .sidebar > ul {
    height: 100%;
  }
</style>
