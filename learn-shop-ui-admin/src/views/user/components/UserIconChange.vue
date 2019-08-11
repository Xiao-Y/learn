<template>
  <div id="app">
    <el-upload
      class="avatar-uploader"
      :action="action"
      :multiple="false"
      :show-file-list="false"
      :on-success="handleSuccess"
      :on-error="handleError"
      :before-upload="beforeAvatarUpload">

      <img v-if="newImageUrl" :src="newImageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script>
  export default {
    props: {
      // 原图片url
      imageUrl: {
        type: String,
        default: ''
      },
      // 新文件名
      newFileName: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        newImageUrl: '',
        action: ''
      }
    },
    created() {
      this.newImageUrl = this.imageUrl;
      this.action = "admin-system/fileApi/singleUpload/userIcon";
      if (this.newFileName != '') {
        this.action = this.action + "/" + this.newFileName;
      }

    },
    methods: {
      // 图片上传成功后
      handleSuccess(res, file) {
        this.newImageUrl = URL.createObjectURL(file.raw);
        this.$emit("uploadSuccess", res.resData);
      }
      ,
      // 图片上传失败后
      handleError(err, file) {
        console.error(err);
        this.$emit("uploadError", err);
      }
      ,
      // 图片上传前校验
      beforeAvatarUpload(file) {
        // const isJPG = true;//file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        if (!isPNG) {
          this.$message.error('上传头像图片只能是 PNG 格式!');
        }

        const isLt2M = file.size / 1024 / 1024 < 1;
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 1MB!');
        }

        return (isPNG) && isLt2M;
      }
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #4ad939;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
