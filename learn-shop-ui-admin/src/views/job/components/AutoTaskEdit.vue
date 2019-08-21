<template>
  <div>
    <div class="ms-doc">
      <h3>权限信息</h3>
      <article>
        <el-form ref="autoTaskInfo" :rules="rulesForm" :model="autoTaskInfo" :inline-message="true" label-width="100px"
                 size="mini">
          <el-form-item label="任务分组" prop="jobGroup">
            <template slot-scope="scope">
              <custom-select v-model="autoTaskInfo.jobGroup"
                             :datasource="systemModuleSelect"
                             placeholder="请选择任务分组">
              </custom-select>
            </template>
          </el-form-item>
          <el-form-item label="任务名称" prop="jobName">
            <el-col :span="18">
              <el-input v-model="autoTaskInfo.jobName" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="Cron表达式" prop="cronExpression" :required="autoTaskInfo.jobStatus == '1'">
            <el-col :span="18">
              <custom-cron-input v-model="autoTaskInfo.cronExpression" :isTestRun="true"></custom-cron-input>
            </el-col>
          </el-form-item>
          <el-form-item label="BeanClass" prop="beanClass">
            <el-col :span="18">
              <el-input v-model="autoTaskInfo.beanClass" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="SpringId" prop="springId">
            <el-col :span="18">
              <el-input v-model="autoTaskInfo.springId" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="执行方法" prop="methodName">
            <el-col :span="18">
              <el-input v-model="autoTaskInfo.methodName" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="任务状态" prop="jobStatus">
            <el-switch v-model="autoTaskInfo.jobStatus" @change="validIndChange" active-text="启用" active-value="1"
                       inactive-text="停止"
                       :validate-event="true"
                       inactive-value="0"></el-switch>
          </el-form-item>
          <el-form-item label="串行/并行" prop="isConcurrent">
            <el-switch v-model="autoTaskInfo.isConcurrent" active-text="串行" active-value="0" inactive-text="并行"
                       inactive-value="1"></el-switch>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="autoTaskInfo.validInd" @change="validIndChange" active-text="有效"
                       inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item label="异常停止">
            <el-switch v-model="autoTaskInfo.isExceptionStop" active-text="是" inactive-text="否"></el-switch>
          </el-form-item>
          <el-form-item label="是否记录日志" prop="isSaveLog">
            <el-switch v-model="autoTaskInfo.isSaveLog" @change="validateSendMail" active-text="是" inactive-text="否"></el-switch>
          </el-form-item>
          <el-form-item label="是否发送邮件" prop="isSendMail">
            <custom-select v-model="autoTaskInfo.isSendMail"
                           :datasource="sendMailSelect"
                           @change="validateSendMail"
                           placeholder="请选择是否发送邮件">
            </custom-select>
          </el-form-item>
          <el-form-item label="邮件接收人" prop="mailReceive" :required="autoTaskInfo.isSendMail != '0'">
            <el-col :span="18">
              <el-input type="textarea" v-model="autoTaskInfo.mailReceive" placeholder="多个接收人用英文封号分割开"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="任务描述" prop="description">
            <el-col :span="18">
              <el-input type="textarea" v-model="autoTaskInfo.description"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit">保存</el-button>
            <el-button @click="onReset('autoTaskInfo')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>
  </div>
</template>

