<template>
  <div>
    <el-row>
      <el-col :span="4" :offset="0">
        <el-card :body-style="{ padding: '0px' }">
          <img src="../../../static/img/apply/leave.jpg" class="image" @click="onShow('leave')">
          <div style="padding: 15px;">
            <span @click="onShow('leave')">我要请假</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" :offset="2">
        <el-card :body-style="{ padding: '0px' }">
          <img src="../../../static/img/apply/leave.jpg" class="image" @click="onShow('leave2')">
          <div style="padding: 15px;" @click="onShow('leave2')">
            <span>我要请假2</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {
    FindDefByKey
  } from '../../../api/proc/procDefMag'

  export default {
    name: "apply",
    components: {},
    data() {
      return {};
    },
    methods: {
      onShow(applyType) {
        FindDefByKey(applyType).then(res => {
          if(!res.resData.deploymentId){
            this.$notify.error({
              title: '错误',
              position: 'bottom-right',
              message: '流程没有部署！'
            });
            return;
          }
          this.$router.push({
            name: 'workbenchApplyInfo',
            query: {
              // 申请信息
              optionType: 'add',
              applyType: applyType,
              proceImgId: res.resData.deploymentId
            }
          });
        });
      }
    }
  }
</script>

<style>

  .image {
    width: 100%;
    display: block;
  }

</style>
