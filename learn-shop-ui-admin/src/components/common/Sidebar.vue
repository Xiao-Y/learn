<template>
  <div class="sidebar">
    <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" unique-opened>
      <template v-for="item in menusDisplay" v-if="!item.hidden">
        <template v-if="item.children">
          <el-submenu :index="item.id">
            <template slot="title"><i :class="item.icon"></i>{{ item.parentTitle }}</template>
            <el-menu-item v-for="(subItem,i) in item.children" :key="i" :index="subItem.index">{{ subItem.title }}
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <router-link :to="item.path">
            <el-menu-item :index="item.id">
              <i :class="item.icon"></i>{{ generateTitle(item.title) }}
            </el-menu-item>
          </router-link>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
  import {generateTitle} from '@/utils/i18n'
  import store from '@/store'

  export default {
    name: 'vSidebar',
    props: {
      routes: {
        type: Array
      },
      isNest: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        menusDisplay: []
      }
    },
    computed: {
      onRoutes() {
        return this.$route.path.replace('/permission', '')
      }
    },
    created: function () {
      this.menusDisplay = store.getters.menus;
      console.info("this.menusDisplay", this.menusDisplay);
    },
    methods: {
      generateTitle
    }
  }
</script>

<style scoped>
  .sidebar {
    display: block;
    position: absolute;
    width: 250px;
    left: 0;
    top: 70px;
    bottom: 0;
    background: #2E363F;
  }

  .sidebar > ul {
    height: 100%;
  }
</style>
