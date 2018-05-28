<template>
  <div>
    <template v-for="menu in navMenus" v-if="menu.title">

      <template v-if="menu.children">
        <el-submenu :index="menu.id">
          <template slot="title"><i :class="menu.icon"></i>{{ menu.title }}</template>
          <nav-menu :navMenus="menu.children"></nav-menu>
        </el-submenu>
      </template>

      <template v-else>
        <el-menu-item :key="menu.id" :index="menu.id" :route="menu.path">
          <i :class="menu.icon"></i>{{ menu.title }}
        </el-menu-item>
      </template>

    </template>
  </div>
</template>

<script>
  export default {
    name: 'NavMenu',
    props: ['navMenus'],
    data() {
      return {}
    },
    created: function () {
      console.info("navMenus", this.navMenus);
    },
    methods: {}
  }
</script>
