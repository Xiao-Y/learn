<template>
  <div>
    <div class="ms-doc">
      <h3>SKU信息</h3>
      <article>
        <el-form ref="goodsSku" :model="goodsSku" label-width="100px" size="mini">
          <el-form-item label="售价" prop="price">
            <el-input v-model="goodsSku.price" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="库存量" prop="stock">
            <el-input v-model="goodsSku.stock" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="goodsSku.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item label="SKU 规格" prop="validInd">
            <custom-sku-spec-select v-for="(item,index) in goodsSkuSpecValuePos"
                                    :select-key-data="selectKeyData"
                                    :spec-key="item.specKeyId"
                                    :spec-value="item.specValueId"
                                    :key="index"
                                    :index="index"
                                    ref="skuSpecSelect"
                                    @change="changeSukSpec"/>
          </el-form-item>
          <el-form-item label="SKU名称" prop="skuName">
            <el-input v-model="goodsSku.skuName" placeholder="请输入内容"></el-input>
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
  import {FindKeyListByCategoryId} from "../../../api/product/GoodsSpecKeyApi";

  import CustomSkuSpecSelect from '../../../components/common/CustomSkuSpecSelect';

  export default {
    components: {
      CustomSkuSpecSelect
    },
    props: {
      spuId: {
        type: String,
        default: null
      },
      skuId: {
        type: String,
        default: null
      },
      categoryId: {
        type: String,
        default: null
      },
      specKeyValue: {
        default: null
      },
      specKeys: {
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
          validInd: true,
        },
        goodsSkuSpecValuePos: [],// custom-sku-spec-select 选种的值
        // maxSkuSpec: 3,// custom-sku-spec-select 组件的最大个数
        selectKeyData: [],// custom-sku-spec-select 子组件中 key 的下拉数据
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
      if (this.specKeyValue) {
        this.goodsSkuSpecValuePos = this.specKeyValue;
      }
      // // 不满足 maxSkuSpec 的，补充完整
      // var length = this.goodsSkuSpecValuePos.length;
      // for (var i = 0; i < (this.maxSkuSpec - length); i++) {
      //   var spec = new Object();
      //   spec.specKeyId = null;
      //   spec.specValueId = null;
      //   spec.specValue = null;
      //   this.goodsSkuSpecValuePos[i + length] = spec;
      // }
      // 加载 custom-sku-spec-select 子组件中 key 的下拉数据
      this.LoadSelectKeyData(this.categoryId);
    },
    methods: {
      //加载下拉列表
      LoadSelectKeyData(categoryId) {
        if (categoryId) {
          FindKeyListByCategoryId(categoryId).then(res => {
            this.selectKeyData = res.resData;
          });
        }
      },
      onSubmit() {
        var _this = this;

        // 整理数据，传送后台
        var pos = [];
        this.goodsSkuSpecValuePos.forEach(item => {
          if (item.specKeyId && item.specValueId) {
            pos.push(item);
          }
        });

        Object.assign(this.goodsSku, {
          "goodsSkuSpecValueVos": pos
        });

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
        // console.info(this.goodsSku)
      },
      onReturn() {
        this.$emit('closeDialog', 'unchanged');
      },
      onReset(goodsSku) {
        this.$refs[goodsSku].resetFields();
      },
      changeSukSpec(index, specKeyId, specValueId, specValue, isCheck) {
        // 不检查时，直接设值
        if (!isCheck) {
          var spec = new Object();
          spec.specKeyId = specKeyId;
          spec.specValueId = specValueId;
          spec.specValue = specValue;
          this.goodsSkuSpecValuePos[index] = spec;
          console.log(this.goodsSkuSpecValuePos);
          this.goodsSku.skuName = this.goodsSkuSpecValuePos
            .filter(f => f.specValue !== null)
            .map(m => m.specValue).join("/");
          return;
        }
        // 校验重复
        var f = false;
        for (var i = 0; i < this.goodsSkuSpecValuePos.length; i++) {
          if (i !== index && specKeyId === this.goodsSkuSpecValuePos[i].specKeyId) {
            f = true;
            break;
          }
        }
        if (!f) {
          var spec = new Object();
          spec.specKeyId = specKeyId;
          spec.specValueId = specValueId;
          spec.specValue = specValue;
          this.goodsSkuSpecValuePos[index] = spec;
        } else {
          specKeyId = this.goodsSkuSpecValuePos[index].specKeyId;
          specValueId = this.goodsSkuSpecValuePos[index].specValueId;
          this.$message({
            showClose: true,
            message: '规格类型不能重复',
            type: 'error'
          });
        }
        // 调用子组件
        this.$refs.skuSpecSelect[index].setSpecKey(specKeyId, specValueId);
        // console.log(this.goodsSkuSpecValuePos);
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
