<template>
  <div>
    <el-table border style="width: 100%" ref="commentListRef" :data="tableData" row-key="id">
      <el-table-column label="批注CODE" prop="userId"></el-table-column>
      <el-table-column label="批注时间" prop="time">
        <template slot-scope="scope">
          <el-date-picker type="datetime" v-model="scope.row.time" readonly></el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="批注信息" prop="message"></el-table-column>
      <el-table-column type="expand" label="详细" width="50">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
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
