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
                     :total="queryPage.recordCount | string2Number">
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
        this.queryPage.pageSize = Number(val);
        this.$emit('onQuery');
      },
      // 翻页
      hcurrentChange(val) {
        this.queryPage.pageNo = Number(val);
        this.$emit('onQuery');
      }
    },
    filters:{
      string2Number: function(num) {
        return Number(num);
      }
    }
  }
</script>

<style scoped>

</style>
