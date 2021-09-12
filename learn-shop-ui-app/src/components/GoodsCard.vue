<template>
  <div class="goods-list">
    <div class="goods-item" v-for="(item,index) in goodsDate" @click="viewProduct(item.id)" :key="index">
      <div class="pictrue">
        <img :src="item.pic"/>
      </div>
      <div class="info">
        <div class="title-tag">
          <span class="tag" v-if="item.recommandStatus === 1">推荐</span>
          <span class="goods-name">{{ item.goodsName }}</span>
          <span class="goods-details">{{ item.subTitle }}</span>
        </div>
        <div class="price-info">
          <span class="new-price">￥{{ item.lowPrice | priceFormat }}</span>
          <span class="old-price">￥{{ item.price | priceFormat }}</span>
        </div>
        <div class="service" v-if="item.serviceIds">
          <span class="item" v-if="item.serviceIds.includes('1')">无忧退货</span>
          <span class="item" v-if="item.serviceIds.includes('2')">快速退款</span>
          <span class="item" v-if="item.serviceIds.includes('3')">免费包邮</span>
          <span class="item" v-if="item.serviceIds.includes('4')">送运费险</span>
        </div>
      </div>
    </div>
    <div v-if="!goodsDate || goodsDate.length <= 0">没有啦！！！</div>
  </div>
</template>

<script>
export default {
  props: {
    goods: {
      type: Array,
      default: () => [{
        id: Number,
        recommandStatus: Number,
        goodsName: String,
        price: Number,
        lowPrice: Number,
        subTitle: String,
        serviceIds: String,
        pic: String
      }]
    }
  },
  data() {
    return {
      goodsDate: []
    }
  },
  created() {
    this.goodsDate = this.goods;
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
/*商品列表*/
.goods-list {
  display: flex;
  flex-wrap: wrap;
  padding: 7px;
  justify-content: space-between;
}

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
    font-size: 14px;
    line-height: 22px;

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
      margin-top: 5px;
      flex-wrap: wrap;
      justify-content: space-between;

      .new-price {
        font-size: 17px;
        color: #fc603a;
        font-weight: bold;
      }

      .old-price {
        font-size: 12px;
        text-decoration: line-through;
        margin-left: 8px;
      }
    }

    .service {
      .item {
        font-size: 12px;
        color: #fc603a;
        border: 1px solid;
        margin-right: 7px;
        padding: 2px;
      }
    }
  }
}
</style>