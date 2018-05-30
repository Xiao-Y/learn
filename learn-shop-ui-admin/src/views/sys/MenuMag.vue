<template>

  <el-row :gutter="10">
    <el-col :span="10">
      <el-collapse value="1">
        <el-collapse-item title="菜单树" name="1">
          <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
          <div class="sidebar">
            <el-tree show-checkbox default-expand-all node-key="id" :data="menus"
                     ref="tree2" :highlight-current="true" :props="defaultProps" :check-strictly="true"
                     @node-click="changeCheck" @check="changeCheck" :filter-node-method="filterNode">
            </el-tree>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-col>

    <el-col :span="14">
      <el-collapse v-model="activeNames">
        <el-collapse-item title="菜单信息" name="2">
          <el-row>
            <div class="buttons">
              <el-button type="success" size="mini" @click="findParentMenu">查看父菜单</el-button>
              <el-button type="primary" size="mini" @click="addMenu">添加</el-button>
              <el-button type="warning" size="mini" @click="editMenu">修改</el-button>
              <el-button type="danger" size="mini" @click="delMenu">删除</el-button>
              <!--<el-button @click="getCheckedNodes">通过 node 获取</el-button>-->
            </div>
          </el-row>

          <el-form :inline="true" ref="menu" label-width="80px" :model="menu" size="mini">
            <el-form-item label="菜单标题">
              <el-input v-model="menu.title" readonly></el-input>
            </el-form-item>
            <el-form-item label="菜单CODE">
              <el-input v-model="menu.titleCode" readonly></el-input>
            </el-form-item>
            <el-form-item label="菜单路径">
              <el-input v-model="menu.path" readonly></el-input>
            </el-form-item>
            <el-form-item label="菜单图标">
              <el-input v-model="menu.icon" readonly></el-input>
            </el-form-item>
            <el-form-item label="有效标志">
              <el-input v-model="menu.validInd" readonly></el-input>
            </el-form-item>
          </el-form>
        </el-collapse-item>

        <div v-show="parentMenusShow">
          <el-collapse-item title="父菜单信息" name="3">
            <el-form :inline="true" ref="menu" label-width="80px" :model="menu" size="mini">
              <el-form-item label="菜单标题">
                <el-input v-model="menu.title" readonly></el-input>
              </el-form-item>
              <el-form-item label="菜单CODE">
                <el-input v-model="menu.titleCode" readonly></el-input>
              </el-form-item>
              <el-form-item label="菜单图标">
                <el-input v-model="menu.icon" readonly></el-input>
              </el-form-item>
              <el-form-item label="有效标志">
                <el-input v-model="menu.validInd" readonly></el-input>
              </el-form-item>
            </el-form>
            <el-form ref="menu" label-width="80px" :model="menu" size="mini">
              <el-form-item label="菜单路径">
                <el-input v-model="menu.path" readonly style="width: 450px"></el-input>
              </el-form-item>
            </el-form>
            <el-form :inline="true" ref="menu" label-width="80px" :model="menu" size="mini">
              <el-form-item label="创建人">
                <el-input v-model="menu.validInd" readonly></el-input>
              </el-form-item>
              <el-form-item label="创建时间">
                <el-input v-model="menu.validInd" readonly></el-input>
              </el-form-item>
              <el-form-item label="更新人">
                <el-input v-model="menu.validInd" readonly></el-input>
              </el-form-item>
              <el-form-item label="更新时间">
                <el-input v-model="menu.validInd" readonly></el-input>
              </el-form-item>
            </el-form>
            <el-form ref="menu" label-width="80px" :model="menu" size="mini">
              <el-form-item label="描述">
                <el-input type="textarea" v-model="menu.validInd" style="width: 450px"></el-input>
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </div>
      </el-collapse>
    </el-col>
  </el-row>
</template>

<script>
import { findParentMenu, findMenus } from "@/api/sys/menuMag";
import { Message } from "element-ui";
import axios from "axios";

export default {
  data() {
    return {
      filterText: "", //过滤条件
      activeNames: ["2", "3"], //折叠面板
      parentMenusShow: false, //是否显示父级菜单
      menu: {
        //本级菜单信息
        id: "",
        pid: "",
        title: "",
        titleCode: "",
        path: "",
        icon: "",
        validInd: ""
      },
      parentMenu: {
        //父级菜单信息
        id: "",
        pid: "",
        title: "",
        titleCode: "",
        path: "",
        icon: "",
        validInd: ""
      },
      defaultProps: {
        //设置数据绑定
        children: "children",
        label: "title"
      },
      menus: []
    };
  },
  created() {
    // 初始化
    this.findMenus();
  },
  methods: {
    findMenus() {
      //获取所有菜单
      // findMenus().then(res => {
      //   var menus = res.resData.menus;
      //   this.menus = menus;
      // })
      var url = "api/menuApi/findMenus";
      axios.get(url).then(res => {
        console.info(res.resData);
        // var menus = res.resData;
        // this.menus = menus;
      });
    },
    changeCheck(data) {
      // 当前菜单信息
      this.menu = data;
      this.parentMenusShow = false;
    },
    filterNode(value, data) {
      // 过滤搜索
      if (!value) return true;
      return data.title.indexOf(value) !== -1;
    },
    findParentMenu() {
      //查看父菜单信息
      var pid = this.menu.pid;
      if (pid) {
        pid = 22;
        findParentMenu(pid)
          .then(res => {
            res.resData;
            this.parentMenusShow = true;
          })
          .catch(error => {
            Message.error(error);
          });
      } else {
        Message.error("请选择一个菜单");
      }
    },
    addMenu() {},
    editMenu() {},
    delMenu() {}
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val);
    }
  }
};
</script>

<style scoped>
.el-row {
  margin-bottom: 10px;
}

.el-form-item {
  margin-bottom: 3px;
}

.buttons {
  margin-top: 5px;
}

.sidebar {
  display: block;
  position: absolute;
  width: 40%;
  height: 380px;
  left: 0;
  top: 100px;
  bottom: 0;
  overflow-x: hidden;
  overflow-y: scroll;
  /*background: #2E363F;*/
}

.sidebar > ul {
  height: 100%;
}

/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
  width: 3px; /*滚动条宽度*/
  height: 3px; /*滚动条高度*/
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  border-radius: 10px; /*滚动条的背景区域的圆角*/
  background-color: white; /*滚动条的背景颜色*/
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 10px; /*滚动条的圆角*/
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: #2e363f; /*滚动条的背景颜色*/
}
</style>
