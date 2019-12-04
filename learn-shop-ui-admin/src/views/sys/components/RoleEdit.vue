<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="10">
        <el-collapse v-model="activeNames">
          <el-collapse-item title="菜单树" name="0">
            <el-input placeholder="输入关键字进行过滤" size="mini" v-model="filterMenu"></el-input>
            <div class="sidebar">
              <el-tree
                show-checkbox
                default-expand-all
                node-key="id"
                :data="menus"
                ref="menuTree"
                :highlight-current="true"
                :props="defaultProps"
                :check-strictly="false"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                @check-change="changeMenuTree"
              ></el-tree>
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-col>

      <el-col :span="14">
        <el-collapse v-model="activeNames">
          <el-collapse-item title="角色信息" name="1">
            <el-form inline ref="roleInfo" label-width="95px" :model="roleInfo" size="mini" :rules="rulesForm">
              <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="roleInfo.roleName"></el-input>
              </el-form-item>
              <el-form-item label="角色CODE" prop="roleCode">
                <el-input v-model="roleInfo.roleCode"></el-input>
              </el-form-item>
              <el-form-item label="角色描述" prop="descritpion">
                <el-input v-model="roleInfo.descritpion"></el-input>
              </el-form-item>
              <el-form-item label="是否有效" prop="validInd">
                <el-switch v-model="roleInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
              </el-form-item>
            </el-form>
          </el-collapse-item>

          <el-collapse-item title="权限信息" name="2">
            <!-- 权限列表 -->
            <permission-list :role-edit-hide="true" :role-id="roleInfo.id"
                             @checkedArray="handleSelectionChange"></permission-list>
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
    <el-button type="primary" @click="validSubmit" size="mini">保存</el-button>
    <el-button @click="onReset('roleInfo')" size="mini">重置</el-button>
    <el-button @click="onReturn" size="mini">返回</el-button>
  </div>
</template>

<script>
  import {findMenus} from "../../../api/sys/menuMag";
  import {
    LoadDataMenuIdList,
    SaveRole,
    CheckRoleCode
  } from "../../../api/sys/roleMag";
  import CustomSelect from '../../../components/common/CustomSelect.vue';

  import PermissionList from '../../../views/sys/PermissionList.vue';

  export default {
    components: {
      CustomSelect,
      PermissionList
    },
    data() {
      return {
        activeNames: ["0", "1", "2"],
        defaultProps: {
          //设置数据绑定
          children: "children",
          label: "title"
        },
        roleInfo: {
          id: null,
          roleName: "",
          roleCode: "",
          descritpion: "",
          validInd: true
        },
        permissionChecked: [], // 被选种的权限ID
        oldPermissionChecked: [], // 原始被选种的权限ID
        menus: [], // 菜单树数据源
        filterMenu: "", // 菜单树过滤
        menuChecked: [], // 被选种的菜单ID
        oldMenuChecked: [], // 原始被选种的菜单ID
        loadMenuCheckedFlag: false, // 是否初始化加载被选种的菜单
        rulesForm: {
          roleName: [{required: true, message: '请输入角色名', trigger: 'blur'}],
          roleCode: [{required: true, message: '请输入角色CODE', trigger: 'blur'},
            {validator: this.checkRoleCode, trigger: 'blur'}]
        },
        oldRoleCode: '',// 旧roleCode，用于校验
      };
    },
    created() {
      if (this.$route.query.optionType === 'edit') {
        var roleInfo = JSON.parse(this.$route.query.roleEdit);
        this.roleInfo = roleInfo;
        this.oldRoleCode = roleInfo.roleCode;
      }
      // 初始化菜单树
      this.findMenus();
    },
    methods: {
      // 收集选种和半选种的所有菜单id
      changeMenuTree() {
        // 选种的菜单
        this.menuChecked = this.$refs.menuTree.getCheckedNodes().map(m => m.id);
        // 半先种的父级菜单
        this.menuChecked = this.menuChecked.concat(
          this.$refs.menuTree.getHalfCheckedNodes().map(m => m.id)
        );
        // console.info("oldMenuChecked",this.oldMenuChecked);
        // console.info("menuChecked",this.menuChecked);
      },
      //获取所有菜单
      findMenus() {
        // 加载菜单
        findMenus().then(res => {
          this.menus = res.resData;
          // 如果是修改的时候
          if (this.roleInfo.id) {
            // 加载菜单选种状态
            this.loadMenuCheckedFlag = true;
          }
        });
      },
      // 过滤搜索
      filterNode(value, data) {
        if (!value) return true;
        return data.title.indexOf(value) !== -1;
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
        roleInfo['menuChecked'] = _this.menuChecked;
        roleInfo['oldMenuChecked'] = _this.oldMenuChecked;
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
    },
    watch: {
      //通过 :filter-node-method,找到过滤方法
      filterMenu(val) {
        this.$refs.menuTree.filter(val);
      },
      loadMenuCheckedFlag(loadMenuCheckedFlag) {
        if (!loadMenuCheckedFlag) {
          return;
        }
        // 加载菜单选种状态
        LoadDataMenuIdList(this.roleInfo.id).then(res => {
          // 需要移除的父级ids,因为菜单树会自己关联
          let deleteMenuIds = new Set();
          // 该角色所拥有的菜单
          var checkedMenu = res.resData;
          this.oldMenuChecked = [...new Set(res.resData)];
          // 构建出所有需要移除的父级ids
          for (let i = 0; i < checkedMenu.length; i++) {
            var menuParentIds = this.VueUtils.getParentByCurrentNodeId(this.menus, checkedMenu[i]).map(m => m.id);
            // 返回的父级id
            menuParentIds.forEach(f => deleteMenuIds.add(f));
          }
          // 移除父级所有id
          deleteMenuIds.forEach(id => {
            checkedMenu.splice(checkedMenu.indexOf(id), 1);
          });
          // 设置菜单树选种
          this.$refs.menuTree.setCheckedKeys(checkedMenu);
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