<script>
  import {SaveAutoTask, CheckAutoTask} from "../../../api/job/jobMag";

  import CustomSelect from '../../../components/common/CustomSelect.vue';
  import CustomCronInput from '../../../components/common/CustomCronInput.vue';

  export default {
    components: {
      CustomSelect,
      CustomCronInput
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        autoTaskInfo: {
          jobName: "",
          beanClass: "",
          springId: "",
          jobGroup: "5",
          jobStatus: "1",
          isConcurrent: "1",
          isSendMail: "2",
          mailReceive: '',
          cronExpression: "",
          isSaveLog: true,
          validInd: true
        },
        systemModuleSelect: [],
        sendMailSelect: [],
        rulesForm: {
          methodName: [{required: true, message: '请输入执行方法', trigger: 'blur'}],
          beanClass: [{validator: this.validateBeanClass, trigger: 'blur'}],
          springId: [{validator: this.validateSpringId, trigger: 'blur'}],
          cronExpression: [{validator: this.validateCronExp, trigger: 'blur'}],
          jobName: [{required: true, message: '请输入任务名称', trigger: 'blur'}],
          mailReceive: [{validator: this.validateMailReceive, trigger: 'blur'}],
        }
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      this.systemModuleSelect = JSON.parse(this.$route.query.systemModuleSelect);
      this.sendMailSelect = JSON.parse(this.$route.query.sendMailSelect);
      if (this.optionType === 'edit') {
        this.autoTaskInfo = JSON.parse(this.$route.query.autoTaskEdit);
      }
    },
    methods: {
      // 校验提交
      validSubmit() {
        var _this = this;
        this.$refs['autoTaskInfo'].validate(valid => {
          if (valid) {
            _this.checkAutoTask();
          } else {
            return false;
          }
        });
      },
      // 校验提交数据正确否
      checkAutoTask() {
        CheckAutoTask(this.autoTaskInfo).then(res => {
          if (res.resData.message && res.resData.message != '') {
            this.$message({
              type: 'error',
              dangerouslyUseHTMLString: true,
              message: res.resData.message
            });
          } else {
            this.onSubmit();
          }
        });
      },
      // 提交
      onSubmit() {
        var _this = this;
        SaveAutoTask(_this.autoTaskInfo).then(res => {
          _this.$message({
            type: 'success',
            message: '保存成功!'
          });
          this.$bus.emit('autoTaskInfo', res.resData);
          _this.$router.back(-1);
        });
      },
      onReturn() {
        //调用router回退页面
        this.$router.back(-1);
      },
      onReset(autoTaskInfo) {
        this.$refs[autoTaskInfo].resetFields();
      },
      // 规则校验：beanClass
      validateBeanClass(rule, value, callback) {
        this.$refs.autoTaskInfo.validateField('springId');
        callback();
      },
      // 规则校验：springId
      validateSpringId(rule, value, callback) {
        if (value != '' && this.autoTaskInfo.beanClass != '') {
          callback(new Error('BeanClass 与 SpringId 不能同时存在!'));
        } else if (value === '' && this.autoTaskInfo.beanClass === '') {
          callback(new Error('BeanClass 与 SpringId 不能同时为空!'));
        } else {
          callback();
        }

      },
      // 规则校验：cronExp
      validateCronExp(rule, value, callback) {
        if (value === '' && this.autoTaskInfo.jobStatus === '1') {
          callback(new Error('启动状态，Cron表达式不能为空'));
        } else {
          callback();
        }
      },
      validateMailReceive(rule, value, callback) {
        if (value === '' && this.autoTaskInfo.isSendMail !== '0') {
          callback(new Error('发送邮件状态，邮件地址不能为空'));
        } else {
          callback();
        }
      },
      // // 规则校验：validInd/jobStatus
      validIndChange() {
        if (!this.autoTaskInfo.validInd) {
          this.autoTaskInfo.jobStatus = "0";
          this.$message.info('有效标志为无效时，自动任务状态只能是停止');
        }
      },
      validateSendMail() {
        console.info(this.autoTaskInfo.isSendMail);
        if (this.autoTaskInfo.isSendMail !== '0' && !this.autoTaskInfo.isSaveLog) {
          this.autoTaskInfo.isSaveLog = true;
          this.$message.info('需要发送邮件时，必要要记录日志');
        }
      },
    }
  };
</script>

<style scoped>
  .ms-doc {
    width: 70%;
    margin: 0 auto;
  }

  .ms-doc h3 {
    padding: 9px 10px 10px;
    margin: 0;
    font-size: 14px;
    line-height: 17px;
    background-color: #f5f5f5;
    border: 1px solid #d8d8d8;
    border-bottom: 0;
    border-radius: 3px 3px 0 0;
  }

  .ms-doc article {
    padding: 10px;
    word-wrap: break-word;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px;
  }

  .ms-doc article .el-checkbox {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }
</style>
