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
          <img src="../../../static/img/apply/leave.jpg" class="image" @click="leaveShow = true">
          <div style="padding: 15px;">
            <span>我要请假</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4" :offset="2">
        <el-card :body-style="{ padding: '0px' }">
          <img src="../../../static/img/apply/leave.jpg" class="image" @click="leaveShow = true">
          <div style="padding: 15px;">
            <span>我要请假</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 申请类型 -->
    <el-dialog title="填写申请单"
               v-if="dialogVisible"
               :visible.sync="dialogVisible"
               :show-close="true"
               :close-on-click-modal="false">
      <!-- 请假申请 -->
      <leave v-if="leaveShow" v-model="dialogVisible" :page-read-only="false"></leave>
    </el-dialog>
    <!-- 申请类型 -->
  </div>
</template>

<script>
  import Leave from './leave'

  export default {
    name: "apply",
    components: {
      Leave
    },
    data() {
      return {
        dialogVisible: false,
        leaveShow: false
      };
    },
    methods: {
      // leave() {
      //   this.dialogVisible = true;
      //   this.leaveShow = true;
      //   console.info("leave");
      // }
    }
  }
</script>

<style>

  .image {
    width: 100%;
    display: block;
  }

</style>
