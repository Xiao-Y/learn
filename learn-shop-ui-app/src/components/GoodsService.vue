<template>
  <div class="service" v-if="serviceIds">
    <span class="item" v-if="serviceIds.includes('3')">包邮</span>
    <span class="item" v-if="serviceIds.includes('4')">送运费险</span>
    <span class="item" v-if="serviceIds.includes('1')">无忧退货</span>
    <span class="item" v-if="serviceIds.includes('2')">急速退款</span>
  </div>
</template>

<script>
export default {
  props: {
    serviceIds: {
      type: String,
      default: null
    }
  }
}

</script>

<style scoped lang="scss">
.service {
  margin: 2px 0;

  .item {
    font-size: 12px;
    color: #fc603a;
    border: 1px solid;
    margin-right: 7px;
    padding: 2px;
  }
}
</style>