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
    <button-group-query @onAdd="handleAdd" @onQuery="loadDataList" :queryFilter="queryFilter"
                        :show-add="!selectView"></button-group-query>
    <el-row>
      <template>
        <el-table border style="width: 100%" ref="mailTemplateListRef"
                  :data="tableData"
                  row-key="id">
          <el-table-column label="选择" width="35" v-if="selectView">
            <template slot-scope="scope">
              <el-radio :label="scope.row.id" @change="handleSelect(scope.row)"></el-radio>
            </template>
          </el-table-column>
          <el-table-column label="模板CODE" prop="mailCode" width="210"></el-table-column>
          <el-table-column label="邮件类型" prop="mailType">
            <template slot-scope="scope">
              <custom-select v-model="scope.row.mailType"
                             :datasource="mailTypeSelect"
                             :value-key="scope.row.mailCode"
                             disabled placeholder="请选择邮件类型">
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column label="数据来源" prop="dataSources">
            <template slot-scope="scope">
              <custom-select v-model="scope.row.dataSources"
                             :datasource="dataSourcesSelect"
                             :value-key="scope.row.mailCode"
                             disabled placeholder="请选择数据来源">
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column label="模板描述" prop="descritpion"></el-table-column>
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
                  <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
                </el-form-item>
                <el-form-item label="收件人邮箱">
                  <el-input type="textarea" v-model="scope.row.toEmails" disabled></el-input>
                </el-form-item>
                <el-form-item label="邮件主题">
                  <el-input type="textarea" v-model="scope.row.subject" disabled></el-input>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200" v-if="!selectView">
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
    LoadDataMailTemplateList,
    ProhibitMailTemplateById,
    DeleteMailTemplateById
  } from "../../api/sys/mailTemplateMag";
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
    props: {
      // 是否显示选择框 和操作按钮
      selectView: {
        type: Boolean,
        default: false
      },
      mailCode: {
        trype: String,
        default: null
      }
    },
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
      // 使用邮件模板时
      if (this.selectView && this.mailCode && this.mailCode != '') {
        this.queryFilter.mailCode = this.mailCode;
      }
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
      this.$bus.once('mailTemplateInfo', function (data) {
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
        LoadDataMailTemplateList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 添加权限
      handleAdd() {
        this.$router.push({
          name: 'sysMailTemplateEdit',
          query: {
            optionType: 'add',
            mailTypeSelect: JSON.stringify(this.mailTypeSelect),
            dataSourcesSelect: JSON.stringify(this.dataSourcesSelect)
          }
        });
      },
      handleEdit(row, index) {
        this.$router.push({
          name: 'sysMailTemplateEdit',
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
          DeleteMailTemplateById(row.id).then(res => {
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
          ProhibitMailTemplateById(row.id).then(res => {
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
