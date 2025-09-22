<template>
  <div>
    <div class="ms-doc">
      <div class="ms-doc-title">用户信息</div >
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
          <el-form-item label="角色" prop="roleIds">
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
                <div>{{ userInfo.showAddress }}</div>
                <el-cascader v-model="userInfo.casAddress"
                             ref="cascaderAddr"
                             slot="reference"
                             :props="optionProps"
                             :style="{ width: '100%' }">
                </el-cascader>
              </el-popover>
            </el-col>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-col :span="18">
              <el-input type="textarea" v-model="userInfo.description"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd" v-if="!fromUserInfo">
            <el-switch v-model="userInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit">保存</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>

  </div>
</template>

<script>
import {CheckUserCode, LoadUserInfoById, SaveUser, UpdateUser} from "../../../api/user/userMag";
import CustomSelect from '../../../components/common/CustomSelect.vue';
import {LoadSelectRoleList} from "../../../api/sys/roleMag";
import {LoadUserDataDictionary} from "../../../api/sys/DataDictionaryMag";
import {LoadCityDataByParentId} from "../../../api/sys/CityMag";

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
        description: '',
        roleIds: [],
        validInd: true,
        casAddress: [],
      },
      selectRole: [], // 角色下拉列表
      selectSex: [], // 性别下拉列表
      optionProps: {
        value: 'cityId',
        label: 'name',
        children: 'children',
        lazy: true,
        emitPath: true,
        lazyLoad: this.lazyLoadCityData
      },
      fromUserInfo: false, // 是否来自个人信息
      oldUserCode: '',// 旧userCode，用于校验
      rulesForm: {
        username: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        usercode: [{required: true, message: '请输入账号', trigger: 'blur'},
          {validator: this.checkUserCode}],
        phone: [{
          message: '请输入正确的手机号', trigger: 'blur',
          pattern: /^1([38][0-9]|4[579]|5[^4]|6[6]|7[0135678]|9[89])\d{8}$/
        }]
      }
    };
  },
  async activated() {
    // 初始化基础数据
    this.initData();
    // 初始化数据
    await this.loadBaseData();
    // 加载业务数据
    await this.loadBusinessData();
  },
  methods: {
    // 初始化基础数据
    initData() {
      this.optionType = this.$route.query.optionType;

      // 个人信息修改
      if (this.optionType === 'myUserInfo') {
        this.fromUserInfo = true;
      }
    },
    async loadBaseData() {
      try {
        // 并行加载基础数据
        const [roleResult, sexResult] = await Promise.all([
          LoadSelectRoleList().catch(error => {
            this.$message.error('角色列表加载失败');
            return {resData: []};
          }),
          LoadUserDataDictionary('sexType').catch(error => {
            this.$message.error('性别数据加载失败');
            return {resData: []};
          }),
        ]);

        this.selectRole = roleResult.resData;
        this.selectSex = sexResult.resData;
      } catch (error) {
        console.error('基础数据加载过程中出错:', error);
      }
    },
    // 加载业务数据
    async loadBusinessData() {
      // 如果不符合条件直接返回
      if (this.optionType !== 'edit' && this.optionType !== 'myUserInfo') {
        return;
      }
      let userId = this.$route.query.userId;
      // 检查 userId 是否有效
      try {
        // 加载用户信息和角色信息
        LoadUserInfoById(userId).then(res => {
          this.userInfo = res.resData;
          this.oldUserCode = this.userInfo.usercode;
          // 处理地址回显
          if (this.userInfo.casAddress) {
            // 确保 casAddress 是数组格式
            this.userInfo.casAddress = Array.isArray(this.userInfo.casAddress)
              ? this.userInfo.casAddress : [];
          }
        });
      } catch (error) {
        this.$message.error('加载业务数据失败');
        console.error(error);
      }
    },
    validSubmit() {
      const _this = this;
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
      if (this.$refs.userInfo) {
        this.$refs.userInfo.resetFields();
      }
      //调用router回退页面
      this.$router.back(-1);
    },
    // 校验账号是否重复
    checkUserCode(rule, value, callback) {
      if (this.oldUserCode !== '' && this.oldUserCode === value) {
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
    async lazyLoadCityData(node, resolve) {
      const {level} = node;
      let parentId = 100000;
      // 获取父级ID
      if (level > 0) {
        parentId = node.value;
      }
      try {
        // 调用API获取子级城市数据
        const res = await LoadCityDataByParentId(parentId);
        let nodes = res.resData || [];
        // // 正确处理数据结构和叶子节点判断
        nodes = nodes.map(item => {
          return {
            cityId: item.cityId,
            name: item.name,
            children: item.children || [],
            leaf: level >= 2
          };
        });
        resolve(nodes);
      } catch (error) {
        this.$message.error('城市数据加载失败');
        resolve([]);
      }
    },
    // 地址显示方法
    addressShow() {
      // 检查组件引用是否存在
      if (this.$refs['cascaderAddr'] && this.$refs['cascaderAddr'].currentLabels) {
        var addLabels = this.$refs['cascaderAddr'].currentLabels;
        // 确保 addLabels 是数组
        if (Array.isArray(addLabels)) {
          Object.assign(this.userInfo, {showAddress: addLabels.join("/")});
        } else {
          // 如果没有标签，设置为空字符串
          Object.assign(this.userInfo, {showAddress: ''});
        }
      } else {
        // 如果组件引用不存在，设置为空字符串
        Object.assign(this.userInfo, {showAddress: ''});
      }
    }
  }
};
</script>
