<template>
  <div>
    <div class="ms-doc">
      <div class="ms-doc-title">任务进度详情</div>
      <article>
        <el-card shadow="never">
          <el-form :model="taskProgress" label-width="120px">
            <el-form-item label="任务组编号">
              <el-input v-model="taskProgress.groupNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="任务进度">
              <el-progress :percentage="parseInt(taskProgress.progress)" :format="formatProgress"></el-progress>
              <div style="margin-top: 10px;">{{ taskProgress.progress }}</div>
            </el-form-item>
            <el-form-item label="任务总数">
              <el-input v-model="taskProgress.totalTaskSize" disabled></el-input>
            </el-form-item>
            <el-form-item label="已完成数">
              <el-input v-model="taskProgress.finishedSize" disabled></el-input>
            </el-form-item>
            <el-form-item label="成功数">
              <el-input v-model="taskProgress.successSize" disabled></el-input>
            </el-form-item>
            <el-form-item label="执行状态">
              <el-tag :type="getStatusType(taskProgress.executeStatus)">{{ getStatusText(taskProgress.executeStatus) }}</el-tag>
            </el-form-item>
            <el-form-item label="执行信息">
              <el-input v-model="taskProgress.msg" type="textarea" disabled></el-input>
            </el-form-item>
          </el-form>
          <el-form-item>
            <el-button type="primary" @click="onReturn">返回</el-button>
          </el-form-item>
        </el-card>
      </article>
    </div>
  </div>
</template>

<script>
import { queryTaskProgress } from '../../../api/task/taskCenterApi';

export default {
  data() {
    return {
      taskId: '',
      taskProgress: {
        groupNo: '',
        progress: '0%',
        totalTaskSize: 0,
        finishedSize: 0,
        successSize: 0,
        executeStatus: '',
        msg: ''
      },
      loading: false
    };
  },
  activated() {
    this.taskId = this.$route.query.taskId;
    if (this.taskId) {
      this.loadTaskProgress();
    }
  },
  methods: {
    loadTaskProgress() {
      this.loading = true;
      queryTaskProgress(this.taskId).then(res => {
        this.taskProgress = res.resData;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    formatProgress(percentage) {
      return `${percentage}%`;
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
