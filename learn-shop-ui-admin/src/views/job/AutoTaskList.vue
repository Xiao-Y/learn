<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件 </b> <i class="el-icon-search"></i>
          </template>
          <el-form ref="queryFilter" :inline="true" :model="queryFilter" label-width="130px" size="mini">
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
    <button-group-query :queryFilter="queryFilter" @onAdd="handleAdd" @onQuery="loadDataList"></button-group-query>

    <el-row>
      <el-table :data="tableData" border style="width:100%">
        <!--        <el-table-column label="ID" prop="id" width="40"></el-table-column>-->
        <el-table-column label="任务分组" prop="jobGroup">
          <template slot-scope="scope">
            <custom-select v-model="scope.row.jobGroup"
                           :datasource="systemModuleSelect"
                           disabled
                           placeholder="请选择任务分组">
            </custom-select>
          </template>
        </el-table-column>
        <el-table-column label="任务名称" prop="jobName"></el-table-column>
        <el-table-column label="Cron表达式" prop="cronExpression"></el-table-column>
        <el-table-column label="任务状态" prop="jobStatus" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.jobStatus === '1' ? 'success' : 'danger'"
              disable-transitions>{{ scope.row.jobStatus | jobStatusName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="任务描述" prop="description"></el-table-column>
        <el-table-column label="详细" type="expand" width="50">
          <template slot-scope="props">
            <el-form class="demo-table-expand" inline label-position="left" label-width="120px">
              <el-form-item label="创建人">
                <span>{{ props.row.creatorCode }}</span>
              </el-form-item>
              <el-form-item label="更新人">
                <span>{{ props.row.updaterCode }}</span>
              </el-form-item>
              <el-form-item label="创建时间">
                <el-date-picker v-model="props.row.createTime" readonly type="datetime"></el-date-picker>
              </el-form-item>
              <el-form-item label="更新时间">
                <el-date-picker v-model="props.row.updateTime" readonly type="datetime"></el-date-picker>
              </el-form-item>
              <!-- 1-springId，2-beanClass,3-http,4-mq -->
              <el-form-item label="运行类型">
                <custom-select v-model="props.row.classType"
                               :datasource="classTypeSelect"
                               disabled="true"
                               placeholder="请选择运行类型">
                </custom-select>
              </el-form-item>

              <template v-if="props.row.classType === '1' || props.row.classType === '2'">
                <el-form-item label="运行的类">
                  <el-input v-model="props.row.runClass" readonly/>
                </el-form-item>
                <el-form-item label="执行方法">
                  <span>{{ props.row.methodName }}</span>
                </el-form-item>
              </template>
              <el-form-item v-if="props.row.classType === '3'" label="HTTP URL">
                <span>{{ props.row.httpUrl }}</span>
              </el-form-item>
              <el-form-item v-if="props.row.classType === '4'" label="Routing Key">
                <span>{{ props.row.routingKey }}</span>
              </el-form-item>
              <el-form-item label="串行/并行">
                <el-switch v-model="props.row.isConcurrent" active-text="串行" active-value="0" disabled
                           inactive-text="并行" inactive-value="1"></el-switch>
              </el-form-item>
              <el-form-item label="启用/停止">
                <el-switch v-model="props.row.validInd" active-text="启用" disabled inactive-text="停止"></el-switch>
              </el-form-item>
              <el-form-item label="异常停止">
                <el-switch v-model="props.row.isExceptionStop" active-text="是" disabled inactive-text="否"></el-switch>
              </el-form-item>
              <el-form-item label="是否记录日志">
                <el-switch v-model="props.row.isSaveLog" active-text="是" disabled inactive-text="否"></el-switch>
              </el-form-item>
              <el-form-item label="是否发送消息">
                <custom-select v-model="props.row.isSendInfo"
                               :datasource="isSendInfo"
                               disabled
                               placeholder="请选择是否发送邮件">
                </custom-select>
              </el-form-item>
              <el-form-item label="发送方式">
                <custom-select v-model="props.row.sendType"
                               :datasource="sendType"
                               disabled
                               placeholder="请选择消息发送方式">
                </custom-select>
              </el-form-item>

              <template v-if="props.row.sendType == 'email'">
                <el-form-item label="邮件模板">
                  <custom-sel-mail-template v-model="props.row.templateId" :button-disabled="true"
                                            :input-read-only="true"></custom-sel-mail-template>
                </el-form-item>
                <el-form-item label="邮件接收人">
                  <el-input v-model="props.row.mailReceive" placeholder="多个接收人用英文封号分割开" readonly
                            type="textarea"></el-input>
                </el-form-item>
              </template>
              <template v-if="props.row.sendType == 'dingding'">
                <el-form-item label="钉钉模板">
                  <custom-sel-mail-template v-model="props.row.templateId" :button-disabled="true"
                                            :input-read-only="true"></custom-sel-mail-template>
                </el-form-item>
                <el-form-item label="钉钉群">
                  <el-input v-model="props.row.mailReceive" placeholder="多个钉钉群用英文封号分割开" readonly
                            type="textarea"></el-input>
                </el-form-item>
              </template>

              <el-form-item label="任务状态">
                <el-switch v-model="props.row.jobStatus" :disabled="!props.row.validInd" active-text="启用" active-value="1"
                           inactive-text="停止"
                           inactive-value="0"
                           @change="changeJobStatus(props.row)"></el-switch>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option :show-ind="false"
                                 @onDel="handleDelete(scope.row,scope.$index)"
                                 @onEdit="handleEdit(scope.row,scope.$index)"></button-group-option>
            <div style="float:left;margin-left:10px;">
              <el-tooltip :content="scope.row.validInd ? '停止' : '启用'" :open-delay="700" class="item" effect="dark"
                          placement="top-start">
                <el-button :type="scope.row.validInd? 'warning' : 'success'" size="mini"
                           @click="handleCust(scope.row,scope.$index)">
                  <i :class="scope.row.validInd ? 'el-icon-video-pause' :'el-icon-video-play'"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip :open-delay="700" class="item" content="立即执行" effect="dark" placement="top-start">
                <el-button size="mini" type="info" @click="handleImmediate(scope.row,scope.$index)">
                  <i class="el-icon-refresh"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip :open-delay="700" class="item" content="执行日志" effect="dark" placement="top-start">
                <el-button size="mini" type="success" @click="handleRunLog(scope.row,scope.$index)">
                  <i class="el-icon-document"></i>
                </el-button>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
    <el-dialog v-if="dialogTableVisible" :title="tableTitle" :visible.sync="dialogTableVisible" width="70%">
      <auto-task-log-list :autoTaskInfo="autoTaskInfo"></auto-task-log-list>
    </el-dialog>
  </div>
</template>


<script>
// ===== api start
import {
  LoadSysDataDictionary,
  LoadJobDataDictionary
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
import AutoTaskLogList from "./AutoTaskLogList.vue";
import CustomSelMailTemplate from '../../components/common/CustomSelMailTemplate.vue';

// ===== 工具类 start
import VueUtils from "../../utils/vueUtils";
import pageMixins from "../../utils/pageMixins";

export default {
  name: "jobAutoTaskListIndex",
  components: {
    AutoTaskLogList,
    ButtonGroupOption,
    ButtonGroupQuery,
    CustomPage,
    CustomSelect,
    CustomSelMailTemplate
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
      isSendInfo: [],// 是否发送消息下拉数据源
      sendType: [],// 消息发送方式下拉数据源
      classTypeSelect: [],// 运行类型下拉数据源
      dialogTableVisible: false,// 打开日志窗口
      tableTitle: '',// 执行日志
      autoTaskInfo: {},// 用于打开日志
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
    LoadSysDataDictionary('systemModule').then(res => {
      this.systemModuleSelect = res.resData;
    });
    LoadJobDataDictionary('isSendInfo').then(res => {
      this.isSendInfo = res.resData;
    });
    LoadJobDataDictionary('classType').then(res => {
      this.classTypeSelect = res.resData;
    });
    LoadJobDataDictionary('sendType').then(res => {
      this.sendType = res.resData;
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
        this.tableData = data.tableData;
        this.queryFilter.recordCount = data.recordCount;
        this.queryFilter.totalPages = data.totalPages;
      });
    },
    // 启用自动任务
    handleStart(row, index) {
      var _this = this;
      UpdateJobInd(row.id, true).then(res => {
        row.validInd = true;
        row.jobStatus = "1";
        _this.$message({
          type: 'success',
          message: '启用成功!'
        });
      });
    },
    // 停止自动任务
    handleProhibit(row, index) {
      var _this = this;
      UpdateJobInd(row.id, false).then(res => {
        row.validInd = false;
        row.jobStatus = "0";
        _this.$message({
          type: 'success',
          message: '停止成功!'
        });
      });
    },
    // 添加自动任务
    handleAdd() {
      this.$router.push({
        name: 'jobAutoTaskEdit',
        query: {
          optionType: 'add',
          systemModuleSelect: JSON.stringify(this.systemModuleSelect),
          isSendInfo: JSON.stringify(this.isSendInfo),
          classTypeSelect: JSON.stringify(this.classTypeSelect),
          sendType: JSON.stringify(this.sendType)
        }
      });
    },
    // 修改自动任务
    handleEdit(row, index) {
      Object.assign(row, {
        oldJobGroup: row.jobGroup,
        oldJobName: row.jobName
      });
      this.$router.push({
        name: 'jobAutoTaskEdit',
        query: {
          optionId: row.id,
          optionType: 'edit',
          autoTaskEdit: JSON.stringify(row),
          systemModuleSelect: JSON.stringify(this.systemModuleSelect),
          isSendInfo: JSON.stringify(this.isSendInfo),
          classTypeSelect: JSON.stringify(this.classTypeSelect),
          sendType: JSON.stringify(this.sendType)
        }
      });
    },
    // 删除自动任务
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
    // 修改任务状态
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
    // 启用/停止
    handleCust(row, index) {
      console.log(row.validInd)
      if (row.validInd) {
        this.handleProhibit(row, index);
      } else {
        this.handleStart(row, index);
      }
    },
    // 立即执行
    handleImmediate(row, index) {
      ImmediateExecutionTask(row.jobName, row.jobGroup).then(res => {
        this.$message.success("成功加入执行队列");
      });
    },
    // 执行日志
    handleRunLog(row, index) {
      var jobGroupName = this.systemModuleSelect.find(f => f.fieldValue === row.jobGroup).fieldDisplay;
      this.tableTitle = "JobGroup:[" + jobGroupName + "],JobName:[" + row.jobName + " ] 的执行日志";
      Object.assign(this.autoTaskInfo, row);
      Object.assign(this.autoTaskInfo, {systemModuleSelect: this.systemModuleSelect});
      this.dialogTableVisible = true;
      this.$bus.emit("busLog");
    }
  },
  filters: {
    jobStatusName(jobStatus) {
      return jobStatus === '1' ? '启用' : '停止';
    }
  }
}
</script>

<style scoped>
/*!*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*!*/
/*::-webkit-scrollbar {*/
/*  width: 3px;*/
/*  !*滚动条宽度*!*/
/*  height: 3px;*/
/*  !*滚动条高度*!*/
/*}*/

/*!*定义滚动条轨道 内阴影+圆角*!*/
/*::-webkit-scrollbar-track {*/
/*  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);*/
/*  border-radius: 10px;*/
/*  !*滚动条的背景区域的圆角*!*/
/*  background-color: white;*/
/*  !*滚动条的背景颜色*!*/
/*}*/

/*!*定义滑块 内阴影+圆角*!*/
/*::-webkit-scrollbar-thumb {*/
/*  border-radius: 10px;*/
/*  !*滚动条的圆角*!*/
/*  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);*/
/*  background-color: #2e363f;*/
/*  !*滚动条的背景颜色*!*/
/*}*/

/*.demo-table-expand {*/
/*  font-size: 0;*/
/*}*/

/*.demo-table-expand label {*/
/*  width: 90px;*/
/*  color: #99a9bf;*/
/*}*/

/*.demo-table-expand .el-form-item {*/
/*  margin-right: 0;*/
/*  margin-bottom: 0;*/
/*  width: 50%;*/
/*}*/
</style>
