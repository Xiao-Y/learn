<template>
  <div>
    <template v-for="menu in navMenus" v-if="menu.title">

      <template v-if="menu.children">
        <el-submenu :key="menu.id" :index="menu.id">
          <template slot="title"><i :class="menu.icon"></i>{{ generateTitle(menu.title) }}</template>
          <nav-menu :navMenus="menu.children"></nav-menu>
        </el-submenu>
      </template>

      <template v-else>
        <el-menu-item :key="menu.id" :index="menu.path">
          <i :class="menu.icon"></i>{{ generateTitle(menu.title) }}
        </el-menu-item>
      </template>

    </template>
  </div>
</template>

<script>
import { generateTitle } from "@/utils/i18n";

export default {
  name: "NavMenu",
  props: ["navMenus"],
  data() {
    return {};
  },
  created: function() {
//    console.info("navMenus", this.navMenus);
  },
  methods: {
    generateTitle
  }
};
</script>
