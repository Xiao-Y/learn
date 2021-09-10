<!-- 标题 -->
<template>
  <van-nav-bar
      :left-arrow="false"
      @click-left="onClickLeft"
      @click-right="onClickRight">
    <van-icon name="user-o" slot="left" size="1.5em"/>
    <van-search
        slot="title"
        v-model="keyWorlds"
        placeholder="请输入搜索关键词"
        shape="round"
        @focus="toSearch">
    </van-search>
    <van-icon name="qr-invalid" slot="right" size="1.5em"/>
  </van-nav-bar>
</template>

<script>

import {Search} from '@/api/GoodsInfoApi';

export default {
  data() {
    return {
      // 搜索关键字
      keyWorlds: '',
    };
  },
  created() {
  },
  methods: {
    toSearch() {
      this.$router.push("/search");
    },
    onSearch() {
      this.$toast("搜索...");
      this.$router.push("/goodsList");
      Search({"keyWorlds": this.keyWorlds}).then(res => {
        console.info(res.resData);
      });
    },
    onClickLeft() {
      this.$toast("登陆...");
    },
    onClickRight() {
      this.$toast("二维码...");
    },
    viewProduct() {
      this.$router.push({name: 'goods'});
    }
  },
  watch: {}
};
</script>

<style>
.van-nav-bar {
  line-height: 40px;
}

.van-nav-bar__title {
  margin-left: 45px;
  max-width: 77%;
}
</style>