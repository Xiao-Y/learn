<template>
  <el-row>
    <div class="block">
      <el-pagination background
                     layout="total, sizes, prev, pager, next"
                     @size-change="sizeChange"
                     @current-change="hcurrentChange"
                     :page-sizes="pageSizes"
                     :current-page.sync="queryPage.pageNo"
                     :page-size="queryPage.pageSize"
                     :total="queryPage.recordCount">
      </el-pagination>
    </div>
  </el-row>
</template>

<script>

  export default {
    props: {
      queryPage: {
        type: Object,
        default: null
      }
    },
    data() {
      return {
        pageSizes: [10, 20, 30, 40],
      }
    },
    created() {
    },
    methods: {
      // 改变页面大小
      sizeChange(val) {
        this.queryPage.pageNo = 1;
        this.queryPage.pageSize = val;
        this.$emit('onQuery');
      },
      // 翻页
      hcurrentChange(val) {
        this.queryPage.pageNo = val;
        this.$emit('onQuery');
      }
    }
  }
</script>

<style scoped>

</style>
