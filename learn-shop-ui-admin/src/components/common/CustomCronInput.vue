<template>
  <div>
    <el-input :value="cron" @input="handleInput" :size="size" placeholder="请输入内容" :readonly="inputReadOnly">
      <el-button slot="append" icon="el-icon-setting" :disabled="buttonDisabled" @click="cronExpDialog">{{buttonText}}
      </el-button>
    </el-input>

    <el-dialog title="Cron表达式"
               v-if="dialogCronExpVisible"
               :visible.sync="dialogCronExpVisible"
               :close-on-click-modal="false">
      <cron-expression :cron="cron" :isTestRun="isTestRun" @cancelCron="cancelCron"
                       @saveCron="saveCron"></cron-expression>
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
      // 双向绑定
      prop: 'cron',
      event: 'change'
    },
    props: {
      // 输入域大小
      size: {
        type: String,
        default: ''
      },
      // button 显示的文字
      buttonText: {
        type: String,
        default: '配置'
      },
      // 是否禁用button
      buttonDisabled: {
        type: Boolean,
        default: false
      },
      // 是否只读input
      inputReadOnly: {
        type: Boolean,
        default: false
      },
      // 双向绑定
      cron: {
        type: String,
        default: ''
      },
      // 是否立即运行
      isTestRun: {
        type: Boolean,
        default: false
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
