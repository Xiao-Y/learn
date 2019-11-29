<template>
  <div>
    <el-form ref="goodsSpu" :model="goodsSpu" label-width="100px" size="mini">
      <div class="ms-doc">
        <h3>商品信息</h3>
        <article>
          <el-form-item label="商品名称" prop="goodsName">
            <el-input v-model="goodsSpu.goodsName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="最低售价" prop="lowPrice">
            <el-input v-model="goodsSpu.lowPrice" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="总库存量" prop="stock">
            <el-input v-model="goodsSpu.stock" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="品牌" prop="brandId">
            <el-input v-model="goodsSpu.brandId" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="分类" prop="categoryId">
            <el-input v-model="goodsSpu.categoryId" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="商品排序" prop="spuSort">
            <el-input v-model="goodsSpu.spuSort" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="goodsSpu.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="success" @click="showSku = true" :disabled="showSku">显示SKU</el-button>
            <el-button type="warning" @click="addSku" :disabled="!showSku">添加SKU</el-button>
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('goodsSpu')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </article>
      </div>
      <br>
      <div class="ms-doc" v-if="showSku">
        <h3>SKU信息</h3>
        <article>
          <good-sku-list :spu-id="goodsSpu.id" :category-id="goodsSpu.categoryId" :show-option="true" ref="GoodsSkuListRef"/>
        </article>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {Update, Add} from "../../../api/product/GoodsSpuApi";

  import GoodSkuList from '../GoodsSkuList';

  export default {
    components: {
      GoodSkuList
    },
    data() {
      return {
        showSku: false,// 是否显示sku信息
        optionType: '', // 操作类型，edit,add
        goodsSpu: {
          id: null,
          goodsName: null,
          lowPrice: 0.00,
          stock: 0,
          brandId: null,
          categoryId: null,
          validInd: true,
          spuSort: 999
        }
      }
    },
    created() {
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit') {
        this.goodsSpu = JSON.parse(this.$route.query.goodsSpuEdit);
      }
    },
    methods: {
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit') {
          Update(_this.goodsSpu).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            this.$bus.emit('notifyInfo', res.resData);
            _this.$router.back(-1);
          });
        } else { // add
          Add(_this.goodsSpu).then(res => {
            _this.$message({
              type: 'success',
              message: '保存成功!'
            });
            this.$bus.emit('notifyInfo', res.resData);
            _this.$router.back(-1);
          });
        }
      },
      addSku(){
        this.$refs.GoodsSkuListRef.handleAdd();
      },
      onReturn() {
        this.$router.back(-1);
      },
      onReset(goodsSpu) {
        this.$refs[goodsSpu].resetFields();
      }
    }
  };
</script>

<style scoped>
  .ms-doc {
    width: 70%;
    margin: 0 auto;
  }

  .ms-doc h3 {
    padding: 9px 10px 10px;
    margin: 0;
    font-size: 14px;
    line-height: 17px;
    background-color: #f5f5f5;
    border: 1px solid #d8d8d8;
    border-bottom: 0;
    border-radius: 3px 3px 0 0;
  }

  .ms-doc article {
    padding: 10px;
    word-wrap: break-word;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px;
  }

  .ms-doc article .el-checkbox {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }
</style>
