<template>
  <div>
    <div class="ms-doc">
      <div class="ms-doc-title">任务详情列表</div>
      <article>
        <!-- 任务组信息 -->
        <el-card shadow="never" style="margin-bottom: 20px;">
          <el-form :model="groupInfo" label-width="100px">
            <el-form-item label="任务组编号">
              <el-input v-model="groupInfo.groupNo" disabled></el-input>
            </el-form-item>
          </el-form>
        </el-card>

        <!-- 数据表格 -->
        <el-table :data="taskDetailList" style="width: 100%" v-loading="loading">
          <el-table-column prop="id" label="任务详情ID" width="120"></el-table-column>
          <el-table-column prop="taskNo" label="任务编号" width="180"></el-table-column>
          <el-table-column prop="taskType" label="任务类型"></el-table-column>
          <el-table-column prop="executeStatus" label="执行状态" width="120">
            <template slot-scope="scope">
              <el-tag :type="getStatusType(scope.row.executeStatus)">{{ getStatusText(scope.row.executeStatus) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="msg" label="执行信息" show-overflow-tooltip></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="warning" @click="retryTask(scope.row.id)" v-if="scope.row.executeStatus === '3'">重试</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </article>
    </div>
  </div>
</template>

<script>
import { queryTaskDetailList, retryTaskDetail } from '../../../api/task/taskCenterApi';

export default {
  data() {
    return {
      groupNo: '',
      groupInfo: {
        groupNo: ''
      },
      taskDetailList: [],
      loading: false,
      pageNum: 1,
      pageSize: 20,
      total: 0
    };
  },
  activated() {
    this.groupNo = this.$route.query.groupNo;
    this.groupInfo.groupNo = this.groupNo;
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      queryTaskDetailList({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        groupNo: this.groupNo
      }).then(res => {
        this.taskDetailList = res.resData.records;
        this.total = res.resData.total;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.loadData();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadData();
    },
    getStatusType(status) {
      switch (status) {
        case '0':
          return 'info';
        case '1':
          return 'warning';
        case '2':
          return 'success';
        case '3':
          return 'danger';
        default:
          return '';
      }
    },
    getStatusText(status) {
      switch (status) {
        case '0':
          return '待执行';
        case '1':
          return '执行中';
        case '2':
          return '执行完成';
        case '3':
          return '执行失败';
        default:
          return '';
      }
    },
    retryTask(taskDetailId) {
      this.$confirm('确定要重试该任务吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        retryTaskDetail(taskDetailId).then(res => {
          this.$message({
            type: 'success',
            message: res.resData
          });
          // 重新加载数据
          this.loadData();
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重试'
        });
      });
    },
    onReturn() {
      this.$router.back(-1);
    }
  }
};
</script>

<style scoped>
.ms-doc-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
}
</style>
