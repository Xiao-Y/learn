<template>

  <div class="wrapper">

    <!-- 侧边栏菜单 -->
    <v-sidebar :routes="routes"></v-sidebar>

    <!-- 主体 -->
    <el-container>
      <!-- 头部 -->
      <el-header>
        <v-head></v-head>
        <!-- tab标签页区域 - 用于标签页切换 -->
        <div class="tabs-switch-page" style="padding-left: 250px; width: 100%">
          <el-tag
            v-for="(tab, index) in tabList"
            :key="tab.name"
            :closable="tab.name !== 'home'"
            :effect="activePath === tab.name ? 'dark' : 'plain'"
            size="medium"
            @click="changeMenu(tab)"
            @close="handleClose(tab, index)">
            {{ tab.label }}
          </el-tag>
        </div>

      </el-header>

      <!--  右侧内容显示区  -->
      <div class="content">
        <transition mode="out-in" name="move">
          <keep-alive>
            <router-view></router-view>
          </keep-alive>
        </transition>
      </div>
    </el-container>
  </div>
</template>

<script>
import store from '../store'
import {mapState} from 'vuex';
import {defaultRouterMap} from "../router";
import vSidebar from './navmenu/Sidebar.vue'
import vHead from './header/Header.vue'

export default {
  components: {
    vSidebar, vHead
  },
  //组件被创建
  created() {
  },
  computed: {
    ...mapState({ // 从 state 中的到的计算属性
      activePath: state => state.activePath, // 已选中菜单
      tabList: state => state.tabList,  // tags菜单列表
      catch_components: state => state.catch_components,  // keepalive缓存
    }),
    routes() {
      return defaultRouterMap
    }
  },
  data() {
    return {
    }
  },
  methods: {
    // 右上角下拉菜单点击事件
    handleCommand(command) {
      switch (command) {
        // 退出
        case 'logout':
          //消息提示
          this.msg.success('退出登录')
          //重置vuex中的数据
          this.$store.commit('clearVUEX')
          //跳转到首页
          this.$router.push("/index");
          break
        //修改密码
        case 'update-password':
          //消息提示
          this.msg.success('修改密码')
          break
      }
    },
    // 点击菜单 - 传入name，添加到keepalive缓存页面
    selectMenu(item) {
      // 加入keepalive缓存
      this.$store.commit('addKeepAliveCache', item.name)
      //添加tags标签
      var submenu = {
        path: item.path,
        name: item.menuCode,
        label: item.menuName
      }
      //修改选中菜单
      this.$store.commit('selectMenu', submenu)
    },
    // 关闭tab标签
    handleClose(tab, index) {
      // 历史选中菜单
      var oldActivePath = this.$store.state.activePath
      // 历史已选中菜单列表
      var oldTabList = this.$store.state.tabList
      // 计算标签个数
      let length = oldTabList.length - 1
      // 删除tabList中的该对象
      for (let i = 0; i < oldTabList.length; i++) {
        let item = oldTabList[i]
        if (item.name === tab.name) {
          oldTabList.splice(i, 1);
        }
      }
      // 删除keepAlive缓存
      this.$store.commit('removeKeepAliveCache', tab.name)
      // 如果关闭的标签不是当前路由的话，就不跳转
      if (tab.name !== oldActivePath) {
        return
      }
      // 如果length为1，必然只剩下首页标签，此时关闭后，更新到首页
      if (length === 1) {
        // 同时存储菜单
        this.$store.commit('closeTab', {activePath: 'home', tabList: oldTabList})
        // tab页向左跳转
        this.$router.push({name: oldTabList[index - 1].name})
        // 不再向下执行
        return
      }
      // 关闭的标签是最右边的话，往左边跳转一个
      if (index === length) {
        // 同时更新路径
        oldActivePath = oldTabList[index - 1].name
        // 同时存储菜单
        this.$store.commit('closeTab', {activePath: oldActivePath, tabList: oldTabList})
        // tab页向左跳转
        this.$router.push({name: oldTabList[index - 1].name})
      } else {
        // 同时更新路径
        oldActivePath = oldTabList[index].name
        // 同时存储菜单
        this.$store.commit('closeTab', {activePath: oldActivePath, tabList: oldTabList})
        // tab页向右跳转
        this.$router.push({name: oldTabList[index].name})
      }
    },
    // 点击标签跳转路由
    changeMenu(item) {
      // 历史选中菜单
      var oldActivePath = this.$store.state.activePath
      // 首先判断点击的是否是自己，如果是自己则return
      if (oldActivePath === item.name) {
        return
      }
      // 不是自己，存储菜单
      this.$store.commit('changeMenu', item.name)
      // 页面跳转
      this.$router.push({name: item.name})
    },
  },
};
</script>
<style lang="less" scoped>


.el-header {
  color: rgb(0, 0, 0);
  font-size: 20px;
  border-bottom: 1px solid #dddddd;
  height: 103px !important;
  padding: 0;
  background: #fff;
}

// tab页
.tabs-switch-page {
  display: flex;
  align-items: center;
  height: 40px;
  background-color: #fff;
  overflow: hidden;
}

.el-tag {
  cursor: pointer;
  margin-left: 10px;
  border-radius: 2px;
  font-size: 12px;
  color: #1890FF;
  border-color: #1890FF;
}

.el-tag--dark {
  color: #fff;
  background-color: #1890FF;
}

.el-dropdown-link {
  cursor: pointer;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.submit-row {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
}
</style>
