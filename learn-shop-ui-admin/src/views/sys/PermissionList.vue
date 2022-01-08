<template>
  <div>
    <el-row v-if="!roleEditHide">
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form ref="queryFilter" :inline="true" :model="queryFilter" class="demo-form-inline" size="mini">
            <el-row>
              <el-form-item label="权限名称" prop="permissionName">
                <el-input v-model="queryFilter.permissionName" placeholder="权限名称"></el-input>
              </el-form-item>
              <el-form-item label="权限CODE" prop="permissionCode">
                <el-input v-model="queryFilter.permissionCode" placeholder="权限CODE"></el-input>
              </el-form-item>
              <el-form-item label="授权链接" prop="url">
                <el-input v-model="queryFilter.url" placeholder="授权链接"></el-input>
              </el-form-item>
              <el-form-item label="系统模块" prop="systemModule">
                <custom-select v-model="queryFilter.systemModule"
                               :datasource="systemModuleSelect"
                               placeholder="请选择系统模块">
                </custom-select>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query v-if="!roleEditHide" :queryFilter="queryFilter" @onAdd="handleAdd"
                        @onQuery="loadDataList"/>
    <el-row>
      <template>
        <el-table ref="permissionListRef" :data="tableData" border
                  row-key="id"
                  style="width: 100%"
                  @select="handleSelect"
                  @select-all="handleSelectAll">
          <el-table-column v-if="roleEditHide" prop="checked" reserve-selection type="selection"
                           width="35"></el-table-column>
          <el-table-column label="权限名称" prop="permissionName" width="210"></el-table-column>
          <el-table-column label="授权链接" prop="url"></el-table-column>
          <el-table-column label="权限描述" prop="description"></el-table-column>
          <el-table-column label="系统模块" prop="systemModule">
            <template slot-scope="scope">
              <custom-select v-model="scope.row.systemModule" :datasource="systemModuleSelect"
                             :value-key="scope.row.url"
                             disabled placeholder="请选择系统模块">
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column label="详细" type="expand" width="50">
            <template slot-scope="scope">
              <el-form class="demo-table-expand" inline label-position="left" label-width="120px">
                <el-form-item label="创建人">
                  <span>{{ scope.row.creatorCode }}</span>
                </el-form-item>
                <el-form-item label="更新人">
                  <span>{{ scope.row.updaterCode }}</span>
                </el-form-item>
                <el-form-item label="创建时间">
                  <el-date-picker v-model="scope.row.createTime" readonly type="datetime"></el-date-picker>
                </el-form-item>
                <el-form-item label="更新时间">
                  <el-date-picker v-model="scope.row.updateTime" readonly type="datetime"></el-date-picker>
                </el-form-item>
                <el-form-item label="权限CODE">
                  <span>{{ scope.row.permissionCode }}</span>
                </el-form-item>
                <el-form-item label="是否有效">
                  <el-switch v-model="scope.row.validInd" active-text="有效" disabled inactive-text="无效"></el-switch>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column v-if="!roleEditHide" fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <!--  操作按钮组 -->
              <button-group-option :disInd="!scope.row.validInd"
                                   @onDel="handleDelete(scope.row,scope.$index)"
                                   @onEdit="handleEdit(scope.row,scope.$index)"
                                   @onInd="handleProhibit(scope.row,scope.$index)"></button-group-option>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <!-- 分页组件  -->
    <custom-page v-if="!roleEditHide" :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
  </div>
</template>

<script>
// ===== api start
import {
  LoadDataPermissionList,
  DeletePermissionById,
  ProhibitPermissionById,
  FindPermissionByMenuId
} from "../../api/sys/permissionMag";
import {
  LoadDataPermissionIdList
} from "../../api/sys/roleMag";
import {
  LoadSysDataDictionary
} from "../../api/sys/DataDictionaryMag";
// ===== component start
import CustomSelect from '../../components/common/CustomSelect.vue';
import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
import CustomPage from '../../components/common/CustomPage.vue'
// ===== 工具类 start
import VueUtils from "../../utils/vueUtils";
import pageMixins from "../../utils/pageMixins";

