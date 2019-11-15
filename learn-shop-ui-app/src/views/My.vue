<template>
    <div class="my">
        <van-panel title="我的">
            <div style="text-align: center">
                <van-image
                        round
                        width="4rem"
                        height="4rem"
                        src="https://img.yzcdn.cn/vant/cat.jpeg"
                />
            </div>
        </van-panel>
        <van-grid :column-num="5">
            <van-grid-item icon="ecard-pay" text="待付款"/>
            <van-grid-item icon="logistics" text="待发货"/>
            <van-grid-item icon="notes-o" text="待评价"/>
            <van-grid-item icon="after-sale" text="退回/售后"/>
            <van-grid-item icon="orders-o" text="全部订单"/>
        </van-grid>
        <br>
        <van-grid :column-num="5">
            <van-grid-item icon="points" text="我的积分"/>
            <van-grid-item icon="pending-payment" text="我的优惠券"/>
            <van-grid-item icon="star-o" text="我的收藏"/>
            <van-grid-item icon="location-o" text="地址管理" @click="location"/>
            <van-grid-item icon="balance-o" text="帐户余额"/>
        </van-grid>
        <br>
        <van-collapse v-model="activeNames">
            <van-collapse-item title="我的积分" name="1">内容</van-collapse-item>
            <van-collapse-item title="我的优惠券" name="2">内容</van-collapse-item>
            <van-collapse-item title="我收到的礼物" name="3">内容</van-collapse-item>
        </van-collapse>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                activeNames: []
            }
        },
        methods: {
            location() {
                this.$router.push({name: 'addressList'});
            }
        }
    }
</script>
