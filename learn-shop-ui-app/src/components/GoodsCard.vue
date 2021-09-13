<template>
  <div class="goods-item" @click="viewProduct(goodsItem.spuId)">
    <div class="pictrue">
      <img v-lazy="goodsItem.pic" alt="商品"/>
    </div>
    <div class="info">
      <div class="title-tag">
        <span class="tag" v-if="goodsItem.recommandStatus === 1">推荐</span>
        <span class="goods-name">{{ goodsItem.goodsName }}</span>
        <span class="goods-details">{{ goodsItem.subTitle }}</span>
      </div>
      <div class="price-info">
        <span v-html="$options.filters.priceFormatStyle(goodsItem.lowPrice)"></span>
        <span v-html="$options.filters.priceOldFormatStyle(goodsItem.price)"></span>
      </div>
      <goods-service :service-ids="goodsItem.serviceIds"/>
    </div>
  </div>
</template>

<script>

import GoodsService from "@/components/GoodsService";

export default {
  components: {
    GoodsService
  },
  props: {
    goodsDate: {
      type: Object,
      default: () => ({
        spuId: String,
        recommandStatus: Number,
        goodsName: String,
        price: Number,
        lowPrice: Number,
        subTitle: String,
        serviceIds: String,
        pic: String
      })
    }
  },
  data() {
    return {
      goodsItem: []
    }
  },
  created() {
    this.goodsItem = this.goodsDate;
    // 最多显示两标签
    if (this.goodsItem.serviceIds) {
      this.goodsItem.serviceIds = this.goodsItem.serviceIds.substr(0, 3);
    }
  },
  methods: {
    /**
     * 查看商品详细信息
     * @param spuId 商品id
     */
    viewProduct(spuId) {
      this.$emit('viewProduct', spuId);
    }
  }
}

</script>

<style scoped lang="scss">
.goods-item {
  width: 49%;
  border: 1px solid #CCCCCC;
  margin: 3px 0;
  border-radius: 6px;
  overflow: hidden;

  .pictrue img {
    width: 100%;
    height: 193px;
  }

  .info {
    margin: 5px;
    font-size: 12px;
    line-height: 20px;

    .title-tag {
      letter-spacing: 0;
      overflow: hidden;
      display: -webkit-box;
      text-overflow: ellipsis;
      -webkit-line-clamp: 2; /*要显示的行数*/
      -webkit-box-orient: vertical;

      .tag {
        color: #FFFFFF;
        padding: 2px 8px;
        background: #fc603a;
        border-radius: 3px;
        margin-right: 5px;
      }

      .goods-name {
        font-weight: bold;
        margin-right: 2px;
      }
    }

    .price-info {
      display: flex;
      margin-top: 1px;
      flex-wrap: wrap;
      justify-content: space-between;
    }
  }
}
</style>