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
            <el-form :inline="true" ref="roleInfo" label-width="80px" :model="roleInfo" size="mini">
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
            <el-input v-model="filterPermission" size="mini" placeholder="输入关键字进行过滤"/>
            <el-table
              ref="tableDataTemp"
              :data="tableDataTemp"
              border
              height="500"
              @selection-change="handleSelectionChange"
              row-key="id"
            >
              <el-table-column type="selection" width="35" prop="checked" reserve-selection></el-table-column>
              <el-table-column label="权限名称" prop="permissionName" width="150"></el-table-column>
              <el-table-column label="授权链接" prop="url"></el-table-column>
              <el-table-column type="expand" label="详细" width="50">
                <template slot-scope="scope">
                  <el-form label-position="left" inline class="demo-table-expand">
                    <el-form-item label="创建人">
                      <span>{{ scope.row.creatorCode }}</span>
                    </el-form-item>
                    <el-form-item label="更新人">
                      <span>{{ scope.row.updaterCode }}</span>
                    </el-form-item>
                    <el-form-item label="创建时间">
                      <el-date-picker type="datetime" v-model="scope.row.createTime" readonly></el-date-picker>
                    </el-form-item>
                    <el-form-item label="更新时间">
                      <el-date-picker type="datetime" v-model="scope.row.updateTime" readonly></el-date-picker>
                    </el-form-item>
                    <el-form-item label="是否有效">
                      <el-switch
                        v-model="scope.row.validInd"
                        active-text="有效"
                        inactive-text="无效"
                        disabled
                      ></el-switch>
                    </el-form-item>
                    <el-form-item label="权限CODE">
                      <span>{{ scope.row.permissionCode }}</span>
                    </el-form-item>
                    <el-form-item label="系统模块">
                      <el-select
                        v-model="scope.row.systemModules"
                        multiple
                        filterable
                        default-first-option
                        disabled
                        placeholder="请选择文章标签"
                      >
                        <el-option
                          v-for="item in systemModuleSelect"
                          :key="item.id"
                          :label="item.fieldDisplay"
                          :value="item.fieldValue"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="权限描述">
                      <span>{{ scope.row.descritpion }}</span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
            </el-table>
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
    <el-button type="primary" @click="onSubmit" size="mini">保存</el-button>
    <el-button @click="onReset('roleInfo')" size="mini">重置</el-button>
    <el-button @click="onReturn" size="mini">返回</el-button>
  </div>
</template>

<script>
  import {
    LoadDataPermissionListAll
  } from "../../../api/sys/permissionMag";
  import {findMenus} from "../../../api/sys/menuMag";
  import {LoadSysDataDictionary} from "../../../api/sys/DataDictionaryMag";
  import {
    LoadDataPermissionIdList,
    LoadDataMenuIdList,
    SaveRole
  } from "../../../api/sys/roleMag";

  export default {
    data() {
      return {
        activeNames: ["0", "1", "2"],
        defaultProps: {
          //设置数据绑定
          children: "children",
          label: "title"
        },
        roleInfo: {
          roleName: "",
          roleCode: "",
          descritpion: "",
          validInd: true
        },
        tableData: [], // 原始权限列表
        tableDataTemp: [], // 过滤后的权限列表
        filterPermission: "", // 权限过滤
        systemModuleSelect: [], // 系统模块下拉列表
        permissionChecked: [], // 被选种的权限ID
        loadPermissionCheckedFlag: false,// 是否初始化加载被选种的权限
        menus: [], // 菜单树数据源
        filterMenu: "", // 菜单树过滤
        menuChecked: [], // 被选种的菜单ID
        loadMenuCheckedFlag: false// 是否初始化加载被选种的菜单
      };
    },
    created() {
      this.roleInfo = this.$route.params.roleEdit;
      // 初始化菜单树
      this.findMenus();
      // 获取权限列表数据
      this.LoadDataPermissionListAll();
      // 加载系统模块的下拉
      this.LoadSysDataDictionary("SystemModule");
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
      },
      //加载下拉列表
      LoadSysDataDictionary(fieldType) {
        LoadSysDataDictionary(fieldType).then(res => {
          this.systemModuleSelect = res.resData;
        });
      },
      // 获取权限列表数据
      LoadDataPermissionListAll() {
        LoadDataPermissionListAll(this.queryFilter).then(res => {
          this.tableData = res.resData;
          this.tableDataTemp = this.tableData;
          // 如果是修改的时候
          if (this.roleInfo.id) {
            // 加载权限选种状态
            this.loadPermissionCheckedFlag = true;
          }
        });
      },
      //获取所有菜单
      findMenus() {
        var _this = this;
        // 加载菜单
        findMenus().then(res => {
          _this.menus = res.resData;
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
      onSubmit() {
        var _this = this;
        var roleInfo = _this.roleInfo;
        roleInfo['permissionChecked'] = _this.permissionChecked;
        roleInfo['menuChecked'] = _this.menuChecked;
        SaveRole(roleInfo).then(res => {
          _this.$message({
            type: "success",
            message: "保存成功!"
          });
          _this.$router.back(-1);
        });
      },
      onReturn() {
        this.$router.back(-1);
      },
      onReset(roleInfo) {
        this.$refs[roleInfo].resetFields();
      },
      handleSelectionChange(row) {
        this.permissionChecked = row.map(item => item.id);
      }
    },
    watch: {
      //通过 :filter-node-method,找到过滤方法
      filterMenu(val) {
        this.$refs.menuTree.filter(val);
      },
      filterPermission(val) {
        this.tableDataTemp = this.tableData.filter(data => {
          if (!val) {
            return true;
          }
          if (data.permissionName) {
            return data.permissionName.includes(val);
          } else if (data.permissionCode) {
            return data.permissionCode.includes(val);
          } else if (data.url.toLowerCase()) {
            return data.url.includes(val.toLowerCase());
          }
        });
      },
      loadPermissionCheckedFlag(val) {
        if (!val) {
          return;
        }
        // 加载权限选种状态
        LoadDataPermissionIdList(this.roleInfo.id).then(res => {
          // 设置选种状态
          this.tableData.forEach(item => {
            this.$refs.tableDataTemp.toggleRowSelection(
              item,
              res.resData.includes(item.id)
            );
          });
        });
      },
      loadMenuCheckedFlag(val) {
        if (!val) {
          return;
        }
        // 加载菜单选种状态
        LoadDataMenuIdList(this.roleInfo.id).then(res => {
          // 需要移除的父级ids,因为菜单树会自己关联
          let deleteMenuIds = new Set();
          var checkedMenu = res.resData;
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
