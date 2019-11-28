<template>
    <div class="home">
        <!-- 标题 -->
        <van-nav-bar
                :left-arrow="false"
                @click-left="onClickLeft"
                @click-right="onClickRight">
            <van-icon name="user-o" slot="left" size="1.5em"/>
            <van-search
                    slot="title"
                    v-model="searchKey"
                    placeholder="请输入搜索关键词"
                    show-action
                    shape="round"
                    @search="onSearch">
                <div slot="action" @click="onSearch">搜索</div>
            </van-search>
            <van-icon name="qr-invalid" slot="right" size="1.5em"/>
        </van-nav-bar>
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
                <van-image :src="hotData.image" v-lazy="hotData.image" style="width:6em;height: 6em"/>
            </van-grid-item>
        </van-grid>
        <!-- 推荐商品 -->
        <div class="hot-re">推荐商品</div>
        <van-card
                v-for="(hotData,index) in hotDatas"
                :key="index"
                tag="HOT"
                price="2.00"
                :lazy-load="true"
                @click="viewProduct(hotData.id)"
                :thumb="hotData.image">
            <div slot="tags">
                <van-tag plain type="danger">标签</van-tag>
                <van-tag plain type="danger">标签</van-tag>
            </div>
            <div slot="desc">描述信息</div>
            <div slot="title">商品标题</div>
        </van-card>

        <tobbar/>
    </div>
</template>

<script>
import Tobbar from "../components/Tobbar";
    export default {
        components: {
            Tobbar
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
                    id: '1',
                    text: '',
                    image: 'https://img.yzcdn.cn/vant/apple-1.jpg'
                }, {
                    id: '2',
                    text: '',
                    image: 'https://img.yzcdn.cn/vant/apple-2.jpg'
                }, {
                    id: '3',
                    text: '',
                    image: 'https://img.yzcdn.cn/vant/apple-3.jpg'
                }, {
                    id: '4',
                    text: '',
                    image: 'https://img.yzcdn.cn/vant/apple-4.jpg'
                }, {
                    id: '5',
                    text: '',
                    image: 'https://img.yzcdn.cn/vant/apple-5.jpg'
                }, {
                    id: '6',
                    text: '',
                    image: 'https://img.yzcdn.cn/vant/apple-6.jpg'
                }]
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
            viewProduct() {
                this.$router.push({name: 'goods'});
            }
        }
    }
</script>

<style>
    .hot-re {
        padding-top: 1em;
        margin-left: 0.2em;
        color: #8c939d;
        font-size: 14px;
    }
    .van-tag{
        margin-top: 2px;
        margin-right: 5px;
    }
</style>