export default {
  components: {
    CustomSelect,
    ButtonGroupOption,
    ButtonGroupQuery,
    CustomPage
  },
  props: {
    // 是否是从角色修改页面过来
    roleEditHide: {
      type: Boolean,
      default: false
    },
    // 角色的 id
    roleId: {
      type: String,
      default: null
    },
    // 菜单的 id
    menuId: {
      type: String,
      default: null
    },
    // 权限列表
    permissionList: {
      type: Array,
      default: null
    }
  },
  mixins: [pageMixins],
  data() {
    return {
      queryFilter: {
        // 查询条件
        permissionName: null,
        permissionCode: null,
        systemModule: null,
        url: null
      },
      tableData: [], // 列表数据源
      systemModuleSelect: [],// 系统模块的下拉数据源
      loadPermissionCheckedFlag: 0, // 监控列表数据加载完成后，加载选种
      permissionChecked: null, // 被选种的权限ID
      oldPermissionChecked: null, // 原始被选种的权限ID
    }
  },
  created() {
    // 加载系统模块的下拉
    LoadSysDataDictionary('systemModule').then(res => {
      this.systemModuleSelect = res.resData;
    });
    // 请求数据
    if (!this.roleEditHide) {
      this.loadDataList();
    }
  },
  // //每次激活时
  // activated() {
  //   // 根据key名获取传递回来的参数，data 就是 map
  //   this.$bus.once('permissionInfo', function (data) {
  //     var index = this.tableData.findIndex(f => f.id === data.id);
  //     if (index != -1) { // 更新
  //       this.tableData.splice(index, 1, data);
  //     } else { // 添加
  //       this.tableData.push(data);
  //     }
  //   }.bind(this));
  // },
  methods: {
    // 设置列表选种状态
    settSelection() {
      // 第一次加载时
      if (this.permissionChecked == null) {
        // 加载权限选种状态
        LoadDataPermissionIdList(this.roleId).then(res => {
          // 设置选种状态
          this.tableData.forEach(item => {
            this.$refs.permissionListRef.toggleRowSelection(
              item,
              res.resData.includes(item.id)
            );
          });
          this.permissionChecked = [...new Set(res.resData)];
          this.oldPermissionChecked = [...new Set(res.resData)];
          this.$emit("checkedArray", this.permissionChecked, this.oldPermissionChecked, "watch1");
        });
      } else {
        // 设置选种状态
        this.tableData.forEach(item => {
          this.$refs.permissionListRef.toggleRowSelection(
            item,
            this.permissionChecked.includes(item.id)
          );
        });
      }
    },
    findPermissionByMenuId() {
      if (this.menuId) {
        FindPermissionByMenuId(this.menuId).then(res => {
          this.tableData = res.resData;
          // 设置列表选种状态
          this.settSelection();
        });
      }
    },
    // 获取权限列表数据
    loadDataList() {
      LoadDataPermissionList(this.queryFilter).then(res => {
        var data = res.resData;
        this.tableData = data.tableData;
        this.queryFilter.recordCount = data.recordCount;
        this.queryFilter.totalPages = data.totalPages;
      });
    },
    // 添加权限
    handleAdd() {
      this.$router.push({
        name: 'sysPermissionEdit',
        query: {
          optionType: 'add',
          systemModuleSelect: JSON.stringify(this.systemModuleSelect)
        }
      });
    },
    handleEdit(row, index) {
      this.$router.push({
        name: 'sysPermissionEdit',
        query: {
          optionType: 'edit',
          permissionEdit: JSON.stringify(row),
          systemModuleSelect: JSON.stringify(this.systemModuleSelect)
        }
      });
    },
    handleDelete(row, index) {
      const _this = this;

      VueUtils.confirmDel(row.url, () => {
        DeletePermissionById(row.id).then(res => {
          _this.tableData.splice(index, 1);
          _this.$message({
            type: 'success',
            message: '删除成功!'
          });
        });
      });
    },
    handleProhibit(row, index) {
      const _this = this;

      VueUtils.confirmInd(row.url, () => {
        ProhibitPermissionById(row.id).then(res => {
          row.validInd = res.resData.validInd;
          _this.$message({
            type: 'success',
            message: '禁用成功!'
          });
        });
      });
    },
    // 勾选单个后，发送通知
    handleSelect(selection, row) {
      const checkedIds = selection.map(item => item.id);
      if (checkedIds.includes(row.id)) {
        this.permissionChecked.push(row.id);
      } else {
        var index = this.permissionChecked.indexOf(row.id);
        if (index > -1) {
          this.permissionChecked.splice(index, 1);
        }
      }
      this.$emit("checkedArray", this.permissionChecked, this.oldPermissionChecked, "handleSelect");
    },
    // 勾选全部后
    handleSelectAll(selection) {
      if (selection.length > 0) {
        selection.forEach(item => {
          if (!this.permissionChecked.includes(item.id)) {
            this.permissionChecked.push(item.id);
          }
        });
      } else {
        this.tableData.forEach(item => {
          const index = this.permissionChecked.indexOf(item.id);
          if (index > -1) {
            this.permissionChecked.splice(index, 1);
          }
        });
      }
      this.$emit("checkedArray", this.permissionChecked, this.oldPermissionChecked, "handleSelectAll");
    }
  },
  watch: {
    menuId(val) {
      if (val === 0 || this.roleId == null) {
        return;
      }
      // console.info("val",val);
      // console.info("roleId",this.roleId);
      // 加载菜单权限
      this.findPermissionByMenuId();
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 5px;
}

.el-form-item {
  margin-bottom: 3px;
}

/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
  width: 3px;
  /*滚动条宽度*/
  height: 3px;
  /*滚动条高度*/
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
  /*滚动条的背景区域的圆角*/
  background-color: white;
  /*滚动条的背景颜色*/
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  /*滚动条的圆角*/
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: #2e363f;
  /*滚动条的背景颜色*/
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
