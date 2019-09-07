<template>
  <div>
    <template>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="申请信息" name="first">
          <!-- 请假申请 -->
          <leave v-if="applyType === 'leave'"></leave>
        </el-tab-pane>
        <el-tab-pane label="申请流程" name="second" :lazy="true">
          <view-process-img></view-process-img>
        </el-tab-pane>
        <el-tab-pane v-if="optionType !== 'add'" label="批注" name="third" :lazy="true">批注</el-tab-pane>
      </el-tabs>
    </template>
  </div>
</template>

<script>
  import Leave from './leave'
  import ViewProcessImg from '../../../views/process/ViewProcessImg'
  import {
    FindApplyPage,
  } from "../../../api/proc/applyMag";

  export default {
    name: "apply",
    components: {
      ViewProcessImg,
      Leave
    },
    data() {
      return {
        activeName: 'first',
        applyType: '',
        optionType: null,
      };
    },
    created() {
      this.applyType = this.$route.query.applyType;
      this.optionType = this.$route.query.optionType;
    },
    methods: {
      openProcImg(optionType) {
        if (optionType === 'add') {

        } else {

        }
      },
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
  }
</script>

<style>

</style>
