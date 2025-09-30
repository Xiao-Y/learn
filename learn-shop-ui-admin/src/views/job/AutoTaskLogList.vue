<template>
  <div>
    <el-row>
      <el-table :data="tableData" border stripe>
        <el-table-column label="任务分组" prop="jobGroup">
          <template slot-scope="scope">
            <custom-select v-model="scope.row.jobGroup"
                           :datasource="autoTaskInfo.systemModuleSelect"
                           placeholder="请选择任务分组"
                           disabled>
            </custom-select>
          </template>
        </el-table-column>
        <el-table-column label="任务名称" prop="jobName"></el-table-column>
        <el-table-column label="运行时间" prop="runTime"></el-table-column>
        <el-table-column label="是否成功" prop="isSuccess" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.isSuccess ? 'success' : 'danger'"
              disable-transitions>{{scope.row.isSuccess | isSuccessName}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="执行时间" prop="runTime">
          <template slot-scope="scope">
            <el-date-picker size="mini" type="datetime" v-model="scope.row.updateTime" readonly></el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="60">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <el-tooltip class="item" effect="dark" content="执行日志" placement="top-start" :open-delay="1500">
              <el-button type="success" size="mini" @click="handleExceInfo(scope.row,scope.$index)">
                <i class="el-icon-document"></i>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
    <el-dialog append-to-body :title="tableTitle" :visible.sync="dialogDetailVisible" v-if="dialogDetailVisible">
      <span>{{dateilInfo}}</span>
    </el-dialog>
  </div>
</template>


<script>
  // ===== api start
  import {
    LoadDataJobLogList
  } from "../../api/job/jobMag";
  // ===== component start
  import CustomPage from '../../components/common/CustomPage.vue';
  import CustomSelect from '../../components/common/CustomSelect.vue';

  // ===== 工具类 start
  import pageMixins from "../../utils/pageMixins";

  export default {
    components: {
      CustomPage,
      CustomSelect
    },
    mixins: [pageMixins],
    props: {
      autoTaskInfo: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        // 查询条件
        queryFilter: {
          jobId: null
        },
        tableData: [],
        systemModuleSelect: [],// 系统模块的下拉数据源
        dialogDetailVisible: false,// 打开日志窗口
        tableTitle: '详细日志信息',// 执行日志
        dateilInfo: '',// 详细日志
      }
    },
    created() {
      this.queryFilter.jobId = this.autoTaskInfo.id;
      // 请数据殂
      this.loadDataList();
    },
    methods: {
      // 请服务器数据（获取自动任务列表数据）
      loadDataList() {
        LoadDataJobLogList(this.queryFilter).then(res => {
          var data = res.resData;
          // 填充数据
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 执行详细日志
      handleExceInfo(row, index) {
        this.dateilInfo = '';
        if (!row.info || row.info === '') {
          this.$message.info("没有详细日志");
          return;
        }
        this.dateilInfo = row.info;
        this.dialogDetailVisible = true;
      }
    },
    filters: {
      isSuccessName(isSuccess) {
        return isSuccess ? '成功' : '失败';
      }
    }
  }
</script>

