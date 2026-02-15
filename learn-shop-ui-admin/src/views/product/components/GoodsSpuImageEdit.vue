<template>
  <div>
    <div class="ms-doc">
      <div class="ms-doc-title">商品图片-{{productInfo.commodityName}}</div >
      <article>
        <el-form label-width="100px" size="mini">
          <el-upload
            :action="uploadProductImageUrl"
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
  import {GetProuctImageByProductId, DeleteProductImageById} from "../../../api/product/GoodsSpuApi";

  export default {
    data() {
      return {
        dialogVisible: false,
        dialogImageUrl: '',
        uploadProductImageUrl: '', // 商品图上上传请求
        productImageList: [], // 图片列表
        productInfo: {} // 商品信息
      };
    },
    activated() {
      this.productInfo = this.$route.params.productInfo;
      this.uploadProductImageUrl = 'core-product/productApi/uploadProductImage/' + this.productInfo.id;
      this.productImageList = []; // 每次激活时清空图片列表，避免重复加载
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
      // 删除商品图片
      handleRemove(file, fileList) {
        DeleteProductImageById(file.id).then(res => {
          var flag = res.resData;
          if (flag) {
            this.$message({
              type: 'success',
              message: file.name + ' 删除成功!'
            });
          } else {
            fileList.push(file);
            this.$message({
              type: 'error',
              message: file.name + ' 删除失败!'
            });
          }
        });

      },
      // 上传后显示图片
      handlePictureCardPreview(file) {
        console.info("file.url", file.url);
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      // 显示商品图片
      initProudctImage() {
        var _this = this;
        var param = {
          productId: this.productInfo.id
        };
        GetProuctImageByProductId(param).then(res => {
          var data = res.resData;
          if (data) {
            for (var key in data) {
              var temp = {
                id: data[key].id,
                name: data[key].oldImageName,
                url: data[key].imagePath
              };
              _this.productImageList.push(temp);
            }
          }
        });
      },
      onReturn() {
        this.$router.back(-1);
      }
    }
  };
</script>
