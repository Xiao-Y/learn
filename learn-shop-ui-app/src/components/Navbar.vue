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
        show-action
        shape="round"
        @search="onSearch">
      <div slot="action" @click="onSearch">搜索</div>
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
    onSearch() {
      this.$toast("搜索...");
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