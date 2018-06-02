<template>

  <div>
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
                <el-button type="primary" size="mini" @click="addMenuEvent">添加</el-button>
                <el-button type="warning" size="mini" @click="editMenuEvent">修改</el-button>
                <el-button type="danger" size="mini" @click="delMenuEvent">删除</el-button>
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
              <el-form :inline="true" ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="菜单标题">
                  <el-input v-model="parentMenu.permissionName" readonly></el-input>
                </el-form-item>
                <el-form-item label="菜单CODE">
                  <el-input v-model="parentMenu.permissionCode" readonly></el-input>
                </el-form-item>
                <el-form-item label="菜单图标">
                  <el-input v-model="parentMenu.icon" readonly></el-input>
                </el-form-item>
                <el-form-item label="有效标志">
                  <el-input v-model="parentMenu.validInd" readonly></el-input>
                </el-form-item>
              </el-form>
              <el-form ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="菜单路径">
                  <el-input v-model="parentMenu.url" readonly style="width: 450px"></el-input>
                </el-form-item>
              </el-form>
              <el-form :inline="true" ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="创建人">
                  <el-input v-model="parentMenu.creatorCode" readonly></el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                  <el-date-picker
                    v-model="parentMenu.createTime" type="date" format="yyyy-MM-dd HH:mm:ss"
                    value-format="timestamp" readonly style="width: 180px">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人">
                  <el-input v-model="parentMenu.updaterCode" readonly></el-input>
                </el-form-item>
                <el-form-item label="更新时间">
                  <el-date-picker
                    v-model="parentMenu.updateTime" type="date" format="yyyy-MM-dd HH:mm:ss"
                    value-format="timestamp" readonly style="width: 180px">
                  </el-date-picker>
                </el-form-item>
              </el-form>
              <el-form ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="描述">
                  <el-input type="textarea" v-model="parentMenu.descritpion" style="width: 450px" readonly></el-input>
                </el-form-item>
              </el-form>
            </el-collapse-item>
          </div>
        </el-collapse>
      </el-col>
    </el-row>

    <!-- 菜单修改/添加dialog start -->
    <el-dialog title="修改/添加" :visible.sync="dialogFormVisible" :close-on-click-modal="false" size="small">
      <el-form :model="editMenu" :rules="rules22" label-width="100px" ref="editMenu" :inline-message="true">
        <el-form-item label="父菜单标题">
          <el-col :span="18">
            <el-input v-model="editMenu.parentTtile" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单标题" prop="title">
          <el-col :span="18">
            <el-input v-model="editMenu.title"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单CODE" prop="titleCode">
          <el-col :span="18">
            <el-input v-model="editMenu.titleCode"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单路径" prop="path">
          <el-col :span="18">
            <el-input v-model="editMenu.path"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-col :span="18">
            <el-input v-model="editMenu.icon"></el-input>
          </el-col>
        </el-form-item>
        <!--<el-form-item label="活动区域" :label-width="formLabelWidth">-->
        <!--<el-select v-model="form.region" placeholder="请选择活动区域">-->
        <!--<el-option label="区域一" value="shanghai"></el-option>-->
        <!--<el-option label="区域二" value="beijing"></el-option>-->
        <!--</el-select>-->
        <!--</el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="cancledialog('editMenu')">取 消</el-button>
        <el-button size="mini" type="primary" @click="submitMenu('editMenu')">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 菜单修改/添加dialog end -->
  </div>
</template>

