<template>

  <div class="login-wrap">
    <div class="login-content-wrap">
      <!--    <div class="head-log">
            <img class="company-log" src="../../static/images/common/company-log.png" alt="公司log"/>
          </div> -->
      <div class="ms-title">后台管理系统</div>
      <div class="ms-login">
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0px" class="demo-ruleForm">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" placeholder="密码" v-model="loginForm.password"
                      @keyup.enter.native="handleLogin"></el-input>
          </el-form-item>
          <div class="bm-btn clearfix">
            <el-button class="login-btn fl" @click.native.prevent="handleLogin">登录</el-button>
            <el-button class="rec-btn fr" @click.native.prevent="dataRecovery" :disabled="true">
              恢复数据
            </el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>

</template>

<script>

  import {
    DataRecovery
  } from "../../api/login";

  import {
    removeToken
  } from '../../utils/cookieUtils';

  export default {
    data() {
      const validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'))
        } else {
          callback()
        }
      }
      const validatePass = (rule, value, callback) => {
        if (value.length < 5) {
          callback(new Error('密码不能小于5位'))
        } else {
          callback()
        }
      }
      return {
        checked: true,
        loginForm: {
          username: 'admin',
          password: 'admin'
        },
        rules: {
          username: [{
            required: true,
            trigger: 'blur',
            validator: validateUsername
          }],
          password: [{
            required: true,
            trigger: 'blur',
            validator: validatePass
          }]
        },
        loading: true
      }
    },
    methods: {
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            // 清理旧 token
            removeToken();
            this.$store.dispatch('LoginActions', this.loginForm).then(() => {
              this.loading = false
//               alert('登录成功')
              localStorage.setItem('ms_username', this.loginForm.username)
              this.$router.push({name: 'homeIndex'})
            }).catch(() => {
              this.loading = false
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      // 数据恢复
      dataRecovery() {
        DataRecovery().then(res => {
          this.$message.success(res.resMsg);
        });
      }
    }
  }

</script>
<style>

  .fl {
    float: left;
  }

  .fr {
    float: right;
  }

  .clearfix:after {
    content: "";
    display: block;
    clear: both;
  }

  .head-log {
    height: 200px;
  }

  .company-log {
    width: 300px;
  }

  .login-wrap:before {
    background: url(../../static/img/login-bg.png) no-repeat;
    background-size: 100% 100%;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: -1;
    content: "";

  }

  .login-content-wrap {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: rgba(38, 50, 56, .2);
  }

  .ms-title {
    position: absolute;
    top: 50%;
    width: 100%;
    margin-top: -230px;
    text-align: center;
    font-size: 30px;
    color: #fff;
  }

  .ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 300px;
    height: 140px;
    margin: -150px 0 0 -190px;
    padding: 40px;
    border-radius: 5px;
    background: #fff;
  }

  .bm-btn {
    text-align: center;
  }

  .bm-btn button {
    width: 40%;
    height: 36px;
  }

  .bm-btn .login-btn {
    color: #FFFFFF;
    background-color: #17b3a3;
  }

  .bm-btn .login-btn:hover {
    color: #FFFFFF;
    background-color: #00d1b2;
  }

  .bm-btn .rec-btn {
    color: #ffffff;
    background-color: #ff0000;
  }

  .bm-btn .rec-btn:hover {
    color: #ffffff;
    background-color: #ff4444;
  }

</style>
