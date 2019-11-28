<template>
    <div class="goods">
        <van-nav-bar
                title="商品信息"
                left-text="返回"
                left-arrow
                @click-left="onClickLeft"/>
        <van-swipe class="goods-swipe" :autoplay="3000">
            <van-swipe-item v-for="thumb in goods.thumb" :key="thumb">
                <img v-lazy="thumb">
            </van-swipe-item>
        </van-swipe>

        <van-cell-group>
            <van-cell>
                <div class="goods-title">{{ goods.title }}</div>
                <div class="goods-price">{{ formatPrice(goods.price) }}</div>
            </van-cell>
            <van-cell class="goods-express">
                <van-col span="10">
                    发货：<van-icon name="location-o"/> 
                    {{ goods.address }}
                </van-col>
                <van-col span="6">运费：{{ goods.express }}</van-col>
                <van-col class="remain" span="8">剩余：{{ goods.remain }}</van-col>
            </van-cell>
        </van-cell-group>
        <van-cell-group class="goods-cell-group">
            <van-cell title="保障" is-link @click="sorry">七天包退</van-cell>
        </van-cell-group>
        <!-- <van-cell-group class="goods-cell-group">
            <van-cell value="进入店铺" icon="shop-o" is-link @click="sorry">
                <template slot="title">
                    <span class="van-cell-text">有赞的店</span>
                    <van-tag class="goods-tag" type="danger">官方</van-tag>
                </template>
            </van-cell>
        </van-cell-group> -->

        <van-cell-group class="goods-cell-group">
            <van-cell title="选择" icon="location-o" is-link @click="onViewSuk"/>
            <van-cell title="参数" is-link @click="sorry"/>
        </van-cell-group>

        <van-cell-group class="goods-cell-group">
            <van-cell title="宝贝评价" is-link @click="sorry">暂无评价</van-cell>
        </van-cell-group>

        <van-goods-action>
            <van-goods-action-icon icon="chat-o" @click="sorry">
                客服 
            </van-goods-action-icon>
            <van-goods-action-icon icon="cart-o" @click="onClickCart">
                购物车
            </van-goods-action-icon>
            <van-goods-action-button type="warning" @click="onAddCart">
                加入购物车
            </van-goods-action-button>
            <van-goods-action-button type="danger" @click="onBuyNow">
                立即购买
            </van-goods-action-button>
        </van-goods-action>

        <!-- sku -->
        <coustomSuk v-model="showSuk" v-if="showSuk"/>
    </div>
    
</template>

<script>

    import CoustomSuk from '../components/Sku';

    export default {
        components:{
            CoustomSuk
        },
        data() {
            return {
                goods: {
                    title: '美国伽力果（约680g/3个）',
                    price: 2680,
                    express: '免运费',
                    address: '武汉',
                    remain: 19,
                    thumb: [
                        'https://img.yzcdn.cn/public_files/2017/10/24/e5a5a02309a41f9f5def56684808d9ae.jpeg',
                        'https://img.yzcdn.cn/public_files/2017/10/24/1791ba14088f9c2be8c610d0a6cc0f93.jpeg'
                    ]
                },
                showSuk:false
            };
        },
        methods: {
            formatPrice() {
                return '¥' + (this.goods.price / 100).toFixed(2);
            },
            onClickCart() {
                this.$router.push('cart');
            },
            sorry() {
                this.$toast('暂无后续逻辑~');
            },
            onClickLeft() {
                this.$router.back();
            },
            onAddCart() {
                this.$toast({
                    icon: 'cart-circle-o',
                    duration: 600
                });
            },
            onBuyNow() {

            },
            onViewSuk(){
                this.showSuk = true;
            }
        }
    };
</script>

<style lang="less">
    .goods {
        padding-bottom: 50px;

        &-swipe {
            img {
                width: 100%;
                display: block;
            }
        }

        &-title {
            font-size: 16px;
        }

        &-price {
            color: #f44;
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

    .remain{
        text-align: right;
    }
</style>