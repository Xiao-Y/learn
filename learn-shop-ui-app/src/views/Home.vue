<template>
  <div class="home">
    <!-- 标题 -->
    <navbar/>
    <!-- 轮播图 -->
    <van-swipe :autoplay="3000">
      <van-swipe-item v-for="(image, index) in swipeImages" :key="index">
        <img v-lazy="image" style="width:100%;height: 12em"/>
      </van-swipe-item>
    </van-swipe>
    <!-- 热销 -->
    <div class="hot-re">热销商品</div>
    <van-grid :column-num="2">
      <van-grid-item
          v-for="(hotData,index) in hotDatas"
          :key="index"
          @click="viewProduct(hotData.id)">
        <van-image :src="hotData.pic" v-lazy="hotData.pic" style="width:6em;height: 6em"/>
      </van-grid-item>
    </van-grid>
    <!-- 推荐商品 -->
    <div class="hot-re">推荐商品</div>
    <!--  商品列表  -->
    <GoodsCard @viewProduct="viewProduct" :goods="hotDatas"/>
    <div class="load-data"></div>
    <tobbar/>
  </div>
</template>

<script>
import Tobbar from "../components/Tobbar";
import Navbar from "../components/Navbar";
import GoodsCard from "../components/GoodsCard";

export default {
  components: {
    Tobbar, Navbar, GoodsCard
  },
  data() {
    return {
      // 轮播图
      swipeImages: [
        'https://img.yzcdn.cn/vant/apple-1.jpg',
        'https://img.yzcdn.cn/vant/apple-2.jpg',
        'https://img.yzcdn.cn/vant/apple-3.jpg',
        'https://img.yzcdn.cn/vant/apple-4.jpg',
        'https://img.yzcdn.cn/vant/apple-5.jpg',
        'https://img.yzcdn.cn/vant/apple-6.jpg'
      ],
      // 搜索关键字
      searchKey: '',
      // 热销商品
      hotDatas: [{
        id: 2,
        recommandStatus: 1,
        goodsName: '1231',
        price: 1231,
        lowPrice: 1200,
        subTitle: '1231',
        serviceIds: '2,3,4',
        pic: 'https://img.yzcdn.cn/vant/apple-1.jpg'
      }, {
        id: 3,
        recommandStatus: 1,
        goodsName: '1231',
        price: 1231,
        lowPrice: 1200,
        subTitle: '1231',
        serviceIds: '1,3,4',
        pic: 'https://img.yzcdn.cn/vant/apple-2.jpg'
      }, {
        id: 1,
        recommandStatus: 1,
        goodsName: '1231',
        price: 1231,
        lowPrice: 1200,
        subTitle: '1231',
        serviceIds: '1',
        pic: 'https://img.yzcdn.cn/vant/apple-3.jpg'
      }, {
        id: 4,
        recommandStatus: 1,
        goodsName: '1231',
        price: 1231,
        lowPrice: 1200,
        subTitle: '1231',
        serviceIds: '1,2',
        pic: 'https://img.yzcdn.cn/vant/apple-4.jpg'
      }, {
        id: 5,
        recommandStatus: 0,
        goodsName: '1231',
        price: 1231,
        lowPrice: 1200,
        subTitle: '1231',
        serviceIds: '1,2,3',
        pic: 'https://img.yzcdn.cn/vant/apple-5.jpg'
      }, {
        id: 6,
        recommandStatus: 0,
        goodsName: '1231',
        price: 1231,
        lowPrice: 1200,
        subTitle: '1231',
        serviceIds: '1,2,4',
        pic: 'https://img.yzcdn.cn/vant/apple-6.jpg'
      },]
    }
  },
  methods: {
    onSearch() {
      this.$toast("搜索...");
    },
    onClickLeft() {
      this.$toast("登陆...");
    },
    onClickRight() {
      this.$toast("二维码...");
    },
    /**
     * 查看商品详细信息
     * @param spuId 商品id
     */
    viewProduct(spuId) {
      //this.$router.push({name: 'goods'});
      console.info("spuId:", spuId);
    }
  }
}
</script>

<style scoped lang="scss">
.hot-re {
  padding-top: 1em;
  margin-left: 0.2em;
  color: #8c939d;
  font-size: 14px;
}

.load-data {
  margin-top: 50px;
}
</style>
