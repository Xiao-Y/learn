<template>
  <div>
    <div class="ms-doc">
      <h3>商品图片-{{productInfo.commodityName}}</h3>
      <article>
        <el-form label-width="100px" size="mini">
          <el-upload
            action="admin-system/fileApi/uploadProductImage/12313"
            list-type="picture-card"
            :show-file-list="true"
            :file-list="productImageList"
            :multiple="true"
            :limit="30"
            :on-exceed="handleExceed"
            :on-preview="handlePictureCardPreview"
            :before-remove="beforeRemove"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
          <el-form-item size="mini">
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>

  </div>
</template>

<script>
  import {UpdateProduct} from "../../../api/product/productMag";

  export default {
    data() {
      return {
        dialogVisible: false,
        dialogImageUrl: '',
        productImageList: [], // 图片列表
        productInfo: {} // 商品信息
      };
    },
    created() {
      this.productInfo = this.$route.params.productInfo;
      console.info(this.productInfo);
      this.initProudctImage();
    },
    methods: {
      // 上传前校验
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      // 删除前提示
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      // 删除
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      // 上传后显示图片
      handlePictureCardPreview(file) {
        console.info("file.url", file.url);
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      initProudctImage() {
//        GetProuctImageById(productInfo.id).then(res => {
//
//        });
        this.productImageList = [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }];
      },
      onReturn() {
        this.$router.back(-1);
      }
    }
  };
</script>

<style scoped>
  .ms-doc {
    width: 80%;
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
