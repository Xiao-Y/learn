<template>
  <div>
    <el-form ref="infoModel" :model="infoModel" label-width="100px" size="mini">
      <div class="ms-doc">
        <h3>分类信息</h3>
        <article>
          <el-form-item label="分类名称" prop="categoryName">
            <el-input v-model="infoModel.categoryName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="商品排序" prop="spuSort">
            <el-input-number v-model="infoModel.categorySort" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="infoModel.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="success" @click="showSpec = true" :disabled="showSpec" size="mini">显示规格</el-button>
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('infoModel')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </article>
      </div>
      <br>
      <div class="ms-doc" v-if="showSpec">
        <h3>规格信息</h3>
        <article>
          <el-button type="warning" @click="addSpec" size="mini">添加</el-button>
          <el-button type="primary" @click="editSpec" size="mini">修改</el-button>
          <el-button type="primary" @click="saveSpec" size="mini">保存</el-button>
          <goods-spec-key-list :category-id="infoModel.id"
                               :show-option="true"
                               :readonly="readonly"
                               ref="GoodsSpecKeyListRef"/>
        </article>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {Update, Add} from "../../../api/product/GoodsCategoryApi";

  import GoodsSpecKeyList from '../GoodsSpecKeyList';

  export default {
    components: {
      GoodsSpecKeyList,
    },
    data() {
      return {
        showSpec: false,// 是否显示规格信息
        optionType: '', // 操作类型，edit,add
        readonly: true,//spec-key-list 是否只读
        infoModel: {
          id: null,
          categoryName: null,
          categorySort: 0,
          validInd: true,
        }
      }
    },
    created() {
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit') {
        this.infoModel = JSON.parse(this.$route.query.editData);
      }
    },
    methods: {
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit') {
          Update(_this.infoModel).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            this.$bus.emit('notifyInfo', res.resData);
            _this.$router.back(-1);
          });
        } else { // add
          Add(_this.infoModel).then(res => {
            _this.$message({
              type: 'success',
              message: '保存成功!'
            });
            this.$bus.emit('notifyInfo', res.resData);
            _this.$router.back(-1);
          });
        }
      },
      addSpec() {
        this.readonly = false;
        this.$refs.GoodsSpecKeyListRef.handleAdd();
      },
      editSpec() {
        this.readonly = false;
      },
      saveSpec() {
        this.$refs.GoodsSpecKeyListRef.saveList();
        this.readonly = true;
      },
      onReturn() {
        this.$router.back(-1);
      },
      onReset(infoModel) {
        this.$refs[infoModel].resetFields();
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
