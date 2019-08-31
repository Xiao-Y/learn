<template>
  <div>
    <el-badge :value="sumNum" :hidden="badgeHidden">
      <el-dropdown trigger="click">
        <span class="el-dropdown-link">待办项</span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item class="clearfix">
            发申请
          </el-dropdown-item>
          <el-dropdown-item class="clearfix">
            审核中的
            <el-badge class="mark" :value="auditProgressNum" :hidden="auditProgressNum <= 0"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix">
            我审核的
            <el-badge class="mark" :value="myAudit" :hidden="myAudit <= 0"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix">
            我发起的
            <el-badge class="mark" :value="myInitiated" :hidden="myInitiated <= 0"/>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-badge>
  </div>
</template>

<script>

  import {QueryOwnerTaskCount} from '../../api/proc/proceTaskMag'

  export default {
    name: "todoList",
    data() {
      return {
        badgeHidden: true,
        sumNum: 0,// 总待办事项
        auditProgressNum: 0,//审核中的
        myAudit: 0,//我审核的
        myInitiated: 0,//我发起的
      }
    },
    created() {
      QueryOwnerTaskCount().then(res => {
        this.myAudit = res.resData;
      });
    },
    methods: {
      initSumNum() {
        this.sumNum = this.auditProgressNum + this.myAudit;
        this.badgeHidden = this.sumNum <= 0;
      }
    },
    watch: {
      auditProgressNum() {
        this.initSumNum();
      },
      myAudit() {
        this.initSumNum()
      }
    }
  }
</script>

<style lang="less">
  .el-badge__content.is-fixed {
    top: 20px;
  }
</style>
