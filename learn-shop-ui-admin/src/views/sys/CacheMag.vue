<template>
  <div>
    <el-row>
      <el-button type="danger" @click="dataRecovery">数据恢复</el-button>
      <el-button type="danger" @click="loadDataCacheAll">初始化所有缓存</el-button>
    </el-row>
    <el-row>
      <el-button type="primary" @click="loadDataCacheByType('initDictionary')">初始化数据字典</el-button>
      <el-button type="success" @click="loadDataCacheByType('initRoleMenu')">初始化角色菜单</el-button>
      <el-button type="warning" @click="loadDataCacheByType('initRolePermission')">初始化角色权限</el-button>
    </el-row>
    <el-row>
      <el-input v-model="cacheNamespace" placeholder="Mybatis Namespace">
        <el-button slot="append" icon="el-icon-setting" @click="clearCacheNamespace">清空缓存</el-button>
      </el-input>
    </el-row>
  </div>
</template>

<script>
  import {
    LoadDataCacheAll,
    LoadDataCacheByType,
    ClearCacheNamespace
  } from "../../api/sys/CacheMag";

  import {
    DataRecovery
  } from "../../api/login";

  export default {
    data() {
      return {
        cacheNamespace: null//Mybatis CacheNamespace
      }
    },
    methods: {
      clearCacheNamespace() {
        ClearCacheNamespace(this.cacheNamespace).then(res => {
          if (res.resData) {
            this.$message.success("缓存更新成功！");
          } else {
            this.$message.error("缓存KEY不存在！");
          }
        });
      },
      loadDataCacheAll() {
        LoadDataCacheAll().then(res => {
          this.$message.success("初始化正在执行...");
        });
      },
      loadDataCacheByType(cacheType) {
        LoadDataCacheByType(cacheType).then(res => {
          this.$message.success("初始化正在执行...");
        });
      },
      // 数据恢复
      dataRecovery() {
        DataRecovery().then(res => {
          this.$message.success(res.resMsg);
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
