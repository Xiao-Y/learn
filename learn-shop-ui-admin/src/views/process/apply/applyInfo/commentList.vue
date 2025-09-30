<template>
  <div>
    <el-table border stripe ref="commentListRef" :data="tableData" row-key="id">
      <el-table-column label="批注CODE" prop="userId"></el-table-column>
      <el-table-column label="批注时间" prop="time">
        <template slot-scope="scope">
          <el-date-picker type="datetime" v-model="scope.row.time" readonly></el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="批注信息" prop="message"></el-table-column>
      <el-table-column type="expand" label="详细" width="50">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="ms-table-expand" label-width="120px">
            <el-form-item label="批注ID">
              <span>{{ scope.row.id }}</span>
            </el-form-item>
            <el-form-item label="任务ID">
              <span>{{ scope.row.taskId }}</span>
            </el-form-item>
            <el-form-item label="类型">
              <span>{{ scope.row.type }}</span>
            </el-form-item>
            <el-form-item label="动作">
              <span>{{ scope.row.action }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {
    FindCommentListByProcInstId,
  } from "../../../../api/proc/applyMag";

  export default {
    name: "commentList",
    data() {
      return {
        tableData: [], // 列表数据源
      }
    },
    created() {
      var procInstId = this.$route.query.procInstId;
      if (procInstId) {
        FindCommentListByProcInstId(procInstId).then(res => {
          this.tableData = res.resData;
        });
      }
    },
    methods: {}
  }
</script>
