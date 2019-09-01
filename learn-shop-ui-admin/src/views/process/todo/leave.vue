<template>
  <div>
    <el-form ref="leaveInfo" :model="leaveInfo" :rules="rulesForm" label-width="100px" size="mini"
             :inline-message="true">
      <el-form-item label="开始时间" prop="startDate">
        <template slot-scope="scope">
          <el-col :span="18">
            <el-date-picker type="datetime" v-model="leaveInfo.startDate"></el-date-picker>
          </el-col>
        </template>
      </el-form-item>
      <el-form-item label="结束时间" prop="endDate">
        <template slot-scope="scope">
          <el-col :span="18">
            <el-date-picker type="datetime" v-model="leaveInfo.endDate"></el-date-picker>
          </el-col>
        </template>
      </el-form-item>
      <el-form-item label="原因" prop="reason">
        <el-col :span="18">
          <el-input type="textarea" v-model="leaveInfo.reason"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item size="mini">
        <el-button type="primary" @click="validSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {
    SubmitLeave,
    FindLeaveById
  } from "../../../api/proc/todo";

  export default {
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
        optionType: 'edit', // 操作类型，edit,add,view
        leaveInfo: {
          id: null,
          startDate: null,
          endDate: null,
          reason: '',
        },
        rulesForm: {
          startDate: [{required: true, message: '请输入请假开始时间', trigger: 'blur'}],
          endDate: [{required: true, message: '请输入请假结束时间', trigger: 'blur'}],
          reason: [{required: true, message: '请输入请假原因', trigger: 'blur'}],
        }
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'view') {
        if (this.$route.query.id) {
          FindleaveById(this.$route.query.id).then(res => {
            this.leaveInfo = res.resData;
          })
        }
      }
    },
    methods: {
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
      }
    }
  };
</script>

<style scoped>

</style>
