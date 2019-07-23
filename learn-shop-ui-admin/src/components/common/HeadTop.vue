<template>
  <div class="header_container">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item v-if="$route.name != 'readmeIndex'" :to="{ path: '/readme/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{$route.meta.title}}</el-breadcrumb-item>
      <!-- <el-breadcrumb-item v-for="(item, index) in $route.meta" :key="index">{{item}}</el-breadcrumb-item> -->
    </el-breadcrumb>
  </div>
</template>

<script>

  export default {
    data: function () {
      return {}
    }
  }
</script>

<style lang="less">
  .header_container{
    background-color: #EFF2F7;
    height: 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
    top: 0px;
  }
</style>
