<template>
  <div>-
    <template v-for="menu in navMenus" v-if="menu.menuName && menu.display === true">

      <template v-if="menu.children">
        <el-submenu :key="menu.id" :index="menu.id">
          <template slot="title">
            <i class="iconfont" :class="['icon-' + menu.icon]"></i>{{ generateTitle(menu.menuName) }}
          </template>
          <el-menu-item :key="menu.id" :index="menu.path" v-if="menu.path && menu.display === true">
            <i class="iconfont" :class="['icon-' + menu.icon]"></i>{{ generateTitle(menu.menuName) }}
          </el-menu-item>
          <nav-menu :navMenus="menu.children"></nav-menu>
        </el-submenu>
      </template>

      <template v-else-if="menu.display === true">
        <el-menu-item :key="menu.id" :index="menu.path">
          <i class="iconfont" :class="['icon-' + menu.icon]"></i>{{ generateTitle(menu.menuName) }}
        </el-menu-item>
      </template>

    </template>
  </div>
</template>

<script>
  import {generateTitle} from "@/utils/i18n";

  export default {
    name: "NavMenu",
    props: ["navMenus"],
    data() {
      return {};
    },
    created: function () {
//    console.info("navMenus", this.navMenus);
    },
    methods: {
      generateTitle
    }
  };
</script>
