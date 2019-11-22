<template>
    <div class="cart">
        <van-nav-bar
                title="购物车"
                left-text="返回"
                :right-text="rightText"
                left-arrow
                @click-right="onClickRight"
                @click-left="onClickLeft"
        />
        <div>
            <van-checkbox-group class="card-goods" v-model="checkedGoods" ref="itemCheckGroup">
                <div v-for="item in goods" :key="item.id" :name="item.id" class="card-goods__item">
                    <van-checkbox ref="itemCheck" :key="item.id" :name="item.id"/>
                    <van-card
                            :title="item.title"
                            :desc="item.desc"
                            :num="item.num"
                            :price="formatPrice(item.price)"
                            :thumb="item.thumb"
                    >
                        <div slot="num">
                            <van-stepper v-model="item.num"/>
                        </div>
                    </van-card>
                </div>
            </van-checkbox-group>

            <van-submit-bar
                    v-if="!showDel"
                    :price="totalPrice"
                    :disabled="!checkedGoods.length"
                    :button-text="submitBarText"
                    @submit="onSubmit"
            >
                <van-checkbox class="checkbox_all" v-model="checkAll" @change="onCheckAll">全选</van-checkbox>
            </van-submit-bar>

            <van-button v-if="showDel" type="danger" class="del_btn" @click="delGoods">删除</van-button>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                checkAll: false,
                showDel: false,
                rightText: "编辑",
                checkedGoods: [],
                goods: [
                    {
                        id: "1",
                        title: "进口香蕉",
                        desc: "约250g，2根",
                        price: 200,
                        num: 1,
                        thumb:
                            "https://img.yzcdn.cn/public_files/2017/10/24/2f9a36046449dafb8608e99990b3c205.jpeg"
                    },
                    {
                        id: "2",
                        title: "陕西蜜梨",
                        desc: "约600g",
                        price: 690,
                        num: 1,
                        thumb:
                            "https://img.yzcdn.cn/public_files/2017/10/24/f6aabd6ac5521195e01e8e89ee9fc63f.jpeg"
                    },
                    {
                        id: "3",
                        title: "美国伽力果",
                        desc: "约680g/3个",
                        price: 2680,
                        num: 1,
                        thumb:
                            "https://img.yzcdn.cn/public_files/2017/10/24/320454216bbe9e25c7651e1fa51b31fd.jpeg"
                    }
                ]
            };
        },
        created() {
            //   this.checkedGoods = [];
            //   console.info(this.goods.map(m=>m.id));
            //   this.checkedGoods = this.goods.map(m=>m.id);
        },
        computed: {
            submitBarText() {
                const count = this.checkedGoods.length;
                return "结算" + (count ? `(${count})` : "");
            },
            totalPrice() {
                return this.goods.reduce(
                    (total, item) =>
                        total +
                        (this.checkedGoods.indexOf(item.id) !== -1
                            ? item.price * item.num
                            : 0),
                    0
                );
            }
        },
        methods: {
            formatPrice(price) {
                return (price / 100).toFixed(2);
            },
            onSubmit() {
                this.$toast("点击结算");
            },
            onClickLeft() {
                var val = this.$route.query.val;
                if (val === "home") {
                    this.$router.push("/");
                } else {
                    this.$router.back();
                }
            },
            onClickRight() {
                this.showDel = !this.showDel;
                if (this.showDel) {
                    this.rightText = "完成";
                } else {
                    this.rightText = "编辑";
                }
            },
            onCheckAll(checked) {
                if (checked) {
                    this.$refs.itemCheckGroup.toggleAll(true);
                } else {
                    this.$refs.itemCheckGroup.toggleAll();
                }
            },
            delGoods() {
                this.checkedGoods.forEach(f => {
                    var index = this.goods.findIndex(g => g.id === f);
                    this.goods.splice(index, 1);
                });
            }
        }
    };
</script>

<style lang="less">
    .van-card__header {
        margin-left: 15px;
    }

    .card-goods {
        padding: 10px 0;
        background-color: #fff;

        &__item {
            position: relative;
            background-color: #c7b9b941;
            margin-left: 10px;

            .van-checkbox__label {
                width: 100%;
                height: auto; // temp
                padding: 0 10px 0 15px;
                box-sizing: border-box;
            }

            .van-checkbox__icon {
                top: 50%;
                left: 2px;
                z-index: 1;
                position: absolute;
                margin-top: -10px;
            }

            .van-card__price {
                color: #f44;
            }
        }
    }

    .del_btn {
        width: 100%;
        position: fixed;
        bottom: 0px;
    }

    .checkbox_all {
        margin-left: 10px;
    }
</style>
