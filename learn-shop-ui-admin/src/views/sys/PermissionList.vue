<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :inline="true" :model="queryFilter" ref="queryFilter" class="demo-form-inline" size="mini">
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
              <el-form-item label="系统模块" prop="systemModules">
                <custom-select v-model="queryFilter.systemModules"
                               :datasource="systemModuleSelect"
                               placeholder="请选择系统模块"
                               multiple>
                </custom-select>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <el-button type="success" size="mini" @click="handleAdd" icon="el-icon-plus">添加</el-button>
    <el-button type="primary" size="mini" @click="onQuery" icon="el-icon-search">查询</el-button>
    <el-button type="info" size="mini" @click="resetForm('queryFilter')" icon="el-icon-close">重置</el-button>
    <el-button type="warning" size="mini" @click="refresh" icon="el-icon-refresh">刷新</el-button>
    <el-row>
      <template>
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column label="权限名称" prop="permissionName" width="210"></el-table-column>
          <el-table-column label="授权链接" prop="url"></el-table-column>
          <el-table-column label="权限描述" prop="descritpion"></el-table-column>
          <el-table-column label="系统模块" prop="systemModule">
            <template slot-scope="scope">
              <custom-select v-model="scope.row.systemModules"
                             :datasource="systemModuleSelect"
                             :value-key="scope.row.url"
                             disabled
                             placeholder="请选择系统模块"
                             multiple>
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand" label-width="80px">
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
                <el-form-item label="权限CODE">
                  <span>{{ scope.row.permissionCode }}</span>
                </el-form-item>
                <el-form-item label="是否有效">
                  <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" content="禁用" placement="top-start">
                <el-button @click="handleProhibit(scope.$index, scope.row)" type="warning" size="mini"
                           :disabled="!scope.row.validInd">
                  <i class="el-icon-warning"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="删除" placement="top-start">
                <el-button @click="handleDelete(scope.$index, scope.row)" type="danger" size="mini">
                  <i class="el-icon-delete"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="修改" placement="top-start">
                <el-button @click="handleEdit(scope.$index, scope.row)" type="primary" size="mini">
                  <i class="el-icon-edit"></i></el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <el-row>
      <template>
        <div class="block">
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="queryFilter.pageNo"
            :page-sizes="[10, 20, 30, 40]"
            layout="total,sizes, prev, pager, next"
            :page-size="queryFilter.pageSize"
            :total="queryFilter.recordCount">
          </el-pagination>
        </div>
      </template>
    </el-row>
  </div>
</template>

<script>
  import {LoadDataPermissionList, DeletePermissionById, ProhibitPermissionById} from "../../api/sys/permissionMag";
  import {LoadSysDataDictionary} from "../../api/sys/DataDictionaryMag";
  import CustomSelect from '../../components/common/CustomSelect.vue';

  export default {
    components: {
      CustomSelect
    },
    data() {
      return {
        queryFilter: {
          // 分页数据
          pageNo: null,// 当前页码
          recordCount: null, // 总记录数
          pageSize: null,//每页要显示的记录数
          totalPages: null,// 总页数
          // 查询条件
          permissionName: null,
          permissionCode: null,
          systemModule: null,
          url: null
        },
        tableData: [],
        activeNames: ['1'],
        systemModuleSelect: [],
        SystemModule: 'SystemModule'
      }
    },
    created() {
      // 加载系统模块的下拉
      this.LoadSysDataDictionary('SystemModule');
      // 请数据殂
      this.LoadDataPermissionList();
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('permissionInfo', function (data) {
        var index = this.tableData.findIndex(f => f.id === data.id);
        if (index != -1) {// 更新
          this.tableData.splice(index, 1, data);
        } else {// 添加
          this.tableData.push(data);
        }
      }.bind(this));
    },
    methods: {
      // 查询按钮
      onQuery() {
        // 从第1页开始
        this.queryFilter.pageNo = 1;
        // 请求数据
        this.LoadDataPermissionList();
        // 关闭查询折叠栏
//        this.activeNames = [];
      },
      // 清除查询条件
      resetForm(queryFilter) {
        this.$refs[queryFilter].resetFields();
      },
      // 请服务器数据（获取权限列表数据）
      LoadDataPermissionList() {
        LoadDataPermissionList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
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
      handleEdit(index, row) {
        this.$router.push({
          name: 'sysPermissionEdit',
          query: {
            optionType: 'edit',
            permissionEdit: JSON.stringify(row),
            systemModuleSelect: JSON.stringify(this.systemModuleSelect)
          }
        });
      },
      // 刷新数据
      refresh() {
        this.LoadDataPermissionList();
      },
      handleDelete(index, row) {
        var _this = this;
        _this.$confirm('此操作将删除该权限 ' + row.url + ' 信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          DeletePermissionById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        }).catch((err) => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //加载下拉列表
      LoadSysDataDictionary(fieldType) {
        LoadSysDataDictionary(fieldType).then(res => {
          this.systemModuleSelect = res.resData;
        });
      },
      // 翻页
      handleCurrentChange(val) {
        this.queryFilter.pageNo = val;
        this.LoadDataPermissionList();
      },
      // 改变页面大小
      handleSizeChange(val) {
        this.queryFilter.pageSize = val;
        this.LoadDataPermissionList();
      },
      handleProhibit(index, row) {
        var _this = this;
        _this.$confirm('此操作将禁用该权限 ' + row.url + ' 信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          ProhibitPermissionById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        }).catch((err) => {
          _this.$message({
            type: 'info',
            message: '已取消禁用'
          });
        });
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
    width: 3px; /*滚动条宽度*/
    height: 3px; /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px; /*滚动条的背景区域的圆角*/
    background-color: white; /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px; /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #2e363f; /*滚动条的背景颜色*/
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
