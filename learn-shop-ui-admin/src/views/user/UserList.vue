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
              <el-form-item label="用户名称" prop="username">
                <el-input v-model="queryFilter.username" placeholder="用户名称"></el-input>
              </el-form-item>
              <el-form-item label="用户CODE" prop="usercode">
                <el-input v-model="queryFilter.usercode" placeholder="用户CODE"></el-input>
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
        <el-table border stripe style="width: 100%" @expand-change="loadUserRole" :expand-row-keys="expandRows" row-key="id" :data="tableData">
          <el-table-column label="用户名称" prop="username"></el-table-column>
          <el-table-column label="用户CODE" prop="usercode"></el-table-column>
          <el-table-column label="用户描述" prop="descritpion"></el-table-column>
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
                  <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效" @change="userInfoChange(scope.row)"></el-switch>
                </el-form-item>
                <el-form-item label="角色信息">
                  <custom-select v-model="scope.row.roleIds" :datasource="selectRole" :value-key="scope.row.usercode"
                    @onchange="userInfoChange(scope.row)"
                    placeholder="请选择角色" multiple >
                  </custom-select>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" content="设置为无效" placement="top-start">
                <el-button @click="handleProhibit(scope.$index, scope.row)" type="warning" size="mini" :disabled="!scope.row.validInd">
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
          <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page.sync="queryFilter.pageNo" :page-sizes="[10, 20, 30, 40]" layout="total,sizes, prev, pager, next"
            :page-size="queryFilter.pageSize" :total="queryFilter.recordCount">
          </el-pagination>
        </div>
      </template>
    </el-row>
  </div>
</template>

<script>
  import {
    LoadDataUserList,
    DeleteUserById,
    ProhibitUserById,
    LoadRoleIdsByUserId,
    UpdateUser
  } from "../../api/user/userMag";
  import {
    LoadSelectRoleList
  } from "../../api/sys/roleMag";
  import CustomSelect from '../../components/common/CustomSelect.vue';

  export default {
    components: {
      CustomSelect
    },
    data() {
      return {
        queryFilter: {
          // 分页数据
          pageNo: null, // 当前页码
          recordCount: null, // 总记录数
          pageSize: null, //每页要显示的记录数
          totalPages: null, // 总页数
          // 查询条件
          username: null,
          usercode: null
        },
        tableData: [], // 列表数据
        selectRole: [], // 角色下拉列表
        expandRows:[] // 打开的折叠拦
      }
    },
    created() {
      // 加载角色下拉列表
      this.loadSelectRoleList();
      // 请求列表数据
      this.loadDataUserList();
    },
    //每次激活时
    activated() {
      var _this = this;
      this.$bus.on('userInfo', function(data) {
        var index = _this.tableData.findIndex(f => f.id === data.id);
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
        this.loadDataUserList();
      },
      // 清除查询条件
      resetForm(queryFilter) {
        this.$refs[queryFilter].resetFields();
      },
      // 刷新数据
      refresh() {
        this.loadDataUserList();
      },
      // 请服务器数据（获取列表数据）
      loadDataUserList() {
        LoadDataUserList(this.queryFilter).then(res => {
          var data = res.resData;
          // 初始化 绑定一个 roleIds，不然后面监听不到
          for (let index in data.content) {
            Object.assign(data.content[index], {
              roleIds: []
            });
          }
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
          this.queryFilter.totalPages = data.totalPages;
        });

      },
      // 翻页
      handleCurrentChange(val) {
        this.queryFilter.pageNo = val;
        this.loadDataUserList();
      },
      // 改变页面大小
      handleSizeChange(val) {
        this.queryFilter.pageSize = val;
        this.loadDataUserList();
      },
      // 添加用户
      handleAdd() {
        this.$router.push({
          name: 'userUserEdit',
          query: {
            optionType: 'add',
            selectRole: JSON.stringify(this.selectRole)
          }
        });
      },
      // 修改用户
      handleEdit(index, row) {
        // 打开折叠栏，再点击编辑保存后，折叠栏有bug
        this.expandRows = [];
        row.password = null;
        this.$router.push({
          name: 'userUserEdit',
          query: {
            optionType: 'edit',
            selectRole: JSON.stringify(this.selectRole),
            userEdit: JSON.stringify(row)
          }
        });
      },
      // 禁用
      handleProhibit(index, row) {
        var _this = this;
        _this.$confirm('此操作将禁用该用户 ' + row.username + ' 信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          ProhibitUserById(row.id).then(res => {
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
      },
      // 删除
      handleDelete(index, row) {
        var _this = this;
        _this.$confirm('此操作将删除该用户 ' + row.username + ' 信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          DeleteUserById(row.id).then(res => {
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
      // 加载角色信息的下拉列表
      loadSelectRoleList() {
        LoadSelectRoleList().then(res => {
          this.selectRole = res.resData;
        });
      },
      // 加载指定用户的角色信息
      loadUserRole(row, expandedRows) {
        if (row.roleIds.length < 1) {
          LoadRoleIdsByUserId(row.id).then(res => {
            var roleIds = res.resData.roleIds;
            Object.assign(row, {
              roleIds: roleIds
            });
          });
        }
      },
      //
      userInfoChange(row){
        UpdateUser(row).then(res => {
          this.$message({
            type: 'success',
            message: '更新成功!'
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