<script>
  import {findParentMenu, findMenus, saveOrUpdateMenu, delMenuByIds} from "@/api/sys/menuMag";
  import {Message} from "element-ui";

  export default {
    data() {
      return {
        filterText: "", //过滤条件
        activeNames: ["2", "3"], //折叠面板
        parentMenusShow: false, //是否显示父级菜单
        dialogFormVisible: false, //菜单修改/添加dialog
        menu: null,//本级菜单信息
        parentMenu: null,//父级菜单信息
        editMenu: null, //添加修改菜单信息
        defaultProps: {
          //设置数据绑定
          children: "children",
          label: "title"
        },
        menus: [],// 菜单树数据源
        node: [], //正在操作的节点
        optionType: '',// 操作类型
        rules22: {// 校验
          title: [{required: true, message: '请输入菜单名称', trigger: 'blur'}],
          titleCode: [{required: true, message: '请输入菜单CODE', trigger: 'blur'}],
          path: [{required: true, message: '请输入菜单路径', trigger: 'blur'}]
        }
      };
    },
    created() {
      this.findMenus();// 初始化菜单树
      this.initMenu();//本级菜单信息
      this.initEditMenu();//父级菜单信息
      this.initParentMenu();//添加修改菜单信息
    },
    methods: {
      //本级菜单信息
      initMenu() {
        this.menu = {
          id: "",
          pid: "",
          title: "",
          titleCode: "",
          path: "",
          icon: ""
//          validInd: null
        }
      },
      //父级菜单信息
      initParentMenu() {
        this.parentMenu = {
          id: "",
          pid: "",
          url: "",
          icon: "",
          permissionName: "",
          permissionCode: "",
          createTime: "",
          creatorCode: "",
          updateTime: "",
          updaterCode: "",
          descritpion: "",
//          validInd: null
        }
      },
      //添加修改菜单信息
      initEditMenu() {
        this.editMenu = {
          id: "",
          pid: "",
          title: "",
          titleCode: "",
          parentTtile: "",
          path: "",
          icon: ""
        }
      },
      //获取所有菜单
      findMenus() {
        findMenus().then(res => {
          this.menus = res.resData;
        })
      },
      // 当前菜单信息
      changeCheck(data) {
//        console.info("data.pid",data.pid);
        this.menu = this.VueUtils.deepClone(data);
//        console.info("menu.pid",this.menu.pid);
        this.parentMenusShow = false;
      },
      // 过滤搜索
      filterNode(value, data) {
        if (!value) return true;
        return data.title.indexOf(value) !== -1;
      },
      //查看父菜单信息
      findParentMenu() {
        this.initParentMenu();
        var nodes = this.$refs.tree2.getCheckedNodes();
        if (nodes.length != 1) {
          Message.error("请选择一个菜单");
          return;
        }
        var pid = nodes[0].pid;
        if (pid) {
          findParentMenu(pid).then(res => {
            this.parentMenu = res.resData;
            this.parentMenusShow = true;
          }).catch(error => {
            Message.error(error);
          });
        } else {
          Message.error("菜单为顶级菜单");
        }
      },
      //递归查询出所有的节点id
      getIds: function (nodes, ids) {
        nodes.forEach(node => {
          if (node.children && node.children != null) {
            this.getIds(node.children, ids);
          }
          ids.push(node.id);
        });
      },
      // 添加菜单
      addMenuEvent() {
        this.optionType = "add";
        var nodes = this.$refs.tree2.getCheckedNodes();
        if (nodes.length > 1) {
          Message.error("请选择一个菜单");
          return;
        } else if (nodes.length == 1) {
          this.editMenu.pid = nodes[0].id;
          this.editMenu.parentTtile = nodes[0].title;
          // 当前操作的节点(用于后面提交)
          this.node = nodes[0];
        } else { //添加根节点
          this.editMenu.parentTtile = '添加根节点';
          this.node = null;
        }
        this.dialogFormVisible = true;

      },
      // 修改菜单
      editMenuEvent() {
        this.optionType = "edit";
        var nodes = this.$refs.tree2.getCheckedNodes();
        console.info(nodes)
        if (nodes.length != 1) {
          Message.error("请选择一个菜单");
          return;
        }
        this.editMenu = this.VueUtils.deepClone(nodes[0]);
//        console.info("nodes[0].pid",nodes[0].pid);
        var parentNode = this.$refs.tree2.getNode(nodes[0].pid);
//        console.info("parentNode",parentNode.data.title);
        this.editMenu.parentTtile = parentNode.data.title;
        this.dialogFormVisible = true;
        // 当前操作的节点(用于后面提交)
        this.node = nodes[0];
      },
      // 删除菜单
      delMenuEvent() {
        this.$confirm('此操作将删除该菜单及子菜单, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = [];
          var nodes = this.$refs.tree2.getCheckedNodes();
          if (nodes) {
            //递归查询出所有的节点id
            this.getIds(nodes, ids);
//            console.info("ids[]", ids);
            delMenuByIds(ids).then(res => {
              nodes.forEach(node => {
                this.$refs.tree2.remove(node);
              })
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            });
          }
        }).catch((err) => {
          console.info(err)
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      // 取消
      cancledialog(formRule) {
        this.$refs[formRule].resetFields();
        this.dialogFormVisible = false
        this.initEditMenu();
      },
      // 提交
      submitMenu(formRule) {
        var editMenus = this.editMenu;
//        console.info("editMenus", editMenus)
        saveOrUpdateMenu(editMenus).then(res => {
          var resData = res.resData;

          // 前面点击后缓存后的节点
          var data = this.node;
          var copuEidtMenus = this.VueUtils.deepClone(editMenus);
          if (this.optionType == "edit") { // 修改
            data.title = copuEidtMenus.title;
            data.titleCode = copuEidtMenus.titleCode;
            data.path = copuEidtMenus.path;
            data.icon = copuEidtMenus.icon;
          } else { // 添加
            //后台返回的
            copuEidtMenus.id = resData.id;
            if (data) {
              if (!data.children) {
                this.$set(data, 'children', []);
              }
              data.children.push(copuEidtMenus);
            } else {
              this.menus.push(copuEidtMenus)
            }
          }
          this.$refs[formRule].resetFields();
          this.dialogFormVisible = false
          this.initEditMenu();
          this.$message({
            type: 'success',
            message: '提交成功!'
          });
        });

      }
    },
    watch: {
      //通过 :filter-node-method,找到过滤方法
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
