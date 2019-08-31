<template>
  <div class="header_container" v-if="$route.name != 'homeIndex'">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ name: 'homeIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{$route.meta.title}}</el-breadcrumb-item>
      <!-- <el-breadcrumb-item v-for="(item, index) in $route.meta" :key="index">{{item}}</el-breadcrumb-item> -->
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  data: function() {
    return {};
  },
  methods: {}
};
</script>

<style lang="less">
.header_container {
  background-color: #eff2f7;
  height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 20px;
  top: 0px;
}
</style>
