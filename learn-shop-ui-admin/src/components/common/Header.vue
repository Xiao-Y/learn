<template>
  <el-menu class="header" :style="{ 'background-color': primaryColor }">
    <div class="logo">{{$t('navbar.title')}}</div>
    <div class="user-info">
      <lang-select class="right-menu-item"></lang-select>
      <skinComp class="right-menu-item"></skinComp>
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          <img class="user-logo" src="../../static/img/img.jpg"> {{username}}
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="changeAvatar">更换头像</el-dropdown-item>
          <el-dropdown-item command="userInfo">个人信息</el-dropdown-item>
          <el-dropdown-item command="editPassword">修改密码</el-dropdown-item>
          <el-dropdown-item command="loginout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-menu>
</template>
<script>
  import LangSelect from '../../components/LangSelect'
  import skinComp from '../../components/skin'
  import {
    getAccessToken
  } from '../../utils/auth' // 验权
  import {
    GetUserInfo
  } from '../../api/user/userMag' // 用户相关
  import {
    LoadSelectRoleList
  } from "../../api/sys/roleMag";


  export default {
    components: {
      LangSelect,
      skinComp
    },
    created() {
      // 加载用户 Header主题
      if (localStorage.getItem('themeValue')) {
        switch (localStorage.getItem('themeValue')) {
          case 'blue':
            /* eslint-disable */
            this.primaryColor = '#409eff'
            break
          case 'green':
            this.primaryColor = '#009a61'
            break
          case 'red':
            this.primaryColor = '#f44336'
            break
          case 'purple':
            this.primaryColor = '#7B7DE5'
            break
          default:
            this.primaryColor = '#21baa9'
            break
        }
      } else {
        this.primaryColor = '#21baa9'
      }
    },
    data() {
      return {
        name: 'billow',
        selectRole: [], // 角色下拉列表
      }
    },
    computed: {
      username() {
        const username = localStorage.getItem('ms_username')
        return username || this.name
      }
    },
    methods: {
      handleCommand(command) {
        switch (command) {
          case 'loginout': // 退出
            localStorage.removeItem('ms_username')
            this.$store.dispatch('LogOutActions').then(() => {
              location.reload() // 为了重新实例化vue-router对象 避免bug
            })
            break;
          case 'userInfo': // 用户信息
            this.getUserInfo();
            break;
          default:
            break;
        }
      },
      // 获取用户信息
      getUserInfo() {
        if (!getAccessToken()) {
          this.$message.error("用户没有登陆，请先登陆");
          return;
        }
        GetUserInfo().then(res => {
          this.$router.push({
            name: 'userUserEdit',
            query: {
              optionType: 'myUserInfo',
              userEdit: JSON.stringify(res.resData)
            }
          });
        });
      }
    }
  }
</script>
<style scoped>
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
    padding-left: 50px;
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
    text-align: center;
  }

  .right-menu-item {
    display: inline-block;
    margin: 0 8px;
  }
</style>
