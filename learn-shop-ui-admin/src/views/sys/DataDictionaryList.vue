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
              <el-form-item label="字段显示" prop="fieldDisplay">
                <el-input v-model="queryFilter.fieldDisplay" placeholder="字段显示"></el-input>
              </el-form-item>
              <el-form-item label="字典类型" prop="fieldType" class="form-custom-select">
                <custom-select v-model="queryFilter.fieldType"
                               :datasource="fieldTypeSelect"
                               placeholder="请选择数据字典类型">
                </custom-select>
              </el-form-item>
              <el-form-item label="系统模块" prop="systemModule" class="form-custom-select">
                <custom-select v-model="queryFilter.systemModule"
                               :datasource="systemModuleSelect"
                               placeholder="请选择数据字典系统模块">
                </custom-select>
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
        <el-table border stripe ref="dataDictionaryListRef"
                  :data="tableData"
                  row-key="id">
          <el-table-column label="系统模块" prop="systemModule"></el-table-column>
          <el-table-column label="字典类型" prop="fieldType"></el-table-column>
          <el-table-column label="字段VALUE" prop="fieldValue"></el-table-column>
          <el-table-column label="字段显示" prop="fieldDisplay"></el-table-column>
          <el-table-column label="字段排序" prop="fieldOrder"></el-table-column>
          <el-table-column label="字段说明" prop="description"></el-table-column>
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
    LoadDictionaryList,
    FindFieldType,
    FindSysModule,
    DelById,
    ProhibitById
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
    name: "sysDataDictionaryList",
    // 引入组件
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
          fieldDisplay: null,
          fieldType: null,
          systemModule: null,
        },
        tableData: [], // 列表数据源
        fieldTypeSelect: [],// 数据字典类型的下拉数据源
        systemModuleSelect: [],// 数据字典系统模块的下拉数据源
      }
    },
    created() {
      // 加载数据字典类型的下拉
      FindFieldType().then(res => {
        this.fieldTypeSelect = res.resData;
      });
      // 加载数据字典类型的下拉
      FindSysModule().then(res => {
        this.systemModuleSelect = res.resData;
      });
      // 请数据殂
      this.loadDataList();
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('dataDictionaryInfo', function (data) {
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
        LoadDictionaryList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 添加权限
      handleAdd() {
        this.$router.push({
          name: 'sysDataDictionaryEdit',
          query: {
            optionType: 'add',
            fieldTypeSelect: JSON.stringify(this.fieldTypeSelect),
            systemModuleSelect: JSON.stringify(this.systemModuleSelect)
          }
        });
      },
      handleEdit(row, index) {
        this.$router.push({
          name: 'sysDataDictionaryEdit',
          query: {
            optionType: 'edit',
            id: row.id,
            dataDictionaryEdit: JSON.stringify(row),
            fieldTypeSelect: JSON.stringify(this.fieldTypeSelect),
            systemModuleSelect: JSON.stringify(this.systemModuleSelect)
          }
        });
      },
      handleDelete(row, index) {
        var _this = this;

        VueUtils.confirmDel(row.fieldDisplay, () => {
          DelById(row.id).then(res => {
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

        VueUtils.confirmInd(row.fieldDisplay, () => {
          ProhibitById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      // 勾选单个后，发送通知
      handleSelect(row) {
        this.$emit("change", row);
      }
    }
  }
</script>
