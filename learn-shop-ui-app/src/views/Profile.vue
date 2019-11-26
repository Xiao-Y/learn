<template>
    <div class="my">
        <van-panel title="我的">
            <div style="text-align: center">
                <van-image
                        round
                        width="4rem"
                        height="4rem"
                        src="https://img.yzcdn.cn/vant/cat.jpeg"/>
            </div>
        </van-panel>
        <van-grid :column-num="4">
            <van-grid-item icon="ecard-pay" text="待付款"/>
            <van-grid-item icon="logistics" text="待发货"/>
            <van-grid-item icon="notes-o" text="待评价"/>
            <van-grid-item icon="after-sale" text="退回/售后"/>
        </van-grid>
        <van-cell-group class="user-group">
            <van-cell icon="orders-o" title="全部订单" is-link/>
        </van-cell-group>
        <van-grid :column-num="4">
            <van-grid-item icon="points" text="我的积分"/>
            <van-grid-item icon="star-o" text="我的收藏"/>
            <van-grid-item icon="location-o" text="地址管理" @click="location"/>
            <van-grid-item icon="balance-o" text="帐户余额"/>
        </van-grid>
        <!-- 优惠券单元格 -->
        <van-coupon-cell
                :coupons="coupons"
                :chosen-coupon="chosenCoupon"
                @click="showList = true"/>
        <!-- 优惠券列表 -->
        <van-popup v-model="showList" position="bottom">
            <van-coupon-list
                    :coupons="coupons"
                    :chosen-coupon="chosenCoupon"
                    :disabled-coupons="disabledCoupons"
                    @change="onChange"
                    @exchange="onExchange"/>
        </van-popup>

        <tobbar/>
    </div>
</template>

<script>
import Tobbar from "../components/Tobbar";
    const coupon = {
        available: 1,
        condition: '无使用门槛\n最多优惠12元',
        reason: '',
        value: 150,
        name: '优惠券名称',
        startAt: 1489104000,
        endAt: 1514592000,
        valueDesc: '1.5',
        unitDesc: '元'
    };

    export default {
        components: {
            Tobbar
        },
        data() {
            return {
                activeNames: [],
                chosenCoupon: -1,
                coupons: [coupon],
                disabledCoupons: [coupon],
                showList: false
            }
        },
        methods: {
            onChange(index) {
                this.showList = false;
                this.chosenCoupon = index;
            },
            onExchange() {
                this.coupons.push(coupon);
            },
            location() {
                this.$router.push({name: 'addressList'});
            }
        }
    }
</script>

<style lang="less">
    .user {
        &-group {
            margin-bottom: 15px;
        }
    }
</style>
