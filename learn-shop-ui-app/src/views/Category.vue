<template>
  <div class="category">
    <!-- 标题 -->
    <navbar/>
    <!--  分类树  -->
    <van-tree-select
        :items="items"
        :main-active-index="mainActiveIndex"
        :active-id="activeId"
        @click-nav="onClickNav"
        @click-item="onClickItem"
    />
    <tobbar/>
  </div>
</template>

<script>

import Tobbar from "../components/Tobbar";
import Navbar from "../components/Navbar";

import {FindCategoryTree} from '@/api/GoodsCategoryApi';

export default {
  components: {
    Tobbar, Navbar
  },
  data() {
    return {
      mainActiveIndex: 0,// 左侧选中项的索引
      activeId: null, // 右侧选中项的 id，支持传入数组
      items: [] // 分类数据
    };
  },
  created() {
    // 加载默认分类树
    this.getCategoryTree(this.items, this.mainActiveIndex).then(res => {
      this.getCategoryTree(res[this.mainActiveIndex].children, res[this.mainActiveIndex].id);
    });

  },
  methods: {
    onClickLeft() {
      const val = this.$route.query.val;
      if (val === "home") {
        this.$router.push("/");
      } else {
        this.$router.back();
      }
    },
    // 点击左侧分类
    onClickNav(index) {
      this.mainActiveIndex = index;
      // 动态加载 children 数据
      this.items[index].children = [];
      this.getCategoryTree(this.items[index].children, this.items[index].id);
    },
    // 点击右侧分类
    onClickItem({id = 0}) {
      //点击跳转到列表页，并把输入的值传过去
      this.$router.push({
        path: '/goodsList',
        query: {
          categoryId: id
        }
      })
    },
    // 获取分类树
    getCategoryTree(obj, activityIndex) {
      return new Promise((resolve) => {
        FindCategoryTree(activityIndex).then(res => {
          res.resData.forEach(m => {
            obj.push({id: m.id, text: m.categoryName, disabled: m.validInd, children: []});
          });
          resolve(obj);
        })
      });
    }
  }
};
</script>
