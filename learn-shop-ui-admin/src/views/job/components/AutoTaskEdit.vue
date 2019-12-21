<template>
  <div>
    <div class="ms-doc">
      <h3>自动任务信息</h3>
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
          <el-form-item label="运行类型" prop="classType">
            <el-col :span="18">
              <custom-select v-model="autoTaskInfo.classType"
                             :datasource="classTypeSelect"
                             @change="changeClassType"
                             placeholder="请选择运行类型">
              </custom-select>
            </el-col>
          </el-form-item>
          <template v-if="autoTaskInfo.classType === '1' || autoTaskInfo.classType === '2'">
            <el-form-item label="运行的类" prop="runClass">
              <el-col :span="18">
                <el-input placeholder="请输入内容" v-model="autoTaskInfo.runClass" class="input-with-select">
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="执行方法" prop="methodName">
              <el-col :span="18">
                <el-input v-model="autoTaskInfo.methodName" placeholder="请输入内容"></el-input>
              </el-col>
            </el-form-item>
          </template>
          <el-form-item label="HTTP URL" prop="httpUrl" v-if="autoTaskInfo.classType === '3'">
            <el-col :span="18">
              <el-input v-model="autoTaskInfo.httpUrl" placeholder="请输入内容"/>
            </el-col>
          </el-form-item>

          <el-form-item label="Routing Key" prop="routingKey" v-if="autoTaskInfo.classType === '4'">
            <el-col :span="18">
              <el-input v-model="autoTaskInfo.routingKey" placeholder="需要配置MQ的路由"/>
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
            <el-switch v-model="autoTaskInfo.isSaveLog" active-text="是"
                       inactive-text="否"></el-switch>
          </el-form-item>
          <el-form-item label="是否发送邮件" prop="isSendMail">
            <custom-select v-model="autoTaskInfo.isSendMail"
                           :datasource="sendMailSelect"
                           @change="checkSendMail"
                           placeholder="请选择是否发送邮件">
            </custom-select>
          </el-form-item>
          <template v-if="autoTaskInfo.isSendMail != '0'">
            <el-form-item label="邮件接收人" prop="mailReceive">
              <el-col :span="18">
                <el-input type="textarea" v-model="autoTaskInfo.mailReceive" placeholder="优先使用指定的收件人,多个接收人用英文封号分割开"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="邮件模板" prop="templateId" required>
              <el-col :span="18">
                <custom-sel-mail-template v-model="autoTaskInfo.templateId"></custom-sel-mail-template>
              </el-col>
            </el-form-item>
          </template>
          <el-form-item label="任务描述" prop="description">
            <el-col :span="18">
              <el-input type="textarea" v-model="autoTaskInfo.description" required></el-input>
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
  import CustomSelMailTemplate from '../../../components/common/CustomSelMailTemplate.vue';

  export default {
    components: {
      CustomSelect,
      CustomCronInput,
      CustomSelMailTemplate
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        autoTaskInfo: {
          jobName: "",
          httpUrl: "",
          routingKey: "",
          jobGroup: "5",
          jobStatus: "1",
          isConcurrent: "1",
          isSendMail: "0",
          mailReceive: '',
          cronExpression: "",
          classType: "1",
          runClass: null,
          methodName: null,
          templateId: null,
          isSaveLog: true,
          validInd: true,
          isExceptionStop:true,
        },
        systemModuleSelect: [],
        sendMailSelect: [],
        classTypeSelect: [],
        rulesForm: {
          methodName: [{required: true, message: '请输入执行方法', trigger: 'blur'}],
          cronExpression: [{validator: this.validateCronExp, trigger: 'blur'}],
          jobName: [{required: true, message: '请输入任务名称', trigger: 'blur'}],
          mailReceive: [{validator: this.validateMailReceive, trigger: 'blur'}],
          templateId: [{required: true, message: '请选择邮件模板', trigger: 'blur'}],
          description: [{required: true, message: '请输入任务描述', trigger: 'blur'}],
          runClass: [{required: true, message: '请输入运行的类', trigger: 'blur'}],
          routingKey: [{required: true, message: '请输入Routing Key', trigger: 'blur'}],
          httpUrl: [{required: true, message: '请输入请求的URL', trigger: 'blur'}],
        }
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      this.systemModuleSelect = JSON.parse(this.$route.query.systemModuleSelect);
      this.sendMailSelect = JSON.parse(this.$route.query.sendMailSelect);
      this.classTypeSelect = JSON.parse(this.$route.query.classTypeSelect);
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
      changeClassType() {
        this.autoTaskInfo.runClass = null;
        this.autoTaskInfo.methodName = null;
        this.autoTaskInfo.routingKey = null;
        this.autoTaskInfo.httpUrl = null;
        this.$refs['autoTaskInfo'].clearValidate(['runClass', 'methodName', 'routingKey', 'httpUrl']);
      },
      checkSendMail() {
        // 不发送邮件时
        if (this.autoTaskInfo.isSendMail === '0') {
          this.autoTaskInfo.mailReceive = null;
          this.autoTaskInfo.templateId = null;
        }
      },
      // 规则校验：cronExp
      validateCronExp(rule, value, callback) {
        if ((value == '' || value === null) && this.autoTaskInfo.jobStatus === '1') {
          callback(new Error('启动状态，Cron表达式不能为空'));
        } else {
          callback();
        }
      },
      validateMailReceive(rule, value, callback) {
        if (value === '' || value === null) {
          callback();
        } else {
          value = value.replace(/\s*|\t|\r|\n/g, '');
          this.autoTaskInfo.mailReceive = value;
          var message = '';
          var emails = value.split(";");
          for (var index in emails) {
            var email = emails[index];
            if (email === '') {
              continue;
            }
            var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (!reg.test(email)) {
              message += email + ";";
            }
          }
          message !== '' ? callback(new Error(message + '邮箱格式不正确')) : callback();
        }
      },
      // // 规则校验：validInd/jobStatus
      validIndChange() {
        if (!this.autoTaskInfo.validInd) {
          this.autoTaskInfo.jobStatus = "0";
          this.$message.info('有效标志为无效时，自动任务状态只能是停止');
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
