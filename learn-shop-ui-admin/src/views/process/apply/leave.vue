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
                <el-date-picker type="datetime" v-model="leaveInfo.startDate"
                                :disabled="readOnlyPage.startDateReadOnly"></el-date-picker>
              </el-col>
            </template>
          </el-form-item>
          <el-form-item label="结束时间" prop="endDate">
            <template slot-scope="scope">
              <el-col :span="18">
                <el-date-picker type="datetime" v-model="leaveInfo.endDate"
                                :disabled="readOnlyPage.endDateReadOnly"></el-date-picker>
              </el-col>
            </template>
          </el-form-item>
          <el-form-item label="原因" prop="reason">
            <el-col :span="18">
              <el-input type="textarea" v-model="leaveInfo.reason" :disabled="readOnlyPage.reasonReadOnly"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="意见" prop="comment" v-if="readOnlyPage.commentShow">
            <el-col :span="18">
              <el-input type="textarea" v-model="leaveInfo.comment" :disabled="readOnlyPage.commentReadOnly"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit('submit')" v-if="readOnlyPage.submitShow">提交</el-button>
            <el-button type="primary" @click="commitProcess('reSubmit')" v-if="readOnlyPage.reSubmitShow">重新提交
            </el-button>
            <el-button type="primary" @click="commitProcess('cancel')" v-if="readOnlyPage.cancelShow">取消申请</el-button>
            <el-button type="primary" @click="commitProcess('agree')" v-if="readOnlyPage.agreeShow">同意</el-button>
            <el-button type="primary" @click="commitProcess('reject')" v-if="readOnlyPage.rejectShow">驳回</el-button>
            <el-button @click="$router.back(-1)">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>
  </div>
</template>


<script>
  import {
    FindApplyById
  } from "../../../api/proc/applyMag";
  import {
    SubmitLeave,
    CommitLeaveProcess
  } from "../../../api/proc/apply/leaveApi";

  export default {
    data() {
      return {
        taskId: null,
        leaveInfo: {
          id: null,
          startDate: null,
          endDate: null,
          reason: '',
          comment: '',
          approveStatus: '0',
          procInstId: '',
          assignee: '', // 指定下一个任务处理人，用于驳回
          submitType: '' // 提交类型:submit-提交，reSubmit-重新提交,cancel-取消申请，agree-同意，reject-驳回
        },
        rulesForm: {
          startDate: [{required: true, message: '请输入请假开始时间', trigger: 'blur'}],
          endDate: [{required: true, message: '请输入请假结束时间', trigger: 'blur'}],
          reason: [{required: true, message: '请输入请假原因', trigger: 'blur'}],
          comment: [{required: true, message: '请输入意见', trigger: 'blur'}],
        },
        readOnlyPage: {},
        readOnlyPageValue: {
          add: {// 新申请的
            startDateReadOnly: false,
            endDateReadOnly: false,
            reasonReadOnly: false,
            commentShow: false,
            commentReadOnly: true,
            submitShow: true,
            cancelShow: false,
            reSubmitShow: false,
            agreeShow: false,
            rejectShow: false
          },
          rework: {// 退回的申请
            startDateReadOnly: false,
            endDateReadOnly: false,
            reasonReadOnly: false,
            commentShow: false,
            commentReadOnly: true,
            submitShow: false,
            cancelShow: true,
            reSubmitShow: true,
            agreeShow: false,
            rejectShow: false
          },
          commit: {// 审批的申请
            startDateReadOnly: true,
            endDateReadOnly: true,
            reasonReadOnly: true,
            commentShow: true,
            commentReadOnly: false,
            submitShow: false,
            cancelShow: false,
            reSubmitShow: false,
            agreeShow: true,
            rejectShow: true
          },
          view: {// 查看申请
            startDateReadOnly: true,
            endDateReadOnly: true,
            reasonReadOnly: true,
            commentShow: false,
            commentReadOnly: true,
            submitShow: false,
            cancelShow: false,
            reSubmitShow: false,
            agreeShow: false,
            rejectShow: false
          }
        }
      };
    },
    created() {
      var optionType = this.$route.query.optionType;
      // 设置页面读写
      this.initReadOnlyPage(optionType);
      if (optionType === 'commit' || optionType === 'view') {
        this.taskId = this.$route.query.taskId;
        this.leaveInfo.id = this.$route.query.applyId;
        this.leaveInfo.procInstId = this.$route.query.procInstId;
        if (this.leaveInfo.id) {
          FindApplyById(this.leaveInfo.id).then(res => {
            if (res.resData && res.resData != '') {
              var applyData = res.resData.applyData;
              this.leaveInfo.assignee = res.resData.applyUserCode;
              Object.assign(this.leaveInfo, JSON.parse(applyData));
              // 上一次的提交类型
              if (this.leaveInfo.submitType === 'reject') {
                // 退回修改的
                this.initReadOnlyPage('rework');
              }
            }
          });
        }
      }
    },
    methods: {
      initReadOnlyPage(optionType) {
        Object.assign(this.readOnlyPage, this.readOnlyPageValue[optionType]);
      },
      // 校验提交
      validSubmit(submitType) {
        var _this = this;
        this.$refs['leaveInfo'].validate(valid => {
          if (valid) {
            this.leaveInfo.submitType = submitType;
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
          _this.$router.back(-1);
        });
      },
      // 审批
      commitProcess(submitType) {
        var _this = this;
        this.leaveInfo.submitType = submitType;
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
