<template>
  <div>
    <template>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="申请信息" name="first">
          <!-- 加载对应的页面 -->
          <component v-bind:is="applyType"></component>
        </el-tab-pane>
        <!-- 流程图 -->
        <el-tab-pane label="申请流程" name="second" :lazy="true">
          <view-process-img></view-process-img>
        </el-tab-pane>
        <!-- 批注信息 -->
        <el-tab-pane v-if="optionType !== 'add'" label="批注" name="third" :lazy="true">
          <comment-list></comment-list>
        </el-tab-pane>
      </el-tabs>
    </template>
  </div>
</template>

<script>
  // 申请页面---start 
  import leave from '../leave';
  // 申请页面---end
  import CommentList from './commentList'; // 批注信息
  import ViewProcessImg from '../../ViewProcessImg'; // 流程图

  export default {
    name: "apply",
    components: {
      CommentList,
      ViewProcessImg,
      CommentList,
      leave
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
      handleClick(tab, event) {
        // console.log(tab, event);
      }
    }
  }
</script>

<style>

</style>
