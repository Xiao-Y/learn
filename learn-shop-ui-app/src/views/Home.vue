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
          v-for="(hotData,index) in goodsDates"
          :key="index"
          @click="toDetails(hotData.id)">
        <van-image :src="hotData.pic" v-lazy="hotData.pic" style="width:6em;height: 6em"/>
      </van-grid-item>
    </van-grid>
    <!-- 推荐商品 -->
    <div class="hot-re">推荐商品</div>
    <!--  商品列表  -->
    <van-list
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
    >
      <div class="goods-list van-clearfix">
        <goods-card v-for="(item,index) in list" :key="index" @toDetails="toDetails" :goods-date="item"/>
      </div>
    </van-list>
    <div class="load-data"></div>
    <tobbar/>
  </div>
</template>

<script>
import {Search} from '@/api/GoodsInfoApi';

import Tobbar from "../components/Tobbar";
import Navbar from "../components/Navbar";
import GoodsCard from "../components/GoodsCard";

export default {
  components: {
    Tobbar, Navbar, GoodsCard
  },
  data() {
    return {
      page: 1,
      list: [],//推荐商品数据
      loading: false,// 当loading为true时，转圈圈
      finished: false,// 数据是否请求结束，结束会先显示- 没有更多了 -
      // 轮播图
      swipeImages: [
        'https://img.yzcdn.cn/vant/apple-1.jpg',
        'https://img.yzcdn.cn/vant/apple-2.jpg',
        'https://img.yzcdn.cn/vant/apple-3.jpg',
        'https://img.yzcdn.cn/vant/apple-4.jpg',
        'https://img.yzcdn.cn/vant/apple-5.jpg',
        'https://img.yzcdn.cn/vant/apple-6.jpg'
      ],
      // 热销商品
      goodsDates: [{
        id: 2,
        recommandStatus: 1,
        goodsName: '资讯类的项目',
        price: 1231,
        lowPrice: 1200,
        subTitle: '数据绑定最常见的形式就是使用“Mustache”语法',
        serviceIds: '2,3,4',
        pic: 'https://img.yzcdn.cn/vant/apple-1.jpg'
      }, {
        id: 3,
        recommandStatus: 1,
        goodsName: '数据绑定最常见的形式就是使用“Mustache”语法',
        price: 1231,
        lowPrice: 1200,
        subTitle: '数据绑定最常见的形式就是使用“Mustache”语法',
        serviceIds: '1,3,4',
        pic: 'https://img.yzcdn.cn/vant/apple-2.jpg'
      }, {
        id: 1,
        recommandStatus: 1,
        goodsName: '资讯类的项目',
        price: 1231,
        lowPrice: 1200,
        subTitle: '数据绑定最常见的形式就是使用“Mustache”语法',
        serviceIds: '1',
        pic: 'https://img.yzcdn.cn/vant/apple-3.jpg'
      }, {
        id: 4,
        recommandStatus: 1,
        goodsName: '资讯类的项目',
        price: 1231,
        lowPrice: 1200,
        subTitle: '数据绑定最常见的形式就是使用“Mustache”语法',
        serviceIds: '1,2',
        pic: 'https://img.yzcdn.cn/vant/apple-4.jpg'
      }, {
        id: 5,
        recommandStatus: 0,
        goodsName: '资讯类的项目',
        price: 1231,
        lowPrice: 1200,
        subTitle: '数据绑定最常见的形式就是使用“Mustache”语法',
        serviceIds: '1,2,3',
        pic: 'https://img.yzcdn.cn/vant/apple-5.jpg'
      }]
    }
  },
  methods: {
    onLoad() {
      Search({}, this.page).then(res => {
        // 加载状态结束
        this.loading = false;
        const tableData = res.resData.tableData;
        console.info(tableData);
        if (tableData && tableData.length > 0) {
          this.list = this.list.concat(tableData);
          // 页码加1
          this.page++;
        } else {
          // 数据全部加载完成
          this.finished = true;
        }
      })
    },
    onSearch() {
      this.$toast("搜索...");
    }
    ,
    onClickLeft() {
      this.$toast("登陆...");
    }
    ,
    onClickRight() {
      this.$toast("二维码...");
    }
    ,
    /**
     * 查看商品详细信息
     * @param spuId 商品id
     */
    toDetails(spuId) {
      this.$router.push({
        name: 'goods',
        query: {
          spuId
        }
      });
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

/*商品列表*/
.goods-list {
  display: flex;
  flex-wrap: wrap;
  padding: 7px;
  justify-content: space-between;
}

.load-data {
  margin-top: 50px;
}
</style>
