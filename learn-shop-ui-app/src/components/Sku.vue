<template>
  <van-sku
      ref="sku"
      v-model="show"
      :sku="sku"
      :goods="goods"
      :goods-id="goodsData.id"
      show-add-cart-btn
      reset-stepper-on-hide
      :initial-sku="initialSku"
  >
    <!-- 自定义 sku-header-price -->
    <template slot="sku-header-price" slot-scope="props">
      <div class="van-sku__goods-price">
        <span class="van-sku__price-symbol">￥</span>
        <span class="van-sku__price-num">{{ props.price }}</span>
      </div>
    </template>

    <!-- 自定义 sku actions -->
    <template slot="sku-actions">
      <div class="van-sku-actions">
        <van-button square size="large" type="warning" @click="onAddCart">加入购物车</van-button>
        <van-button square size="large" type="danger" @click="onBuyNow">立即购买</van-button>
      </div>
    </template>
  </van-sku>
</template>

<script>

import {FindGoodsSku, FindSkuSpec} from '@/api/GoodsSkuApi';

export default {
  model: {
    // 双向绑定
    prop: 'showSku',
    event: 'change'
  },
  props: {
    showSku: {
      type: Boolean,
      default: false
    },
    goodsData: {
      type: Object,
      default: () => ({
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
      })
    }
  },
  data() {
    return {
      show: false,
      sku: {
        tree: [],
        list: [],
        // // 所有sku规格类目与其值的从属关系，比如商品有颜色和尺码两大类规格，颜色下面又有红色和蓝色两个规格值。
        // // 可以理解为一个商品可以有多个规格类目，一个规格类目下可以有多个规格值。
        // tree: [{
        //     "k": "颜色", // skuKeyName：规格类目名称
        //     "v": [{
        //         id: "30349", // skuValueId：规格值 id
        //         name: "红色", // skuValueName：规格值名称
        //         imgUrl: "https://img.yzcdn.cn/1.jpg", // 规格类目图片，只有第一个规格类目可以定义图片
        //         previewImgUrl: "https://img.yzcdn.cn/1p.jpg" // 用于预览显示的规格类目图片
        //     }, {
        //         id: "30323",
        //         name: "蓝色",
        //         imgUrl: "https://img.yzcdn.cn/2.jpg",
        //         previewImgUrl: "https://img.yzcdn.cn/2p.jpg"
        //     }],
        //     "k_s": "s1" // skuKeyStr：sku 组合列表（下方 list）中当前类目对应的 key 值，value 值会是从属于当前类目的一个规格值 id
        // }, {
        //     "k": "型号", // skuKeyName：规格类目名称
        //     "v": [{
        //         id: "40322", // skuValueId：规格值 id
        //         name: "AA", // skuValueName：规格值名称
        //         imgUrl: "https://img.yzcdn.cn/1.jpg", // 规格类目图片，只有第一个规格类目可以定义图片
        //         previewImgUrl: "https://img.yzcdn.cn/1p.jpg" // 用于预览显示的规格类目图片
        //     }, {
        //         id: "40388",
        //         name: "BB",
        //         imgUrl: "https://img.yzcdn.cn/2.jpg",
        //         previewImgUrl: "https://img.yzcdn.cn/2p.jpg"
        //     }],
        //     "k_s": "s2" // skuKeyStr：sku 组合列表（下方 list）中当前类目对应的 key 值，value 值会是从属于当前类目的一个规格值 id
        // }],
        // // 所有 sku 的组合列表，比如红色、M 码为一个 sku 组合，红色、S 码为另一个组合
        // list: [{
        //     id: 2259, // skuId，下单时后端需要
        //     price: 100, // 价格（单位分）
        //     "s1": "30349", // 规格类目 k_s 为 s1 的对应规格值 id
        //     "s2": "40322", // 规格类目 k_s 为 s2 的对应规格值 id
        //     "s3": "0", // 最多包含3个规格值，为0表示不存在该规格
        //     stock_num: 10 // 当前 sku 组合对应的库存
        // }, {
        //     id: 2270, // skuId，下单时后端需要
        //     price: 100, // 价格（单位分）
        //     s1: "30349", // 规格类目 k_s 为 s1 的对应规格值 id
        //     s2: "40388", // 规格类目 k_s 为 s2 的对应规格值 id
        //     s3: "0", // 最多包含3个规格值，为0表示不存在该规格
        //     stock_num: 1210 // 当前 sku 组合对应的库存
        // }, {
        //     id: 2223, // skuId，下单时后端需要
        //     price: 100, // 价格（单位分）
        //     s1: "30323", // 规格类目 k_s 为 s1 的对应规格值 id
        //     s2: "40388", // 规格类目 k_s 为 s2 的对应规格值 id
        //     s3: "0", // 最多包含3个规格值，为0表示不存在该规格
        //     stock_num: 330 // 当前 sku 组合对应的库存
        // }, {
        //     id: 2255, // skuId，下单时后端需要
        //     price: 100, // 价格（单位分）
        //     s1: "30323", // 规格类目 k_s 为 s1 的对应规格值 id
        //     s2: "40322", // 规格类目 k_s 为 s2 的对应规格值 id
        //     s3: "0", // 最多包含3个规格值，为0表示不存在该规格
        //     stock_num: 110 // 当前 sku 组合对应的库存
        // }],
        messages: [{
          // 商品留言
          datetime: "0", // 留言类型为 time 时，是否含日期。'1' 表示包含
          multiple: "0", // 留言类型为 text 时，是否多行文本。'1' 表示多行
          name: "备注", // 留言名称
          type: "text", // 留言类型，可选: id_no（身份证）, text, tel, date, time, email
          required: "0", // 是否必填 '1' 表示必填
          placeholder: "留言其它搭配" // 可选值，占位文本
        }],
        price: 999999, // 默认价格（单位元）
        stock_num: 999999, // 商品总库存
        collection_id: 0, // 无规格商品 skuId 取 collection_id，否则取所选 sku 组合对应的 id
        none_sku: false, // 是否无规格商品
        hideStock: false, // 是否隐藏剩余库存
      },
      // 初始化 sku 数据
      initialSku: {
        // 初始选中数量
        selectedNum: 1
      },
      goods: {
        // 商品标题
        title: "测试商品",
        // 默认商品 sku 缩略图
        picture: "https://img.yzcdn.cn/1.jpg"
      }
    };
  },
  created() {
    this.show = this.showSku;
    // 商品spuId
    const goodsId = this.goodsData.id;
    // 商品信息
    this.sku.price = this.goodsData.lowPrice / 100;
    this.sku.stock_num = this.goodsData.stock;
    this.sku.collection_id = goodsId;
    this.goods.title = this.goodsData.goodsName;

    // 查询可以选择的搭配
    FindGoodsSku(goodsId).then(res => {
      this.sku.list = res.resData;
    });
    // 查询商品所有规格
    FindSkuSpec(goodsId).then(res => {
      this.sku.tree = res.resData;
      // 如果商品无规格时，设置为 无规格商品
      this.sku.none_sku = this.sku.tree.length === 0;
    });
  },
  methods: {
    genData() {
      const {goodsId, selectedNum, selectedSkuComb} = this.$refs.sku.getSkuData();
      const skuId = selectedSkuComb ? selectedSkuComb.id : null;
      return {goodsId: goodsId, skuId: skuId, selectedNum: selectedNum};
    },
    onBuyNow() {
      this.$emit("onAddCart", this.genData());
    },
    onAddCart() {
      this.$emit("onAddCart", this.genData());
    }
  },
  watch: {
    show() {
      if (!this.show) {
        this.$emit('change', this.show);
      }
    }
  }
};
</script>