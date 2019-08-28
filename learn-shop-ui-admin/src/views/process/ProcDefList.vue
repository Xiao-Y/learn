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
              <el-form-item label="邮件CODE" prop="mailCode">
                <el-input v-model="queryFilter.mailCode" placeholder="邮件CODE"></el-input>
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
        <el-table border style="width: 100%" ref="procDefListRef"
                  :data="tableData"
                  row-key="id">
          <el-table-column label="ID" prop="id" width="210"></el-table-column>
          <el-table-column label="KEY" prop="key"></el-table-column>
          <el-table-column label="分组" prop="category"></el-table-column>
          <el-table-column label="名称" prop="name"></el-table-column>
          <el-table-column label="版本" prop="version"></el-table-column>
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
                <el-form-item label="资源路径">
                  <span>{{ scope.row.resourceName }}</span>
                </el-form-item>
                <el-form-item label="图片路径">
                  <span>{{ scope.row.diagramResourceName }}</span>
                </el-form-item>
                <el-form-item label="描述">
                  <span>{{ scope.row.description }}</span>
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
    LoadDataProcDefList,
    ProhibitProcDefById,
    DeleteProcDefById
  } from "../../api/proc/procDefMag";
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
    mixins: [pageMixins],
    data() {
      return {
        queryFilter: {
          // 查询条件
          mailCode: null
        },
        tableData: [], // 列表数据源
        mailTypeSelect: [],// 邮件类型的下拉数据源
        dataSourcesSelect: [],// 数据来源的下拉数据源
      }
    },
    created() {
      // 加载邮件类型的下拉
      LoadSysDataDictionary('mailType').then(res => {
        this.mailTypeSelect = res.resData;
      });
      // 加载数据来源的下拉
      LoadSysDataDictionary('dataSourcesType').then(res => {
        this.dataSourcesSelect = res.resData;
      });
      // 请数据殂
      this.loadDataList();
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('procDefInfo', function (data) {
        var index = this.tableData.findIndex(f => f.id === data.id);
        if (index != -1) { // 更新
          this.tableData.splice(index, 1, data);
        } else { // 添加
          this.tableData.push(data);
        }
      }.bind(this));
    },
    methods: {
      // 获取权限列表数据
      loadDataList() {
        LoadDataProcDefList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 添加权限
      handleAdd() {
        this.$router.push({
          name: 'sysProcDefEdit',
          query: {
            optionType: 'add',
            mailTypeSelect: JSON.stringify(this.mailTypeSelect),
            dataSourcesSelect: JSON.stringify(this.dataSourcesSelect)
          }
        });
      },
      handleEdit(row, index) {
        this.$router.push({
          name: 'sysProcDefEdit',
          query: {
            optionType: 'edit',
            id: row.id,
            mailTypeSelect: JSON.stringify(this.mailTypeSelect),
            dataSourcesSelect: JSON.stringify(this.dataSourcesSelect)
          }
        });
      },
      handleDelete(row, index) {
        var _this = this;

        VueUtils.confirmDel(row.mailCode, () => {
          DeleteProcDefById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      },
      handleProhibit(row, index) {
        var _this = this;

        VueUtils.confirmInd(row.mailCode, () => {
          ProhibitProcDefById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      }
    }
  }
</script>

<style scoped>
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
