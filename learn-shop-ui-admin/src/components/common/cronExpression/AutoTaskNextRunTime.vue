<template>
  <section style="height: 183px">
    <div>
      <el-row>
        <el-col :span="3">
          <el-button type="success" @click="testRun" size="mini">执行计划</el-button>
        </el-col>
        <el-col :span="9">
          Cron表达式：{{cron}}
        </el-col>
      </el-row>
    </div>
    <div>最后{{testRunCronEx.times}}次的运行时间：</div>
    <div style="padding-top: 10px;padding-left: 25px;">
      <template v-for="(str,index) in rs">
        {{str}}<br/>
      </template>
    </div>
  </section>
</template>
<script>

  import dtime from 'time-formater'
  // var parser = require('cron-parser');
  import {TestRunCron} from "../../../api/job/jobMag";

  export default {
    props: {
      cron: ''
    },
    data() {
      return {
        rs: [],
        dialogFlag: false,
        testRunCronEx: {
          times: 5,
          cron: ''
        }
      }
    },
    created() {
      // this.initPage();
      // this.testRun();
    },
    methods: {
      testRun() {
        this.rs = [];
        // try {
        //   var interval = parser.parseExpression(this.cron);
        //   for(var i = 0; i < this.times;i++){
        //     const date = dtime(interval.next()).format('YYYY-MM-DD HH:mm:ss');
        //     this.rs.push(date);
        //   }
        // } catch (err) {
        //   console.error('Error: ' + err.message);
        // }

        if (this.cron !== '') {
          this.testRunCronEx.cron = this.cron;
          TestRunCron(this.testRunCronEx).then(res => {
            this.rs = res.resData;
          });
        }
      }
    }
  }
</script>
