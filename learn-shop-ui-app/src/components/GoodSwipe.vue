<template>
  <div>
    <!-- 轮播图片 -->
    <van-swipe class="goods-swipe" :autoplay="3000" v-if="selectGridItemIndex === null">
      <van-swipe-item v-for="(pic,index) in spuPics" :key="index" @click="onSwipeItem(index)">
        <img v-lazy="pic" alt="轮播图" :src="pic" class="goods-swipe-item-pic"/>
      </van-swipe-item>
    </van-swipe>
    <!-- 点击下方小图片后显示的 -->
    <van-image
        :src="selectGridItemPic"
        v-if="selectGridItemIndex !== null"
        class="big-pic"
        @click="onGridPic"
    />
    <!-- 下方小图片,最多显示8个 -->
    <van-grid square clickable :column-num="8">
      <van-grid-item v-for="(pic,index) in skuPics" :key="index" @click="selectGridItem(index)">
        <van-image :src="pic" height="100%" width="100%" :class="{active:selectGridItemIndex===index}"/>
      </van-grid-item>
    </van-grid>
    <!-- 显示大图 -->
    <van-image-preview
        v-model="showBigPic"
        :images="viewBigPics"
        :startPosition="startPosition"
        @change="onChange"
        closeable>
      <template v-slot:index>第{{ bigPicIndex }}页</template>
    </van-image-preview>
  </div>
</template>
<script>

export default {
  props: {
    // spu画册图片，连产品图片限制为8张，以逗号分割
    spuPics: {
      type: Array,
      default: () => []
    },
    // spu画册图片，连产品图片限制为8张，以逗号分割
    skuPics: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      selectGridItemIndex: null, // 选中小图标的下标
      selectGridItemPic: '',// 轮播位显示选中的图片
      showBigPic: false, // 显示大图
      bigPicIndex: 1, // 大图显示的坐标
      startPosition: 0, //图片预览起始位置索引
      viewBigPics: [], // 显示大图的图片 URL 数组
    }
  },
  methods: {
    onSwipeItem(index) {
      this.viewBigPics = this.spuPics;
      this.startPosition = index;
      this.bigPicIndex = this.startPosition + 1;
      this.showBigPic = true;
    },
    onGridPic() {
      this.viewBigPics = this.skuPics;
      this.startPosition = this.selectGridItemIndex;
      this.bigPicIndex = this.startPosition + 1;
      this.showBigPic = true;
    },
    onChange(index) {
      this.bigPicIndex = index + 1;
    },
    selectGridItem(index) {
      if (this.selectGridItemIndex === index) {
        this.selectGridItemIndex = null;
      } else {
        this.selectGridItemIndex = index;
      }
      this.selectGridItemPic = this.skuPics[index];
    }
  }
}


</script>

<style lang="less">
/*设置小图片大小*/
.van-grid-item__content {
  padding: 3px 2px;
}

/*小图片选中的边框*/
.active {
  border: 2px solid #fc603a;
}

/*轮播图片*/
.van-swipe__track {
  padding-bottom: 4px;
}

/*轮播图片*/
.goods-swipe-item-pic {
  width: 100%;
  display: block;
  height: 270px;
}

/*点击小图片显示的大图*/
.big-pic {
  height: 270px;
}

</style>