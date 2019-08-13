<template>
  <section ref="nestRunTime">
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
    <div>最后{{times}}次的运行时间：</div>
    <div style="padding-top: 10px;padding-left: 25px;">
      <template v-for="(str,index) in rs">
        {{str}}</br>
      </template>
    </div>
  </section>
</template>
<script>

  import {TestRunCron} from "../../../api/job/jobMag";
  import dtime from 'time-formater'
  var parser = require('cron-parser');

  export default {
    props: {
      cron: ''
    },
    data() {
      return {
        rs: [],
        dialogFlag: false,
        times: 5
      }
    },
    created() {
      // this.initPage();

    },
    methods: {
      testRun() {
        this.rs = [];
        try {
          var interval = parser.parseExpression(this.cron);
          for(var i = 0; i < this.times;i++){
            const date = dtime(interval.next()).format('YYYY-MM-DD HH:mm:ss');
            this.rs.push(date);
          }
        } catch (err) {
          console.log('Error: ' + err.message);
        }

        // var cron = this.cron.replace("?", "%3F");
        // TestRunCron(cron, this.times).then(res => {
        //   this.rs = res.resData;
        // });
      }
    }
  }
</script>
