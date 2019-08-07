<template>
  <div>
    <div class="ms-doc">
      <h3>用户信息</h3>
      <article>
        <el-form ref="userInfo" :model="userInfo" :rules="rulesForm" :inline-message="true" label-width="100px"
                 size="mini">
          <el-form-item label="姓名" prop="username">
            <el-col :span="18">
              <el-input v-model="userInfo.username" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="账号" prop="usercode">
            <el-col :span="18">
              <el-input v-model="userInfo.usercode" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="密码" prop="password" v-if="!fromUserInfo">
            <el-col :span="18">
              <el-input type="password" v-model="userInfo.password" placeholder="默认密码与用户名相同"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <custom-select v-model="userInfo.sex" :datasource="selectSex" :value-key="userInfo.usercode"
                           :disabled="fromUserInfo"
                           placeholder="请选择性别">
            </custom-select>
          </el-form-item>
          <el-form-item label="角色">
            <custom-select v-model="userInfo.roleIds" :datasource="selectRole" :value-key="userInfo.usercode"
                           :disabled="fromUserInfo"
                           placeholder="请选择角色" multiple>
            </custom-select>
          </el-form-item>
          <el-form-item label="出生日期" prop="birthDate">
            <el-date-picker type="datetime" v-model="userInfo.birthDate" format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-col :span="18">
              <el-input v-model="userInfo.phone" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="地址" prop="casAddress">
            <el-col :span="18">
              <el-popover trigger="hover" placement="right" @show="addressShow">
                <div >{{userInfo.showAddress}}</div>
                <el-cascader v-model="userInfo.casAddress"
                              ref="cascaderAddr"
                              slot="reference"
                              :options="citySources"
                              :props="optionProps"
                              @active-item-change="handleItemChange"
                              @change="handleChange">
                </el-cascader>
              </el-popover>
            </el-col>
          </el-form-item>
          <el-form-item label="描述" prop="descritpion">
            <el-col :span="18">
              <el-input type="textarea" v-model="userInfo.descritpion"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd" v-if="!fromUserInfo">
            <el-switch v-model="userInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit">保存</el-button>
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
    LoadRoleIdsByUserId,
    CheckUserCode
  } from "../../../api/user/userMag";
  import CustomSelect from '../../../components/common/CustomSelect.vue';
  import {
    LoadSelectRoleList
  } from "../../../api/sys/roleMag";
  import {
    LoadUserDataDictionary
  } from "../../../api/sys/DataDictionaryMag";
  import {
    LoadCityData,
  } from "../../../api/sys/CityMag";

  import {
    getCityData,
    setCityData
  } from '../../../utils/cookieUtils' // 验权

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
          roleIds: [],
          validInd: true
        },
        selectRole: [], // 角色下拉列表
        selectSex: [], // 性别下拉列表
        citySources: [],// 省份下拉列表
        optionProps: {
          value: 'cityId',
          label: 'name',
          children: 'children'
        },
        fromUserInfo: false, // 是否来自个人信息
        oldUserCode: '',// 旧userCode，用于校验
        rulesForm: {
          username: [{required: true, message: '请输入姓名', trigger: 'blur'}],
          usercode: [{required: true, message: '请输入账号', trigger: 'blur'},
            {validator: this.checkUserCode, trigger: 'blur'}],
          phone: [{
            message: '请输入正确的手机号', trigger: 'blur',
            pattern: /^1([38][0-9]|4[579]|5[^4]|6[6]|7[0135678]|9[89])\d{8}$/
          }]
        }
      };
    },
    created() {
      // 组件：在个人信息修改时也是这个页面 optionType === 'myUserInfo'
      var selectRole = this.$route.query.selectRole;
      if (selectRole) {
        this.selectRole = JSON.parse(selectRole);
      } else {
        LoadSelectRoleList().then(res => {
          this.selectRole = res.resData;
        });
      }
      var selectSex = this.$route.query.selectSex;
      if (selectSex) {
        this.selectSex = JSON.parse(selectSex);
      } else {
        LoadUserDataDictionary('sexType').then(res => {
          this.selectSex = res.resData;
        });
      }

      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit' || this.optionType === 'myUserInfo') {
        var editInfo = JSON.parse(this.$route.query.userEdit);
        this.userInfo = editInfo;
        this.loadUserRole(this.userInfo);
        this.oldUserCode = editInfo.usercode;
      }
      // 个人信息修改
      if (this.optionType === 'myUserInfo') {
        this.fromUserInfo = true;
      }
      // 加载城市下拉列表
      this.loadCitySources();
    },
    methods: {
      validSubmit() {
        var _this = this;
        this.$refs['userInfo'].validate(valid => {
          if (valid) {
            _this.onSubmit();
          } else {
            return false;
          }
        });
      },
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit' || this.optionType === 'myUserInfo') {
          UpdateUser(_this.userInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            if (this.optionType === 'edit') {
              //传递一个map，updateuser 是 key，resData 是 value
              this.$bus.emit('userInfo', res.resData);
            }
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
      },
      // 校验账号是否重复
      checkUserCode(rule, value, callback) {
        if (this.oldUserCode != '' && this.oldUserCode === value) {
          callback();
          return true;
        }
        CheckUserCode(value).then(res => {
          if (res.resData >= 1) {
            callback(new Error("账号已经存在"));
          } else {
            callback();
          }
        });
      },
      loadCitySources() {
        LoadCityData().then(res => {
          this.citySources = res;
        });
      },
      // 加载城市下拉列表
      handleChange(value) {
        console.info("value", value);
        console.log(this.$refs['cascaderAddr'].currentLabels);
      },
      handleItemChange(value) {
        console.info("value1", value);
      },
      addressShow(){
        var addLabels = this.$refs['cascaderAddr'].currentLabels;
        Object.assign(this.userInfo,{showAddress:addLabels.join("/")});
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
  .ms-doc article .el-checkbox {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }

  .el-input__inner {
    width: 80%
  }
</style>
