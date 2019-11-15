<template>
    <div id="app">
        <router-view/>
        <van-tabbar route>
            <van-tabbar-item replace to="/home" icon="home-o">首页</van-tabbar-item>
            <van-tabbar-item replace to="/search" icon="apps-o">分类</van-tabbar-item>
            <van-tabbar-item replace to="/friends" icon="fire-o" info="5">推荐</van-tabbar-item>
            <van-tabbar-item replace to="/setting" icon="shopping-cart-o" info="20">购物车</van-tabbar-item>
            <van-tabbar-item replace to="/my" icon="user-o">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<style>

</style>
