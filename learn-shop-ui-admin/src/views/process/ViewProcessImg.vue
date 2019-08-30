<template>
  <div class="block">
    <el-image :src="imgSrc">
      <div slot="placeholder" class="image-slot">
        加载中<span class="dot">...</span>
      </div>
    </el-image>
  </div>
</template>

<script>
  // ===== api start
  import {
    ViewDeployImgById,
  } from "../../api/proc/procDeployMag";

  export default {
    name: "procViewProcessImg",
    data() {
      return {
        imgSrc: null,
    }
    },
    created() {
      var id = this.$route.query.id;
      if(id && id != '' && id != null){
        this.imgSrc = ViewDeployImgById(id);
      }
    }
  }
</script>

<style scoped>

</style>
