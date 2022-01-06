<template>
  <div>
    <el-row :gutter="10">
      <!--   菜单树   -->
      <custom-menu-tree ref="menuTree" :checkStrictly="false" :defaultCheckedMenu="defaultCheckedMenu" @changeCheck="changeMenuTree"/>
      <!-- 角色信息 -->
      <el-col :span="14">
        <el-collapse v-model="activeNames">
          <el-collapse-item name="1" title="角色信息">
            <el-form ref="roleInfo" :model="roleInfo" :rules="rulesForm" inline label-width="95px" size="mini">
              <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="roleInfo.roleName"></el-input>
              </el-form-item>
              <el-form-item label="角色CODE" prop="roleCode">
                <el-input v-model="roleInfo.roleCode"></el-input>
              </el-form-item>
              <el-form-item label="角色描述" prop="description">
                <el-input v-model="roleInfo.description"></el-input>
              </el-form-item>
              <el-form-item label="是否有效" prop="validInd">
                <el-switch v-model="roleInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
              </el-form-item>
            </el-form>
          </el-collapse-item>

          <el-collapse-item name="2" title="权限信息">
            <!-- 权限列表 -->
            <permission-list :role-edit-hide="true" :role-id="roleInfo.id"
                             @checkedArray="handleSelectionChange"/>
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
    <el-button size="mini" type="primary" @click="validSubmit">保存</el-button>
    <el-button size="mini" @click="onReset('roleInfo')">重置</el-button>
    <el-button size="mini" @click="onReturn">返回</el-button>
  </div>
</template>

<script>
import {
  LoadDataChildrenMenuIdList,
  SaveRole,
  CheckRoleCode
} from "../../../api/sys/roleMag";

import CustomSelect from '../../../components/common/CustomSelect.vue';
import CustomMenuTree from '../../../components/common/CustomMenuTree.vue'
import PermissionList from '../../../views/sys/PermissionList.vue';

export default {
  components: {
    CustomSelect,
    PermissionList,
    CustomMenuTree
  },
  data() {
    return {
      activeNames: ["1", "2"],
      roleInfo: {
        id: null,
        roleName: "",
        roleCode: "",
        description: "",
        validInd: true
      },
      permissionChecked: [], // 被选种的权限ID
      oldPermissionChecked: [], // 原始被选种的权限ID
      menuChecked: [], // 被选种的菜单ID
      rulesForm: {
        roleName: [{required: true, message: '请输入角色名', trigger: 'blur'}],
        roleCode: [{required: true, message: '请输入角色CODE', trigger: 'blur'},
          {validator: this.checkRoleCode, trigger: 'blur'}]
      },
      oldRoleCode: '',// 旧roleCode，用于校验
      defaultCheckedMenu: [],// 初始化选种的
    };
  },
  created() {
    if (this.$route.query.optionType === 'edit') {
      var roleInfo = JSON.parse(this.$route.query.roleEdit);
      this.roleInfo = roleInfo;
      this.oldRoleCode = roleInfo.roleCode;
    }
    // 初始化菜单树
    // this.findMenus();
    // 加载菜单选种状态
    this.loadCheckMenu();
  },
  methods: {
    // 收集选种和半选种的所有菜单id
    changeMenuTree(menuChecked) {
      // 选种的菜单
      // this.menuChecked = menuChecked;
      // console.info("menuChecked",this.menuChecked);
    },
    // 加载菜单选种状态
    loadCheckMenu() {
      LoadDataChildrenMenuIdList(this.roleInfo.id).then(res => {
        // 该角色所拥有的菜单
        this.defaultCheckedMenu = [...new Set(res.resData)];
      });
    },
    validSubmit() {
      var _this = this;
      this.$refs['roleInfo'].validate(valid => {
        if (valid) {
          _this.onSubmit();
        } else {
          return false;
        }
      });
    },
    onSubmit() {
      var _this = this;
      var roleInfo = _this.roleInfo;
      roleInfo['permissionChecked'] = _this.permissionChecked;
      roleInfo['oldPermissionChecked'] = _this.oldPermissionChecked;
      roleInfo['menuChecked'] = _this.$refs.menuTree.getCheckAll();
      SaveRole(roleInfo).then(res => {
        _this.$message({
          type: "success",
          message: "保存成功!"
        });
        this.$bus.emit('roleInfo', res.resData);
        _this.$router.back(-1);
      });
    },
    onReturn() {
      this.$router.back(-1);
    },
    onReset(roleInfo) {
      this.$refs[roleInfo].resetFields();
    },
    handleSelectionChange(checkData, oldPermissionChecked, type) {
      this.permissionChecked = checkData;
      this.oldPermissionChecked = oldPermissionChecked;
      // console.info(type + " parent this.permissionChecked", this.permissionChecked);
      // console.info(type + " parent this.oldPermissionChecked", this.oldPermissionChecked);
    },
    // 校验角色CODE 是否重复
    checkRoleCode(rule, value, callback) {
      if (this.oldRoleCode != '' && this.oldRoleCode === value) {
        callback();
        return true;
      }
      CheckRoleCode(value).then(res => {
        if (res.resData >= 1) {
          callback(new Error("角色CODE已经存在"));
        } else {
          callback();
        }
      });
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

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
