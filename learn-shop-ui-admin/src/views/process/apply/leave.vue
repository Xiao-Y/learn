<template>
  <div>
    <div class="ms-doc">
      <h3>请假申请信息</h3>
      <article>
        <el-form ref="leaveInfo" :model="leaveInfo" :rules="rulesForm" label-width="100px" size="mini"
                 :inline-message="true">
          <el-form-item label="开始时间" prop="startDate">
            <template slot-scope="scope">
              <el-col :span="18">
                <el-date-picker type="datetime" v-model="leaveInfo.startDate" :disabled="readOnly"></el-date-picker>
              </el-col>
            </template>
          </el-form-item>
          <el-form-item label="结束时间" prop="endDate">
            <template slot-scope="scope">
              <el-col :span="18">
                <el-date-picker type="datetime" v-model="leaveInfo.endDate" :disabled="readOnly"></el-date-picker>
              </el-col>
            </template>
          </el-form-item>
          <el-form-item label="原因" prop="reason">
            <el-col :span="18">
              <el-input type="textarea" v-model="leaveInfo.reason" :disabled="readOnly"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="意见" prop="comment" v-if="readOnly && optionType !== 'view'">
            <el-col :span="18">
              <el-input type="textarea" v-model="leaveInfo.comment" :disabled="!readOnly"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit" v-if="!readOnly">提交</el-button>
            <el-button type="primary" @click="commitProcess('true')" v-if="readOnly && optionType !== 'view'">同意
            </el-button>
            <el-button type="primary" @click="commitProcess('false')" v-if="readOnly && optionType !== 'view'">驳回
            </el-button>
            <el-button @click="onReturn" size="mini">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>
  </div>
</template>


<script>
  import {
    SubmitLeave,
    FindApplyById,
    CommitLeaveProcess
  } from "../../../api/proc/applyMag";

  export default {
    data() {
      return {
        readOnly: true,
        optionType: 'add', // 操作类型，edit,add,view
        taskId: null,
        leaveInfo: {
          id: null,
          startDate: null,
          endDate: null,
          reason: '',
          comment: '',
          deptLeaderApprove: false,
          procInstId: '',
        },
        rulesForm: {
          startDate: [{required: true, message: '请输入请假开始时间', trigger: 'blur'}],
          endDate: [{required: true, message: '请输入请假结束时间', trigger: 'blur'}],
          reason: [{required: true, message: '请输入请假原因', trigger: 'blur'}],
          comment: [{required: true, message: '请输入意见', trigger: 'blur'}],
        }
      };
    },
    created() {
      this.readOnly = Boolean(this.$route.query.pageReadOnly);
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit' || this.optionType === 'view') {
        this.taskId = this.$route.query.taskId;
        this.leaveInfo.id = this.$route.query.applyId;
        this.leaveInfo.procInstId = this.$route.query.procInstId;
        if (this.leaveInfo.id) {
          FindApplyById(this.leaveInfo.id).then(res => {
            if (res.resData && res.resData != '') {
              Object.assign(this.leaveInfo,JSON.parse(res.resData));
            }
          })
        }
      }
    },
    methods: {
      onReturn() {
        this.$router.back(-1);
      },
      // 校验提交
      validSubmit() {
        var _this = this;
        this.$refs['leaveInfo'].validate(valid => {
          if (valid) {
            _this.onSubmit();
          } else {
            return false;
          }
        });
      },
      onSubmit() {
        var _this = this;
        SubmitLeave(_this.leaveInfo).then(res => {
          _this.$message({
            type: 'success',
            message: '提交成功!'
          });
        });
      },
      // 审批
      commitProcess(flag) {
        var _this = this;
        this.leaveInfo.deptLeaderApprove = flag;
        CommitLeaveProcess(_this.leaveInfo, _this.leaveInfo.procInstId, _this.taskId).then(res => {
          _this.$message({
            type: 'success',
            message: '提交成功!'
          });
          _this.$bus.emit('taskInfo', _this.leaveInfo.id);
          _this.$router.back(-1);
        });
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
</style>
