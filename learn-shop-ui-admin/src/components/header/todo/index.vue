<template>
  <div>
    <el-badge :value="sumCount" :hidden="badgeHidden">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">待办项</span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item class="clearfix" command="apply">
            发申请
          </el-dropdown-item>
          <el-dropdown-item class="clearfix" command="ongoing">
            进行中的
            <el-badge class="mark" :value="ongoingCount" :hidden="ongoingCount <= 0"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix" command="myTasks">
            我的任务
            <el-badge class="mark" :value="myTasksCount" :hidden="myTasksCount <= 0"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix" command="myStart">
            我发起的
            <el-badge class="mark" :value="myStartCount" :hidden="myStartCount <= 0"/>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-badge>
  </div>
</template>

<script>

  import {
    MyStartCount,
    OngoingCount,
    QueryAssigneeTaskCount
  } from '../../../api/proc/applyMag'

  export default {
    name: "todoList",
    data() {
      return {
        badgeHidden: true,
        sumCount: 0,// 总待办事项
        ongoingCount: 0,//进行中的
        myTasksCount: 0,//我的任务
        myStartCount: 0,//我发起的
      }
    },
    created() {
      // 我发起的
      MyStartCount().then(res => {
        this.myStartCount = res.resData;
      });
      // 我的任务
      QueryAssigneeTaskCount().then(res => {
        this.myTasksCount = res.resData;
      });
      // 进行中的
      OngoingCount().then(res => {
        this.ongoingCount = res.resData;
      });
    },
    methods: {
      initSumCount() {
        this.sumCount = this.ongoingCount + this.myTasksCount;
        this.badgeHidden = this.sumCount <= 0;
      },
      handleCommand(command) {
        switch (command) {
          case 'apply': // 发申请
            this.$router.push({
              name: 'workbenchApplyList'
            });
            break;
          case 'ongoing': // 进行中的
          case 'myTasks': // 我的任务
          case 'myStart': // 我发起的
            this.$router.push({
              name: 'workbenchMyTaskListIndex',
              query: {
                command: command
              }
            });
            break;
        }
      }
    },
    watch: {
      ongoingCount() {
        this.initSumCount();
      }
      ,
      myTasksCount() {
        this.initSumCount()
      }
    }
  }
</script>

<style lang="less">
  .el-badge__content.is-fixed {
    top: 20px;
  }
</style>
