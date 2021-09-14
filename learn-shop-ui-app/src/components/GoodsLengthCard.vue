<template>
  <van-card
      :thumb="goodsItem.pic"
      @click="toDetails(goodsItem.spuId)">
    <template #num>
      <span>30天销量：{{ goodsItem.sale }}</span>
    </template>
    <template #price>
      <span v-html="$options.filters.priceFormatStyle(goodsItem.lowPrice)"></span>
    </template>
    <template #origin-price>
      <span v-html="$options.filters.priceOldFormatStyle(goodsItem.lowPrice)"></span>
    </template>
    <template #title>
      <span class="van-card__goodsname">{{ goodsItem.goodsName }}/{{ goodsItem.subTitle }}</span>
    </template>
    <template #desc>
      <span class="van-card__detail">{{ goodsItem.detailTitle }}</span>
    </template>
    <template #tag>
      <div v-if="goodsItem.recommandStatus === 1" class="van-tag van-tag--mark van-tag--danger">推荐</div>
      <div v-if="goodsItem.recommandStatus === 0 && goodsItem.newStatus === 1"
           class="van-tag van-tag--mark van-tag--green">新品
      </div>
    </template>
    <template #tags>
      <GoodsService :service-ids="goodsItem.serviceIds"/>
    </template>
  </van-card>
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
        sale: Number,
        recommandStatus: Number,
        goodsName: String,
        price: Number,
        lowPrice: Number,
        subTitle: String,
        serviceIds: String,
        detailTitle: String,
        newStatus: Number,
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
     * 跳转到详情页面
     * @param id
     */
    toDetails(id) {
      this.$emit("toDetails", id);
    },
  }
}

</script>

<style scoped lang="scss">
/*商品详细内容*/
.van-card__content span {
  padding-top: 4px;
}

/*商品标题*/
.van-card__goodsname {
  font-weight: 500;
  font-size: 16px;
  margin-bottom: 5px;
}

/*商品简介，单选/双行显示多的... 替换*/
.title-class {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  word-break: break-all;
}

.van-card__detail {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  line-height: 1.7em;
}

/*销量*/
.van-card__num {
  margin-top: 4px;
}

/*tag颜色*/
.van-tag--green {
  background-color: #22c56f;
}

/*小标签*/
.van-tags__div span {
  margin-right: 5px;
}

</style>