<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :model="queryFilter" ref="queryFilter" :inline="true" label-width="130px" size="mini">
            <el-form-item label="任务分组" prop="jobGroup">
              <el-input v-model="queryFilter.jobGroup" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务名称" prop="jobName">
              <el-input v-model="queryFilter.jobName" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务状态" prop="jobStatus">
              <el-input v-model="queryFilter.jobStatus" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="BeanClass" prop="beanClass">
              <el-input v-model="queryFilter.beanClass" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="SpringId" prop="springId">
              <el-input v-model="queryFilter.springId" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="执行方法" prop="methodName">
              <el-input v-model="queryFilter.methodName" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query @onAdd="handleAdd" @onQuery="loadDataList" :queryFilter="queryFilter"></button-group-query>

    <el-row>
      <el-table :data="tableData" border style="width:100%">
        <!--        <el-table-column label="ID" prop="id" width="40"></el-table-column>-->
        <el-table-column label="任务分组" prop="jobGroup">
          <template slot-scope="scope">
            <custom-select v-model="scope.row.jobGroup"
                           :datasource="systemModuleSelect"
                           placeholder="请选择任务分组"
                           disabled>
            </custom-select>
          </template>
        </el-table-column>
        <el-table-column label="任务名称" prop="jobName"></el-table-column>
        <el-table-column label="Cron表达式" prop="cronExpression"></el-table-column>
        <el-table-column label="任务状态" prop="jobStatus" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.jobStatus === '1' ? 'success' : 'danger'"
              disable-transitions>{{scope.row.jobStatus | jobStatusName}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="任务描述" prop="description"></el-table-column>
        <el-table-column type="expand" label="详细" width="50">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
              <el-form-item label="创建人">
                <span>{{ props.row.creatorCode }}</span>
              </el-form-item>
              <el-form-item label="更新人">
                <span>{{ props.row.updaterCode }}</span>
              </el-form-item>
              <el-form-item label="创建时间">
                <el-date-picker type="datetime" v-model="props.row.createTime" readonly></el-date-picker>
              </el-form-item>
              <el-form-item label="更新时间">
                <el-date-picker type="datetime" v-model="props.row.updateTime" readonly></el-date-picker>
              </el-form-item>
              <el-form-item label="BeanClass">
                <span>{{ props.row.beanClass }}</span>
              </el-form-item>
              <el-form-item label="SpringId">
                <span>{{ props.row.springId }}</span>
              </el-form-item>
              <el-form-item label="执行方法">
                <span>{{ props.row.methodName }}</span>
              </el-form-item>
              <el-form-item label="串行/并行">
                <el-switch v-model="props.row.isConcurrent" active-text="串行" active-value="0" inactive-text="并行"
                           inactive-value="1" disabled></el-switch>
              </el-form-item>
              <el-form-item label="有效状态">
                <el-switch v-model="props.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
              </el-form-item>
              <el-form-item label="异常停止">
                <el-switch v-model="props.row.isExceptionStop" active-text="是" inactive-text="否" disabled></el-switch>
              </el-form-item>
              <el-form-item label="是否记录日志">
                <el-switch v-model="props.row.isSaveLog" active-text="是" inactive-text="否" disabled></el-switch>
              </el-form-item>
              <el-form-item label="任务状态">
                <el-switch v-model="props.row.jobStatus" active-text="启用" active-value="1" inactive-text="停止"
                           inactive-value="0"
                           :disabled="!props.row.validInd"
                           @change="changeJobStatus(props.row)"></el-switch>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                 @onEdit="handleEdit(scope.row,scope.$index)"
                                 @onInd="handleProhibit(scope.row,scope.$index)"
                                 :disInd="!scope.row.validInd"></button-group-option>
            <div style="float:left;margin-left:10px;">
              <el-tooltip class="item" effect="dark" content="立即执行" placement="top-start" :open-delay="1500">
                <el-button type="success" size="mini" @click="handleImmediate(scope.row,scope.$index)"
                           :disabled="!scope.row.validInd || scope.row.jobStatus == '0'">
                  <i class="el-icon-success"></i>
                </el-button>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
  </div>
</template>


<script>
  // ===== api start
  import {
    LoadSysDataDictionary
  } from "../../api/sys/DataDictionaryMag";
  import {
    LoadDataJobList,
    UpdateJobStatus,
    DeleteAutoTask,
    UpdateJobInd,
    ImmediateExecutionTask
  } from "../../api/job/jobMag";
  // ===== component start
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue';
  import CustomSelect from '../../components/common/CustomSelect.vue';

  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  export default {
    components: {
      ButtonGroupOption,
      ButtonGroupQuery,
      CustomPage,
      CustomSelect
    },
    mixins: [pageMixins],
    data() {
      return {
        queryFilter: {
          // 查询条件
          jobGroup: null,
          jobName: null,
          jobStatus: null,
          beanClass: null,
          springId: null,
          methodName: null
        },
        tableData: [],
        activeNames: ['1'],
        systemModuleSelect: [],// 系统模块的下拉数据源
      }
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('autoTaskInfo', function (data) {
        var index = this.tableData.findIndex(f => f.id === data.id);
        if (index != -1) { // 更新
          this.tableData.splice(index, 1, data);
        } else { // 添加
          this.tableData.push(data);
        }
      }.bind(this));
    },
    created() {
      // 加载系统模块的下拉
      LoadSysDataDictionary('SystemModule').then(res => {
        this.systemModuleSelect = res.resData;
      });
      // 请数据殂
      this.loadDataList();
    },
    methods: {
      // 请服务器数据（获取自动任务列表数据）
      loadDataList() {
        LoadDataJobList(this.queryFilter).then(res => {
          var data = res.resData;
          // 填充数据
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      handleProhibit(row, index) {
        var _this = this;
        VueUtils.confirmInd(row.jobName, () => {
          UpdateJobInd(row.id, false).then(res => {
            row.validInd = false;
            row.jobStatus = "0";
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      handleAdd() {
        this.$router.push({
          name: 'jobAutoTaskEdit',
          query: {
            optionType: 'add',
            systemModuleSelect: JSON.stringify(this.systemModuleSelect)
          }
        });
      },
      handleEdit(row, index) {
        Object.assign(row, {
          oldJobGroup: row.jobGroup,
          oldJobName: row.jobName
        });
        this.$router.push({
          name: 'jobAutoTaskEdit',
          query: {
            optionType: 'edit',
            autoTaskEdit: JSON.stringify(row),
            systemModuleSelect: JSON.stringify(this.systemModuleSelect)
          }
        });
      },
      handleDelete(row, index) {
        var _this = this;
        VueUtils.confirmDel(row.jobName, () => {
          DeleteAutoTask(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      },
      changeJobStatus(row) {
        if (!row.cronExpression || row.cronExpression === '') {
          this.$message.error("Cron 表达式不能为空");
          row.jobStatus = row.jobStatus === "0" ? "1" : "0";
          return;
        }
        UpdateJobStatus(row.id, row.jobStatus).then(res => {
          var message = "自动任务启动成功";
          if (row.jobStatus === "0") {
            message = "自动任务停止成功";
          }
          this.$message.success(message);
        }).catch(err => {
          row.jobStatus = row.jobStatus === "0" ? "1" : "0";
        });
      },
      handleImmediate(row, index) {
        ImmediateExecutionTask(row.jobName, row.jobGroup).then(res => {
          this.$message.success("成功加入执行队列");
        });
      }
    },
    filters: {
      jobStatusName(jobStatus) {
//        row.tag = row.jobStatus;
//        console.info("row.tag", row.tag);
        return jobStatus === '1' ? '启用' : '停止';
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
