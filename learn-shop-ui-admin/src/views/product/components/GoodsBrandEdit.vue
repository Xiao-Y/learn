<template>
  <div>
    <el-form ref="infoModel" :model="infoModel" label-width="100px" size="mini">
      <div class="ms-doc">
        <div class="ms-doc-title">品牌信息</div >
        <article>
          <el-form-item label="品牌名称" prop="brandName">
            <el-input v-model="infoModel.brandName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="商品排序" prop="spuSort">
            <el-input-number v-model="infoModel.brandSort" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="infoModel.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('infoModel')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </article>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {Update, Add} from "../../../api/product/GoodsBrandApi";


  export default {
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        readonly: true,//spec-key-list 是否只读
        infoModel: {
          id: null,
          brandName: null,
          brandSort: 0,
          validInd: true,
        }
      }
    },
    activated() {
      this.optionType = this.$route.query.optionType;
      if (this.optionType === 'edit') {
        this.infoModel = JSON.parse(this.$route.query.editData);
        console.info(this.infoModel)
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
