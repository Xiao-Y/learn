<template>
  <div>
    <el-input v-model="filterMenu" placeholder="输入关键字进行过滤" size="mini"></el-input>
    <div class="sidebar">
      <el-tree
        ref="menuTree"
        :check-strictly="checkStrictly"
        :data="menus"
        :default-expand-all="expandAll"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        :highlight-current="true"
        :default-expanded-keys="defaultCheckedMenu"
        :props="defaultProps"
        :show-checkbox="showCheckbox"
        node-key="id"
        @check="nodeCheck"
        @node-click="nodeCheck"
        @check-change="changeCheck">
            <span slot-scope="{ node, data }">
              <i :class="['icon-' + data.icon]" class="iconfont"/><span>{{ node.label }}</span>
            </span>
      </el-tree>
    </div>
  </div>
</template>

<script>
import {findMenus} from "../../api/sys/menuMag";

export default {
  props: {
    // 默认被行选种的
    defaultCheckedMenu: {
      type: Array,
      default: null
    },
    // 是否显示勾选框
    showCheckbox: {
      type: Boolean,
      default: true
    },
    // 是否展开所有
    expandAll: {
      type: Boolean,
      default: true
    },
    // 不级联选种
    checkStrictly: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 设置数据绑定
      defaultProps: {
        children: "children",
        label: "menuName"
      },
      activeNames: ["0"],
      // 点击后被选种的菜单ID
      menuChecked: [],
      filterMenu: "", // 菜单树过滤
      // 完整菜单
      menus: []
    }
  },
  mounted() {
    // 获取所有菜单
    findMenus().then(res => {
      this.menus = res.resData;
      // 初始化被选种的
      if (this.defaultCheckedMenu) {
        this.$refs.menuTree.setCheckedKeys(this.defaultCheckedMenu);
      }
    });
  },
  methods: {
    // 当前菜单信息
    nodeCheck(data) {
      this.$emit('nodeCheck', data);
    },
    // 过滤搜索
    filterNode(value, data) {
      if (!value) return true;
      return data.menuName.indexOf(value) !== -1;
    },
    // 收集选种和半选种的所有菜单id
    changeCheck() {
      // 选种的菜单
      this.menuChecked = this.getCheckAll();
      this.$emit('changeCheck', this.menuChecked);
    },
    // 获取选种和半选种的
    getCheckAll() {
      // 选种的菜单
      let menuChecked = this.$refs.menuTree.getCheckedNodes().map(m => m.id);
      // 半先种的父级菜单
      menuChecked = menuChecked.concat(
        this.$refs.menuTree.getHalfCheckedNodes().map(m => m.id)
      );
      return menuChecked;
    },
    // 获取选种和半选种的
    getCheckDataAll() {
      // 选种的菜单
      let menuChecked = this.$refs.menuTree.getCheckedNodes();
      // 半先种的父级菜单
      menuChecked = menuChecked.concat(
        this.$refs.menuTree.getHalfCheckedNodes()
      );
      return menuChecked;
    }
  },
  watch: {
    //通过 :filter-node-method,找到过滤方法
    filterMenu(val) {
      this.$refs.menuTree.filter(val);
    }
  }
}
</script>


<style scoped>
.sidebar {
  /*  position: relative;*/
  /*  left: 0;*/
  /*  bottom: 0;*/
  overflow-x: hidden;
  overflow-y: scroll;
  height: 550px;
  /*  !*background: #2E363F;*!*/
}

.sidebar > ul {
  height: 100%;
}

/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
  width: 3px;
  /*滚动条宽度*/
  height: 3px;
  /*滚动条高度*/
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
  /*滚动条的背景区域的圆角*/
  background-color: white;
  /*滚动条的背景颜色*/
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  /*滚动条的圆角*/
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: #2e363f;
  /*滚动条的背景颜色*/
}
</style>
