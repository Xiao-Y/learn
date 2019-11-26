// 导航栏
<template>
  <van-tabbar route>
    <van-tabbar-item replace to="/home" icon="home-o">首页</van-tabbar-item>
    <van-tabbar-item replace to="/category" icon="apps-o">分类</van-tabbar-item>
    <van-tabbar-item replace to="/friends" icon="fire-o" info="5">推荐</van-tabbar-item>
    <van-tabbar-item replace to="/cart?val=home" icon="shopping-cart-o" info="20">购物车</van-tabbar-item>
    <van-tabbar-item replace to="/profile" icon="user-o">我的</van-tabbar-item>
  </van-tabbar>
</template>