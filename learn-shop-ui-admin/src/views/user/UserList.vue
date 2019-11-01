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
              <el-form-item label="姓名" prop="username">
                <el-input v-model="queryFilter.username" placeholder="用户名称"></el-input>
              </el-form-item>
              <el-form-item label="账号" prop="usercode">
                <el-input v-model="queryFilter.usercode" placeholder="用户CODE"></el-input>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query @onAdd="handleAdd" @onQuery="loadDataList" :queryFilter="queryFilter"></button-group-query>
    <el-row>
      <template>
        <el-table border stripe style="width: 100%" @expand-change="expandChang" :expand-row-keys="expandRows"
                  row-key="id"
                  ref="tableData" :data="tableData">
          <el-table-column label="姓名" prop="username"></el-table-column>
          <el-table-column label="账号" prop="usercode"></el-table-column>
          <el-table-column label="性别" prop="sex" width="100">
            <template slot-scope="scope">
              <custom-select v-model="scope.row.sex" :datasource="selectSex" :value-key="scope.row.usercode"
                             placeholder="请选择性别" disabled>
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column label="出生日期" prop="birthDate">
            <template slot-scope="scope">
              <el-date-picker type="datetime" v-model="scope.row.birthDate" format="yyyy-MM-dd"
                              readonly></el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone"></el-table-column>
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
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
                  <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效"
                             @change="userInfoChange(scope.row)"></el-switch>
                </el-form-item>
                <el-form-item label="角色">
                  <custom-select v-model="scope.row.roleIds" :datasource="selectRole" :value-key="scope.row.usercode"
                                 @onchange="userInfoChange(scope.row)" placeholder="请选择角色" multiple>
                  </custom-select>
                </el-form-item>
                <el-form-item label="地址" prop="casAddress">
                  <el-popover trigger="hover" placement="right" @show="addressShow(scope.row,scope.$index)">
                    <div>{{scope.row.showAddress}}</div>
                    <el-cascader v-model="scope.row.casAddress" slot="reference" :ref="'cascaderAddr' + scope.$index"
                                 :options="citySources" :props="optionProps" disabled>
                    </el-cascader>
                  </el-popover>
                </el-form-item>
                <el-form-item label="描述" prop="descritpion">
                  <span>{{ scope.row.descritpion }}</span>
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
                                   :disInd="!scope.row.validInd"></button-group-option>
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
    LoadDataUserList,
    DeleteUserById,
    ProhibitUserById,
    LoadRoleIdsByUserId,
    UpdateUser
  } from "../../api/user/userMag";
  import {
    LoadSelectRoleList
  } from "../../api/sys/roleMag";
  import {
    LoadUserDataDictionary
  } from "../../api/sys/DataDictionaryMag";
  import {
    LoadCityData,
  } from "../../api/sys/CityMag";
  // ===== component start
  import CustomSelect from '../../components/common/CustomSelect.vue';
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue';
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
    mixins:[pageMixins],
    data() {
      return {
        queryFilter: {
          // 查询条件
          username: null,
          usercode: null
        },
        tableData: [], // 列表数据
        selectRole: [], // 角色下拉列表
        selectSex: [], // 性别下拉列表
        expandRows: [], // 打开的折叠拦
        citySources: [], // 省份下拉列表
        optionProps: {
          value: 'cityId',
          label: 'name',
          children: 'children'
        }
      }
    },
    created() {
      // 加载角色下拉列表
      LoadSelectRoleList().then(res => {
        this.selectRole = res.resData;
      });
      // 加载性别下拉列表
      LoadUserDataDictionary('sexType').then(res => {
        this.selectSex = res.resData;
      });
      // 请求列表数据
      this.loadDataList();
    },
    //每次激活时
    activated() {
      var _this = this;
      this.$bus.on('userInfo', function (data) {
        var index = _this.tableData.findIndex(f => f.id === data.id);
        if (index != -1) { // 更新
          this.tableData.splice(index, 1, data);
        } else { // 添加
          this.tableData.push(data);
        }
      }.bind(this));
    },
    methods: {
      // 获取列表数据
      loadDataList() {
        LoadDataUserList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 添加用户
      handleAdd() {
        this.$router.push({
          name: 'userUserEdit',
          query: {
            optionType: 'add',
            selectRole: JSON.stringify(this.selectRole),
            selectSex: JSON.stringify(this.selectSex)
          }
        });
      },
      // 修改用户
      handleEdit(row, index) {
        // 打开折叠栏，再点击编辑保存后，折叠栏有bug
        this.expandRows = [];
        row.password = null;
        this.$router.push({
          name: 'userUserEdit',
          query: {
            optionType: 'edit',
            selectRole: JSON.stringify(this.selectRole),
            selectSex: JSON.stringify(this.selectSex),
            userEdit: JSON.stringify(row)
          }
        });
      },
      // 禁用
      handleProhibit(row, index) {
        var _this = this;
        VueUtils.confirmInd(row.username, () => {
          ProhibitUserById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      // 删除
      handleDelete(row, index) {
        var _this = this;
        VueUtils.confirmDel(row.username, () => {
          DeleteUserById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      },
      // 打开折叠栏
      expandChang(row, expandedRows) {
        // 加载指定用户的角色信息
        if (row.roleIds.length < 1) {
          LoadRoleIdsByUserId(row.id).then(res => {
            var roleIds = res.resData.roleIds;
            Object.assign(row, {
              roleIds: roleIds
            });
          });
        }
        // 加载城市下拉列表
        if (this.citySources.length < 1) {
          LoadCityData().then(res => {
            this.citySources = res;
          });
        }
      },
      // 角色修改时，更新用户信息
      userInfoChange(row) {
        UpdateUser(row).then(res => {
          this.$message({
            type: 'success',
            message: '更新成功!'
          });
        });
      },
      // 加载城市下拉列表
      loadCitySources() {
        LoadCityData().then(res => {
          this.citySources = res;
        });
      },
      addressShow(row, index) {
        var addLabels = this.$refs['cascaderAddr' + index].currentLabels;
        Object.assign(row, {
          showAddress: addLabels.join("/")
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
