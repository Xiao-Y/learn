<template>
  <div>
    <el-input :value="cron" @input="handleInput" size="mini" placeholder="请输入内容">
      <el-button slot="append" icon="el-icon-setting" @click="cronExpDialog">配置</el-button>
    </el-input>

    <el-dialog title="Cron表达式"
               v-if="dialogCronExpVisible"
               :visible.sync="dialogCronExpVisible"
               :close-on-click-modal="false">
      <cron-expression :cron="cron" @cancelCron="cancelCron" @saveCron="saveCron"></cron-expression>
    </el-dialog>
  </div>
</template>

<script>
  import CronExpression from '../../components/common/cronExpression/CronExpression.vue';

  export default {
    components: {
      CronExpression
    },
    name: "CustomCronInput",
    model: {
      prop: 'cron',
      event: 'change'
    },
    props: {
      // 双向绑定
      cron: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        dialogCronExpVisible: false
      }
    },
    methods: {
      // 打开 cron 表达式选择窗口
      cronExpDialog() {
        this.dialogCronExpVisible = true;
      },
      cancelCron() {
        this.dialogCronExpVisible = false;
      },
      handleInput(value) {
        this.$emit('change', value)
      },
      // 保存cron
      saveCron(cron) {
        this.$emit('change', cron);
        this.dialogCronExpVisible = false;
      }
    }
  }
</script>
