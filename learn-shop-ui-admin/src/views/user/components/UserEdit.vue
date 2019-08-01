<template>
  <div>
    <div class="ms-doc">
      <h3>用户信息</h3>
      <article>
        <el-form ref="userInfo" :model="userInfo" label-width="100px" size="mini">
          <el-form-item label="用户名称" prop="username">
            <el-input v-model="userInfo.username" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="用户CODE" prop="usercode">
            <el-input v-model="userInfo.usercode" placeholder="请输入内容"></el-input>
          </el-form-item>
		  <el-form-item label="用户密码" prop="password">
		    <el-input v-model="userInfo.password" placeholder="请输入内容"></el-input>
		  </el-form-item>
          <el-form-item label="角色信息">
            <custom-select v-model="userInfo.roleIds" :datasource="selectRole" :value-key="userInfo.usercode"
              placeholder="请选择角色" multiple>
            </custom-select>
          </el-form-item>
          <el-form-item label="用户描述" prop="descritpion">
            <el-input type="textarea" v-model="userInfo.descritpion"></el-input>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="userInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button @click="onReset('userInfo')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>

  </div>
</template>

<script>
  import {
    SaveUser,
    UpdateUser,
    LoadRoleIdsByUserId
  } from "../../../api/user/userMag";
  import CustomSelect from '../../../components/common/CustomSelect.vue';
  export default {
    components: {
      CustomSelect
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        userInfo: {
          username: '',
          usercode: '',
          descritpion: '',
          validInd: true
        },
        selectRole: []
      };
    },
    created() {
      this.selectRole = JSON.parse(this.$route.query.selectRole);
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit') {
        this.userInfo = JSON.parse(this.$route.query.userEdit);
        this.loadUserRole(this.userInfo);
      }
    },
    methods: {
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit') {
          UpdateUser(_this.userInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            //传递一个map，updateuser 是 key，resData 是 value
            this.$bus.emit('userInfo', res.resData);
            _this.$router.back(-1);
          });
        } else { // add
          SaveUser(_this.userInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '保存成功!'
            });
            //传递一个map，adduser 是 key，resData 是 value
            this.$bus.emit('userInfo', res.resData);
            _this.$router.back(-1);
          });
        }
      },
      onReturn() {
        //调用router回退页面
        this.$router.back(-1);
      },
      onReset(userInfo) {
        this.$refs[userInfo].resetFields();
      },
      // 加载指定用户的角色信息
      loadUserRole(userInfo) {
        if (userInfo.roleIds.length < 1) {
          LoadRoleIdsByUserId(userInfo.id).then(res => {
            var roleIds = res.resData.roleIds;
            Object.assign(userInfo, {
              roleIds: roleIds
            });
          });
        }
      }
    }
  };
</script>

<style scoped>
  .ms-doc {
    width: 70%;
    margin: 0 auto;
  }

  .ms-doc h3 {
    padding: 9px 10px 10px;
    margin: 0;
    font-size: 14px;
    line-height: 17px;
    background-color: #f5f5f5;
    border: 1px solid #d8d8d8;
    border-bottom: 0;
    border-radius: 3px 3px 0 0;
  }

  .ms-doc article {
    padding: 10px;
    word-wrap: break-word;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px;
  }

  /*.ms-doc article h1 {*/
  /*font-size: 32px;*/
  /*padding-bottom: 10px;*/
  /*margin-bottom: 15px;*/
  /*border-bottom: 1px solid #ddd;*/
  /*}*/

  /*.ms-doc article h2 {*/
  /*margin: 24px 0 16px;*/
  /*font-weight: 600;*/
  /*line-height: 1.25;*/
  /*padding-bottom: 7px;*/
  /*font-size: 24px;*/
  /*border-bottom: 1px solid #eee;*/
  /*}*/

  /*.ms-doc article p {*/
  /*margin-bottom: 15px;*/
  /*line-height: 1.5;*/
  /*}*/

  .ms-doc article .el-checkbox {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }
</style>
