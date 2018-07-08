<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-setting"></i>自动任务</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <template>
      <el-collapse>
        <el-collapse-item title="查询条件" name="1">
          <el-form :model="scheduleJobFilter" ref="scheduleJobFilter" :inline="true" label-width="130px" size="mini">
            <el-form-item label="任务分组" prop="jobGroup">
              <el-input v-model="scheduleJobFilter.jobGroup" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务名称" prop="jobName">
              <el-input v-model="scheduleJobFilter.jobName" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务状态" prop="jobStatus">
              <el-input v-model="scheduleJobFilter.jobStatus" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="BeanClass" prop="beanClass">
              <el-input v-model="scheduleJobFilter.beanClass" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="SpringId" prop="springId">
              <el-input v-model="scheduleJobFilter.springId" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="执行方法" prop="methodName">
              <el-input v-model="scheduleJobFilter.methodName" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onQuery">查询</el-button>
              <el-button type="warning" @click="resetForm('scheduleJobFilter')">清除</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
      <el-table :data="tableData" border style="width:100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="任务分组">
                <span>{{ props.row.jobGroup }}</span>
              </el-form-item>
              <el-form-item label="Cron表达式">
                <span>{{ props.row.cronExpression }}</span>
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
              <el-form-item label="任务是否有状态">
                <el-switch v-model="props.row.isConcurrent" active-text="串行" active-value="1" inactive-text="并行"
                           inactive-value="0" disabled></el-switch>
              </el-form-item>
              <el-form-item label="有效状态">
                <el-switch v-model="props.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
              </el-form-item>
              <el-form-item label="任务状态">
                <el-switch v-model="props.row.jobStatus" active-text="启用" active-value="1" inactive-text="停止"
                           inactive-value="0"></el-switch>
              </el-form-item>
              <el-form-item label="创建时间">
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
              <el-form-item label="更新时间">
                <span>{{ props.row.updateTime }}</span>
              </el-form-item>
              <el-form-item label="创建人">
                <span>{{ props.row.creatorCode }}</span>
              </el-form-item>
              <el-form-item label="更新人">
                <span>{{ props.row.updaterCode }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="ID" prop="id" width="40"></el-table-column>
        <el-table-column label="任务分组" prop="jobGroup"></el-table-column>
        <el-table-column label="任务名称" prop="jobName"></el-table-column>
        <el-table-column label="任务状态" prop="jobStatus" width="80"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <template style="margin-bottom: 20px">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage1"
        layout="total, prev, pager, next"
        :page-size="100"
        :total="1000">
      </el-pagination>
    </template>
  </div>
</template>


<script>
  import {requestDataList} from "@/api/job/jobMag";

  export default {
    data() {
      return {
        scheduleJobFilter: {
          jobGroup: null,
          jobName: null,
          jobStatus: null,
          beanClass: null,
          springId: null,
          methodName: null
        },
        currentPage1: 2,
        tableData: []
      }
    },
    created() {
      // 请数据殂
      this.requestDataList();
    },
    methods: {
      onQuery() {
        this.requestDataList();
      },
      resetForm(scheduleJobFilter){
        this.$refs[scheduleJobFilter].resetFields();
      },
      requestDataList() {
        // 获取自动任务列表数据
        requestDataList(this.scheduleJobFilter).then(res => {
          var data = res.resData;
          // 填充数据
          this.tableData = data.content;
        })

      },
      handleCurrentChange() {

      },
      handleSizeChange() {

      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 10px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 20px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
