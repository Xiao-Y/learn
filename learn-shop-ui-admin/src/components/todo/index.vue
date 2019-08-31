<template>
  <div>
    <el-badge :value="12">
      <el-dropdown trigger="click">
        <span class="el-dropdown-link">待办项</span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item class="clearfix">
            发申请
          </el-dropdown-item>
          <el-dropdown-item class="clearfix">
            审核中的
            <el-badge class="mark" :value="12"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix">
            我审核的
            <el-badge class="mark" :value="5"/>
          </el-dropdown-item>
          <el-dropdown-item class="clearfix">
            我发起的
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-badge>
  </div>
</template>

<script>
  export default {
    name: "todoList"
  }
</script>

<style lang="less">
  .el-badge__content.is-fixed {
    top: 20px;
  }
</style>
