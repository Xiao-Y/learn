<template>
  <div>
    <!-- vant搜索 -->
    <van-nav-bar :left-arrow="false">
      <van-icon name="arrow-left" slot="left" size="1.5em" @click="$router.back()"/>
      <van-search slot="title" @search="toSearch" autofocus shape="round" v-model="kw"
                  placeholder="请输入搜索关键词">
      </van-search>
    </van-nav-bar>
    <!-- 排序 -->
    <div class="list-sort">
      <div @click="onSort" :class="{'active':isActive==0}">综合排序</div>
      <div @click="saleSort" :class="{'active':isActive==1}">销量</div>
      <div @click="priceSort" style="display: flex;">
        <div>价格</div>
        <div style="margin-left: 5px;">
          <div :class="{'active':isActive==2}" class="iconfont icon-jiantou"></div>
          <div :class="{'active':isActive==3}" class="iconfont icon-jiantouxia"></div>
        </div>
      </div>
      <div><!-- 下拉菜单 -->
        <van-dropdown-menu>
          <van-dropdown-item v-model="value1" :options="option1" @change="screen"/>
        </van-dropdown-menu>
      </div>
    </div>

    <!-- vant骨架屏 -->
    <van-skeleton v-for="i in 18" :key="i" title :row="3" :loading="list.length<=0" style="background-color: white;"/>

    <div style="margin-top: 96px;">
      <!-- 商品卡片 -->
      <van-card
          v-for="(item,index) in list"
          :key="index"
          :tag="item.newStatus == 1 ? '天猫':'淘宝'"
          :price="item.lowPrice | priceFormat"
          :origin-price="item.price | priceFormat"
          :thumb="item.pic"
          @click="toDetails(item.id)">
        <template #num>
          <span>30天销量：{{ item.sale }}</span>
        </template>
        <template #title>
          <span v-html="item.goodsName"/>
        </template>
        <template #desc>
          <span v-html="item.subTitle"/>
        </template>
        <template #tags>
          <span v-html="item.detailTitle"/>
        </template>
      </van-card>
    </div>

  </div>
</template>

<script>
import {Search} from "@/api/GoodsInfoApi";

export default {
  data() {
    return {
      kw: '',
      list: [],
      value1: -1,
      option1: [
        {text: '店铺类型', value: -1},
        {text: '淘宝', value: 0},
        {text: '天猫', value: 1}
      ],
      isActive: -1 //0综合排序，1销量，2价格升序，3价格降序
    }
  },
  mounted() {
    this.kw = this.$route.query.kw //把搜索页面搜索的值传过来
    // let id = this.$route.query.id
    Search({"keyWorlds": this.kw}).then(res => {
      this.list = res.resData.tableData;
    });
  },
  methods: {
    toSearch() { //跳转到搜索页面
      this.$router.push({
        path: '/search'
      })
    },
    toDetails(id) { //跳转到详情页面
      this.$router.push({
        path: '/details',
        query: {
          id
        }
      })
    },
    priceSort() { //价格排序
      if (this.isActive < 2) {
        this.isActive = 2
        this.list.sort((a, b) => {
          return a.actualPrice - b.actualPrice
        })
      } else if (this.isActive == 2) {
        this.isActive = 3
        this.list.sort((a, b) => {
          return b.actualPrice - a.actualPrice
        })
      } else if (this.isActive == 3) {
        this.isActive = 2
        this.list.sort((a, b) => {
          return a.actualPrice - b.actualPrice
        })
      }
    },
    onSort() { //综合排序
      this.isActive = 0
      this.list.sort((a, b) => {
        return b.shopLevel - a.shopLevel
      })
    },
    saleSort() { //销量排序
      this.isActive = 1
      this.list.sort((a, b) => {
        return b.monthSales - a.monthSales
      })
    },
    screen(value) { //筛选
      this.isActive = -1
      this.list = []

      if (value == -1) {
        this.$axios.get('http://api.kudesoft.cn/tdk/goods').then(res => {
          this.list = res.data.data.data.list
        })
      } else {
        this.$axios.get("http://api.kudesoft.cn/tdk/goods").then(res => {
          let list = res.data.data.data.list
          list.map(item => {
            if (item.shopType == value) {
              this.list.push(item)
            }
          })
        })
      }
    }
  },
  filters: {
    priceFormat: function (price) {
      return (price / 100).toFixed(2);
    }
  }
}
</script>

<style scoped>
.van-nav-bar__title {
  margin-left: 45px;
  max-width: 80%;
}

.iconfont {
  font-size: 8px;
  margin: 0 3px;
}

.active {
  color: red;
}

.list-sort {
  position: fixed;
  top: 54px;
  left: 0;
  z-index: 99;
  width: 100%;
  background-color: #FFFFFF;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: -1px;
  font-weight: 300;
}

.van-card__content span {
  padding-top: 4px;
}
</style>