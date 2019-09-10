<template>
  <div>
    <el-row>
      <!--      <el-col :span="4" v-for="(o, index) in 12" :key="o" :offset="(index % 4) > 0 ? 2 : 0">-->
      <!--        <el-card :body-style="{ padding: '0px' }">-->
      <!--          <img src="../../../static/img/apply/leave.jpg" class="image" @click="leave">-->
      <!--          <div style="padding: 15px;">-->
      <!--            <span>我要请假</span>-->
      <!--          </div>-->
      <!--        </el-card>-->
      <!--      </el-col>-->
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
          <img src="../../../static/img/apply/leave.jpg" class="image" @click="onShow('leave')">
          <div style="padding: 15px;" @click="onShow('leave')">
            <span>我要请假</span>
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
