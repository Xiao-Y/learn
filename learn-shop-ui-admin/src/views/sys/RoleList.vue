<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form inline :model="queryFilter" ref="queryFilter" class="query-form-filter" size="mini">
            <el-row>
              <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="queryFilter.roleName" placeholder="角色名称"></el-input>
              </el-form-item>
              <el-form-item label="角色CODE" prop="roleCode">
                <el-input v-model="queryFilter.roleCode" placeholder="角色CODE"></el-input>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query @onAdd="handleAdd"
                        @onQuery="loadDataList"
                        :hasAdd="'RoleApi-saveRole'"
                        :queryFilter="queryFilter"></button-group-query>

    <el-row>
      <template>
        <el-table :data="tableData" border stripe >
          <el-table-column label="角色名称" prop="roleName"></el-table-column>
          <el-table-column label="角色CODE" prop="roleCode"></el-table-column>
          <el-table-column label="角色描述" prop="description"></el-table-column>
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="ms-table-expand" label-width="120px">
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
                  <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <!--  操作按钮组 -->
              <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                   @onEdit="handleEdit(scope.row,scope.$index)"
                                   @onInd="handleProhibit(scope.row,scope.$index)"
                                   :hasDel="'sys:role:deleteRoleById'"
                                   :hasEdit="'sys:role:saveRole'"
                                   :hasInd="'sys:role:prohibitRoleById'"
                                   :disInd="!scope.row.validInd">

              </button-group-option>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>

  </div>
</template>

<script>
  // ===== api start
  import {
    LoadDataRoleList,
    DeleteRoleById,
    ProhibitRoleById
  } from "../../api/sys/roleMag";
  // ===== component start
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue';
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  export default {
    name: "sysRoleListIndex",
    // 引入组件
    components: {
      ButtonGroupOption,
      ButtonGroupQuery,
      CustomPage
    },
    mixins:[pageMixins],
    data() {
      return {
        queryFilter: {
          // 查询条件
          roleName: null,
          roleCode: null
        },
        tableData: []
      }
    },
    created() {
      // 请数据殂
      this.loadDataList();
    },
    //每次激活时
    activated() {
      var _this = this;
      this.$bus.once('roleInfo', function (data) {
        var index = _this.tableData.findIndex(f => f.id === data.id);
        if (index != -1) { // 更新
          this.tableData.splice(index, 1, data);
        } else { // 添加
          this.tableData.push(data);
        }
      }.bind(this));
    },
    methods: {
      // 获取角色列表数据
      loadDataList() {
        LoadDataRoleList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });

      },
      // 添加角色
      handleAdd() {
        this.$router.push({
          name: 'sysRoleEdit',
          query: {
            optionType: 'add'
          }
        });
      },
      handleEdit(row, index) {
        this.$router.push({
          name: 'sysRoleEdit',
          query: {
            optionType: 'edit',
            roleEdit: JSON.stringify(row)
          }
        });
      },
      handleProhibit(row, index) {
        var _this = this;
        VueUtils.confirmInd(row.roleName, () => {
          ProhibitRoleById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      handleDelete(row, index) {
        var _this = this;
        VueUtils.confirmDel(row.roleName,()=>{
          DeleteRoleById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      }
    }
  }
</script>

