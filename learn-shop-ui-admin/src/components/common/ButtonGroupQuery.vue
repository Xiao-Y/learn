<template>
  <div style="display: inline-block;">
    <el-button type="success" size="mini" @click="onAdd" icon="el-icon-plus" v-if="showAdd" :disabled="disAdd">添加
    </el-button>
    <el-button type="primary" size="mini" @click="onQuery" icon="el-icon-search" v-if="showQuery" :disabled="disQuery">
      查询
    </el-button>
    <el-button type="info" size="mini" @click="resetForm" icon="el-icon-close" v-if="showReset" :disabled="disReset">
      重置
    </el-button>
    <el-button type="warning" size="mini" @click="refresh" icon="el-icon-refresh" v-if="showRefresh"
               :disabled="disRefresh">刷新
    </el-button>
  </div>
</template>

<script>
  export default {
    props: {
      // 提示信息延时
      openDelay: {
        type: Number,
        default: 1500
      },
      // 是否显示添加按钮
      showAdd: {
        type: Boolean,
        default: true
      },
      // 是否禁用添加按钮
      disAdd: {
        type: Boolean,
        default: false
      },
      // 是否显示查询按钮
      showQuery: {
        type: Boolean,
        default: true
      },
      // 是否禁用查询按钮
      disQuery: {
        type: Boolean,
        default: false
      },
      // 是否显示重置按钮
      showReset: {
        type: Boolean,
        default: true
      },
      // 是否禁用重置按钮
      disReset: {
        type: Boolean,
        default: false
      },
      // 是否显示刷新按钮
      showRefresh: {
        type: Boolean,
        default: true
      },
      // 是否禁用刷新按钮
      disRefresh: {
        type: Boolean,
        default: false
      },
      // 查询表彰的 ref
      queryRef: {
        type: String,
        default: 'queryFilter'
      },
      queryFilter: {
        type: Object,
        default: null
      }
    },
    data() {
      return {}
    },
    methods: {
      // 添加事件
      onAdd() {
        this.$emit('onAdd');
      },
      // 查询事件
      onQuery() {
        this.queryFilter.pageNo = 1;
        this.$emit('onQuery');
      },
      // 重置事件
      resetForm() {
        this.$parent.$refs[this.queryRef].resetFields();
      },
      // 刷新事件
      refresh() {
        this.$emit('onQuery');
      }
    }
  }
</script>

<style>
</style>
