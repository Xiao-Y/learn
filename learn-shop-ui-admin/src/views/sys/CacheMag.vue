<template>
  <div>
    <el-row>
      <el-button type="danger" @click="loadDataCacheAll">初始化所有缓存</el-button>
    </el-row>
    <el-row>
      <el-button type="primary" @click="loadDataCacheByType('initDictionary')">初始化数据字典</el-button>
      <el-button type="success" @click="loadDataCacheByType('initRoleMenu')">初始化角色菜单</el-button>
      <el-button type="warning" @click="loadDataCacheByType('initRolePermission')">初始化角色权限</el-button>
    </el-row>
  </div>
</template>

<script>
  import {
    LoadDataCacheAll,
    LoadDataCacheByType
  } from "../../api/sys/CacheMag";

  export default {
    data() {
      return {}
    },
    methods: {
      loadDataCacheAll() {
        LoadDataCacheAll().then(res => {
          this.$message.success("初始化正在执行...");
        });
      },
      loadDataCacheByType(cacheType) {
        LoadDataCacheByType(cacheType).then(res => {
          this.$message.success("初始化正在执行...");
        });
      }
    }
  }
</script>

<style>
  .el-row {
    margin-bottom: 10px;
  }
</style>
