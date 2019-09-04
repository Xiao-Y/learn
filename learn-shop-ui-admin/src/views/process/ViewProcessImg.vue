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
  import {
    ViewExecutionImgById,
  } from "../../api/proc/proceTaskMag";

  export default {
    name: "procViewProcessImg",
    data() {
      return {
        imgSrc: null,
      }
    },
    created() {
      var id = this.$route.query.id;
      if (id && id != '' && id != null) {
        if (this.$route.query.type === 'execution') {
          this.imgSrc = ViewExecutionImgById(id);
        } else {
          this.imgSrc = ViewDeployImgById(id);
        }
      }
    }
  }
</script>

<style scoped>

</style>
