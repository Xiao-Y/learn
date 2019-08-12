<template>
  <div>
    <div class="ms-doc">
      <h3>权限信息</h3>
      <article>
        <el-form ref="autoTaskInfo" :model="autoTaskInfo" label-width="100px" size="mini">
          <el-form-item label="任务分组" prop="jobGroup">
            <template slot-scope="scope">
              <custom-select v-model="autoTaskInfo.jobGroup"
                             :datasource="systemModuleSelect"
                             placeholder="请选择任务分组">
              </custom-select>
            </template>
          </el-form-item>
          <el-form-item label="任务名称" prop="jobName">
            <el-input v-model="autoTaskInfo.jobName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="jobStatus">
            <el-switch v-model="autoTaskInfo.jobStatus" active-text="启用" active-value="1" inactive-text="停止"
                       inactive-value="0"></el-switch>
          </el-form-item>
          <el-form-item label="Cron表达式" prop="cronExpression">
            <el-input v-model="autoTaskInfo.cronExpression" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="BeanClass" prop="cronExpression">
            <el-input v-model="autoTaskInfo.beanClass" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="SpringId" prop="cronExpression">
            <el-input v-model="autoTaskInfo.springId" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="执行方法" prop="cronExpression">
            <el-input v-model="autoTaskInfo.methodName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="串行/并行" prop="isConcurrent">
            <el-switch v-model="autoTaskInfo.isConcurrent" active-text="串行" active-value="0" inactive-text="并行"
                       inactive-value="1"></el-switch>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="autoTaskInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item label="任务描述" prop="description">
            <el-input type="textarea" v-model="autoTaskInfo.description"></el-input>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('autoTaskInfo')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>

  </div>
</template>

<script>
  import {SaveAutoTask} from "../../../api/job/jobMag";

  import CustomSelect from '../../../components/common/CustomSelect.vue';

  export default {
    components: {
      CustomSelect
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        autoTaskInfo: {
          jobStatus: "1",
          isConcurrent: "1",
          validInd: true
        },
        systemModuleSelect: []
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      this.systemModuleSelect = JSON.parse(this.$route.query.systemModuleSelect);
      if (this.optionType === 'edit') {
        this.autoTaskInfo = JSON.parse(this.$route.query.autoTaskEdit);
      }
    },
    methods: {
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
      }
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
