<template>
  <div>
    <el-menu class="header" :style="{ 'background-color': primaryColor }">
      <div class="logo">{{$t('navbar.title')}}</div>
      <div class="user-info">
        <todo-list class="right-menu-item"></todo-list>
        <lang-select class="right-menu-item"></lang-select>
        <skin-comp class="right-menu-item"></skin-comp>
        <my-info class="right-menu-item"></my-info>
      </div>
    </el-menu>
  </div>
</template>
<script>
  import LangSelect from './langselect/index'
  import SkinComp from './skin/index'
  import MyInfo from './myinfo/index'
  import TodoList from './todo/index'

  export default {
    components: {
      LangSelect,
      SkinComp,
      MyInfo,
      TodoList
    },
    data() {
      return {}
    },
    created() {
      // 加载用户 Header主题
      if (localStorage.getItem('themeValue')) {
        switch (localStorage.getItem('themeValue')) {
          case 'blue':
            /* eslint-disable */
            this.primaryColor = '#409eff';
            break;
          case 'green':
            this.primaryColor = '#009a61';
            break;
          case 'red':
            this.primaryColor = '#f44336';
            break;
          case 'purple':
            this.primaryColor = '#7B7DE5';
            break;
          default:
            this.primaryColor = '#21baa9';
            break;
        }
      } else {
        this.primaryColor = '#21baa9';
      }
    }
  }
</script>
<style lang="less">
  .header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    line-height: 70px;
    color: #fff;
  }

  .header .logo {
    float: left;
    width: 250px;
    text-align: center;
  }

  .user-info {
    float: right;
    padding-right: 50px;
    font-size: 16px;
    color: #fff;
  }

  .user-info .el-dropdown-link {
    position: relative;
    display: inline-block;
    /*padding-left: 50px;*/
    color: #fff;
    cursor: pointer;
    vertical-align: middle;
  }

  .user-info .user-logo {
    position: absolute;
    left: 0;
    top: 15px;
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .el-dropdown-menu__item {
    text-align: left;
  }

  .right-menu-item {
    display: inline-block;
    margin: 0 8px;
  }
</style>
