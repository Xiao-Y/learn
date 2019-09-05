<template>
  <div>
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
      <el-form-item label="意见" prop="comment" v-if="!readOnly">
        <el-col :span="18">
          <el-input type="textarea" v-model="leaveInfo.comment" :disabled="readOnly"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item size="mini">
        <el-button type="primary" @click="validSubmit" v-if="!readOnly">提交</el-button>
        <el-button type="primary" @click="commitProcess(true)" v-if="readOnly">同意</el-button>
        <el-button type="primary" @click="commitProcess(false)" v-if="readOnly">驳回</el-button>
        <el-button @click="onReturn" size="mini">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {
    SubmitLeave,
    FindLeaveById
  } from "../../../api/proc/todo";

  import {
    CommitProcess
  } from '../../../api/proc/proceTaskMag'

  export default {
    props: {
      pageReadOnly: {
        type: Boolean,
        default: true
      }
    },
    // 双向绑定
    model: {
      prop: 'dialogVisible',
      event: 'change'
    },
    dialogVisible: {
      show: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        readOnly: true,
        optionType: 'edit', // 操作类型，edit,add,view
        taskId: null,
        leaveInfo: {
          id: null,
          startDate: null,
          endDate: null,
          reason: '',
          comment: '',
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
      this.readOnly = this.pageReadOnly;
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit') {
        this.taskId = this.$route.query.taskId;
        this.leaveInfo.id = this.$route.query.id;
        if (this.leaveInfo.id) {
          FindLeaveById(this.leaveInfo.id).then(res => {
            if (res.resData && res.resData != '') {
              this.leaveInfo = JSON.parse(res.resData);
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
          // 关闭窗口
          this.$emit('change', false);
        });
      },
      // 审批
      commitProcess(flag) {
        var _this = this;
        console.info("flag:", flag);
        var taskInfo = {
          deptLeaderApprove: flag
        };
        CommitProcess(taskInfo, _this.taskId).then(res => {
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

</style>
