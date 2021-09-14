<template>
  <div class="goods">
    <van-nav-bar
        title="商品信息"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"/>
    <!-- 轮播图片 -->
    <good-swipe :spu-pics="goods.albumPics" :sku-pics="goods.albumPics"/>

    <van-cell-group>
      <van-cell>
        <div class="goods-title">{{ goods.goodsName }} {{ goods.subTitle }} {{ goods.detailTitle }}</div>
        <span v-html="$options.filters.priceFormatStyle(goods.lowPrice)"></span>
        <span class="goods-old-price" v-html="$options.filters.priceOldFormatStyle(goods.price)"></span>
      </van-cell>
      <van-cell class="goods-express">
        <van-col span="10">
          发货：
          <van-icon name="location-o"/>
          {{ goods.address }}
        </van-col>
        <van-col span="6">
          <van-icon name="logistics"/>
          运费：{{ goods.express }}
        </van-col>
        <van-col class="remain" span="8">
          <van-icon name="shopping-cart-o"/>
          剩余：{{ goods.sale }}
        </van-col>
      </van-cell>
      <van-cell title="服务保障" icon="service-o" is-link @click="sorry">
        <goods-service :service-ids="goods.serviceIds"/>
      </van-cell>
      <van-cell value="进入店铺" icon="shop-o" is-link @click="sorry">
        <template slot="title">
          <span class="van-cell-text">有赞的店</span>
          <van-tag class="goods-tag" type="danger">官方</van-tag>
        </template>
      </van-cell>
      <van-cell title="规格选择" icon="discount" is-link @click="onViewSuk"/>
      <van-cell title="商品参数" icon="setting-o" is-link @click="sorry"/>
      <van-cell title="宝贝评价" icon="edit" is-link @click="sorry">暂无评价</van-cell>
    </van-cell-group>

    <van-goods-action>
      <van-goods-action-icon icon="chat-o" @click="sorry">
        客服
      </van-goods-action-icon>
      <van-goods-action-icon icon="cart-o" @click="onClickCart">
        购物车
      </van-goods-action-icon>
      <van-goods-action-button type="warning" @click="onViewSuk">
        加入购物车
      </van-goods-action-button>
      <van-goods-action-button type="danger" @click="onViewSuk">
        立即购买
      </van-goods-action-button>
    </van-goods-action>
    <!-- sku -->
    <coustomSuk v-model="showSuk" v-if="showSuk" :goods-data="goods" @onAddCart="onAddCart"/>
  </div>

</template>

<script>

import {GetById} from '@/api/GoodsSpuApi';

import CoustomSuk from '@/components/Sku';
import GoodsService from "@/components/GoodsService";
import GoodSwipe from "@/components/GoodSwipe";

export default {
  components: {
    CoustomSuk, GoodsService, GoodSwipe
  },
  data() {
    return {
      goods: {
        id: 0,
        brandName: null, // 品牌名称
        spuNo: null, // 商品编号
        goodsName: null, // 商品名称
        subTitle: null, // 副标题
        detailTitle: null, // 详情标题
        price: 0.00, // 价格
        lowPrice: 0.00, // 最低售价
        express: 0.00, // 运费
        newStatus: 0, // 新品状态:0->不是新品；1->新品
        recommandStatus: 0, // 推荐状态；0->不推荐；1->推荐
        previewStatus: 0, // 是否为预告商品：0->不是；1->是
        address: '武汉', // 发货地
        sale: 0, // 销量
        stock: 0, // 总库存量
        lowStock: 0, // 库存预警值
        serviceIds: null, // 以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
        albumPics: [] // 画册图片，连产品图片限制为5张，以逗号分割
      },
      showSuk: false,
    };
  },
  created() {
    // 查询商品信息
    GetById(this.$route.query.spuId).then(res => {
      let data = res.resData;
      Object.assign(this.goods, data);
      this.goods.albumPics = data.albumPics.split("，");
      this.goods.albumPics = this.goods.albumPics.slice(0, 8);
    });
  },
  methods: {
    onClickCart() {
      this.$router.push('cart');
    },
    sorry() {
      this.$toast('暂无后续逻辑~');
    },
    onClickLeft() {
      this.$router.back();
    },
    onAddCart({goodsId = null, skuId = null, selectedNum = null}) {
      this.$toast({
        icon: 'cart-circle-o',
        duration: 600
      });
      console.info("goods:", goodsId, skuId, selectedNum);
    },
    onViewSuk() {
      this.showSuk = true;
    }
  }
};
</script>

<style lang="less">
.van-nav-bar {
  /*头部标签*/

  &__title {
    margin: auto;
  }
}

.goods {
  padding-bottom: 50px;

  &-title {
    font-size: 14px;
    font-weight: bold;
  }

  &-detail {
    font-size: 12px;
    line-height: 1.5;
  }

  &-old-price {
    margin-left: 10px;
  }

  &-express {
    color: #999;
    font-size: 12px;
    padding: 5px 15px;
  }

  &-cell-group {
    margin: 15px 0;

    .van-cell__value {
      color: #999;
    }
  }

  &-tag {
    margin-left: 5px;
  }
}

.remain {
  text-align: right;
}
</style>