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
    ViewExecutionImgById,
  } from "../../api/proc/applyMag";
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
      var id = this.$route.query.proceImgId;
      if (id && id != '' && id != null) {
        if (this.$route.query.proceType === 'execution') {
          this.imgSrc = ViewExecutionImgById(id);
        } else {
          console.info(decodeURI(id));
          this.imgSrc = ViewDeployImgById(decodeURI(id));
        }
      }
    }
  }
</script>

<style scoped>

</style>
