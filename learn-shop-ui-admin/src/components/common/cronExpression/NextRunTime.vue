<template>
  <el-card shadow="always">
    <div slot="header">
      <span>最后{{times}}次的运行时间</span>
    </div>
    <el-row>
      <el-col :span="3">Cron表达式：</el-col>
      <el-col :span="20">
        <el-input v-model="cronExp" :readonly="true" size="mini">
          <el-button slot="append" icon="el-icon-search" @click="testRun">执行计划</el-button>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <template v-for="(str,index) in rs">
        {{str}}<br/>
      </template>
    </el-row>
  </el-card>
</template>
<script>

  import {TestRunCron} from "../../../api/job/jobMag";

  export default {
    props: {
      // cron 表达式
      cronExp: {
        type: String,
        default: ''
      },
      // 是否立即运行
      isTestRun: {
        type: Boolean,
        default: false
      },
      // 运行的次数
      times: {
        type: Number,
        default: 5
      }
    },
    data() {
      return {
        rs: [],
        testRunCronEx: {}
      }
    },
    created() {
      if (this.isTestRun) {
        this.testRun();
      }
    },
    methods: {
      testRun() {
        if (this.cronExp !== '') {
          this.testRunCronEx.times = this.times;
          this.testRunCronEx.cron = this.cronExp;
          TestRunCron(this.testRunCronEx).then(res => {
            this.rs = res.resData;
          });
        }
      }
    }
  }
</script>
