<template>
  <div>
    <div class="ms-doc">
      <h3>SKU信息</h3>
      <article>
        <el-form ref="goodsSku" :model="goodsSku" label-width="100px" size="mini">
          <el-form-item label="SKU名称" prop="skuName">
            <el-input v-model="goodsSku.skuName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="售价" prop="price">
            <el-input v-model="goodsSku.price" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="库存量" prop="stock">
            <el-input v-model="goodsSku.stock" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="goodsSku.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('goodsSku')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>

    </div>

  </div>
</template>

<script>
  import {Update, Add, GetById} from "../../../api/product/GoodsSkuApi";

  export default {
    props: {
      spuId: {
        type: String,
        default: null
      },
      skuId: {
        type: String,
        default: null
      }
    },
    data() {
      return {
        optionType: 'add', // 操作类型，edit,add
        goodsSku: {
          id: null,
          skuName: null,
          price: 0.00,
          stock: 0,
          spuId: null,
          validInd: true
        }
      }
    },
    created() {
      if (this.skuId) {
        GetById(this.skuId).then(res => {
          this.goodsSku = res.resData;
          this.optionType = 'edit';
        });
      }
      this.goodsSku.spuId = this.spuId;
    },
    methods: {
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit') {
          Update(_this.goodsSku).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            this.$bus.emit('notifyInfo', res.resData);
            this.$emit('closeDialog', 'refresh');
          });
        } else { // add
          Add(_this.goodsSku).then(res => {
            _this.$message({
              type: 'success',
              message: '保存成功!'
            });
            this.$bus.emit('notifyInfo', res.resData);
            this.$emit('closeDialog', 'refresh');
          });
        }
      },
      onReturn() {
        this.$emit('closeDialog', 'unchanged');
      },
      onReset(goodsSku) {
        this.$refs[goodsSku].resetFields();
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
