<template>
  <div>
    <div class="ms-doc">
      <div class="ms-doc-title">任务组管理</div>
      <article>
        <!-- 搜索条件 -->
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-form-item label="任务类型">
            <el-input v-model="searchForm.taskType" placeholder="请输入任务类型"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态">
              <el-option label="全部" value=""></el-option>
              <el-option label="待执行" value="0"></el-option>
              <el-option label="执行中" value="1"></el-option>
              <el-option label="执行完成" value="2"></el-option>
              <el-option label="执行失败" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="search">查询</el-button>
            <el-button @click="reset">重置</el-button>
          </el-form-item>
        </el-form>

        <!-- 数据表格 -->
        <el-table :data="taskGroupList" style="width: 100%" v-loading="loading">
          <el-table-column prop="id" label="任务ID" width="100"></el-table-column>
          <el-table-column prop="groupNo" label="任务组编号" width="180"></el-table-column>
          <el-table-column prop="taskType" label="任务类型"></el-table-column>
          <el-table-column prop="taskSize" label="任务总数" width="100"></el-table-column>
          <el-table-column prop="executeEndSize" label="已完成数" width="100"></el-table-column>
          <el-table-column prop="successSize" label="成功数" width="100"></el-table-column>
          <el-table-column prop="executeStatus" label="执行状态" width="120">
            <template slot-scope="scope">
              <el-tag :type="getStatusType(scope.row.executeStatus)">{{ getStatusText(scope.row.executeStatus) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
          <el-table-column label="操作" width="200" fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="viewProgress(scope.row.id)">查看进度</el-button>
              <el-button size="mini" type="info" @click="viewDetail(scope.row.groupNo)">查看详情</el-button>
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
import { queryTaskGroupList } from '../../api/task/taskCenterApi';

export default {
  data() {
    return {
      searchForm: {
        taskType: '',
        status: ''
      },
      taskGroupList: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      queryTaskGroupList({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        taskType: this.searchForm.taskType,
        status: this.searchForm.status
      }).then(res => {
        this.taskGroupList = res.resData.records;
        this.total = res.resData.total;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    search() {
      this.pageNum = 1;
      this.loadData();
    },
    reset() {
      this.searchForm = {
        taskType: '',
        status: ''
      };
      this.pageNum = 1;
      this.loadData();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.loadData();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadData();
    },
    viewProgress(taskId) {
      this.$router.push({
        path: '/task/taskGroupList/taskProgress',
        query: { taskId: taskId }
      });
    },
    viewDetail(groupNo) {
      this.$router.push({
        path: '/task/taskGroupList/taskDetail',
        query: { groupNo: groupNo }
      });
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
.demo-form-inline {
  margin-bottom: 20px;
}
</style>
