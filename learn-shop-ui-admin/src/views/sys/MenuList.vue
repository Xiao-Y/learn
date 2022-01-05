<template>

  <div>
    <el-row :gutter="10">
<!--      <el-col :span="10">-->
<!--        <el-collapse value="1">-->
<!--          <el-collapse-item title="菜单树" name="1">-->
<!--            <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>-->
<!--            <div class="sidebar">-->
<!--              <el-tree show-checkbox-->
<!--                       default-expand-all-->
<!--                       node-key="id"-->
<!--                       ref="tree2"-->
<!--                       :data="menus"-->
<!--                       :expand-on-click-node="false"-->
<!--                       :highlight-current="true"-->
<!--                       :props="defaultProps"-->
<!--                       :check-strictly="true"-->
<!--                       @node-click="nodeCheckMenu"-->
<!--                       @check="nodeCheckMenu"-->
<!--                       :filter-node-method="filterNode">-->
<!--                <span slot-scope="{ node, data }">-->
<!--                  <i class="iconfont" :class="['icon-' + data.icon]"/><span>{{ node.label }}</span>-->
<!--                </span>-->
<!--              </el-tree>-->
<!--            </div>-->
<!--          </el-collapse-item>-->
<!--        </el-collapse>-->
<!--      </el-col>-->
      <custom-menu-tree ref="tree2" :checkStrictly="true" @nodeCheck="nodeCheckMenu"/>

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
                <el-input v-model="menu.menuCode" readonly></el-input>
              </el-form-item>
              <el-form-item label="菜单位置">
                <el-input v-model="menu.sortField" readonly></el-input>
              </el-form-item>
              <el-form-item label="菜单图标">
                <custom-icon v-model="menu.icon"></custom-icon>
              </el-form-item>
              <el-form-item label="有效标志">
                <el-switch v-model="menu.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
              </el-form-item>
              <el-form-item label="是否显示">
                <el-switch v-model="menu.display" active-text="显示" inactive-text="隐藏" disabled></el-switch>
              </el-form-item>
            </el-form>
          </el-collapse-item>

          <div v-show="parentMenusShow">
            <el-collapse-item title="父菜单信息" name="3">
              <el-form :inline="true" ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="菜单标题">
                  <el-input v-model="parentMenu.menuName" readonly></el-input>
                </el-form-item>
                <el-form-item label="菜单CODE">
                  <el-input v-model="parentMenu.menuCode" readonly></el-input>
                </el-form-item>
                <el-form-item label="有效标志">
                  <el-switch v-model="menu.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
                </el-form-item>
                <el-form-item label="是否显示">
                  <el-switch v-model="menu.display" active-text="显示" inactive-text="隐藏" disabled></el-switch>
                </el-form-item>
              </el-form>
              <el-form ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="菜单图标">
                  <custom-icon v-model="parentMenu.icon" disabled></custom-icon>
                </el-form-item>
              </el-form>
              <el-form ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="菜单位置">
                  <el-input v-model="parentMenu.sortField" readonly></el-input>
                </el-form-item>
              </el-form>
              <el-form :inline="true" ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="创建人">
                  <el-input v-model="parentMenu.creatorCode" readonly></el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                  <el-date-picker v-model="parentMenu.createTime" type="date" format="yyyy-MM-dd HH:mm:ss"
                                  value-format="timestamp"
                                  readonly style="width: 180px">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人">
                  <el-input v-model="parentMenu.updaterCode" readonly></el-input>
                </el-form-item>
                <el-form-item label="更新时间">
                  <el-date-picker v-model="parentMenu.updateTime" type="date" format="yyyy-MM-dd HH:mm:ss"
                                  value-format="timestamp"
                                  readonly style="width: 180px">
                  </el-date-picker>
                </el-form-item>
              </el-form>
              <el-form ref="parentMenu" label-width="80px" :model="parentMenu" size="mini">
                <el-form-item label="描述">
                  <el-input type="textarea" v-model="parentMenu.description" readonly></el-input>
                </el-form-item>
              </el-form>
            </el-collapse-item>
          </div>
        </el-collapse>
      </el-col>
    </el-row>

    <!-- 菜单修改/添加dialog start -->
    <el-dialog title="修改/添加" :visible.sync="dialogFormVisible" :close-on-click-modal="false"
               @close="cancelDialog('editMenu')" v-if="dialogFormVisible">
      <el-form :model="editMenu" :rules="rules22" label-width="130px" ref="editMenu" :inline-message="true">
        <el-form-item label="父菜单标题" prop="parentTitle">
          <el-col :span="18">
            <el-input v-model="editMenu.parentTitle" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单标题" prop="title">
          <el-col :span="18">
            <el-input v-model="editMenu.title"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单CODE" prop="menuCode">
          <el-col :span="18">
            <el-input v-model="editMenu.menuCode" placeholder="必须与路由中的name相同并唯一"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单位置" prop="sortField">
          <el-col :span="18">
            <el-input-number v-model="editMenu.sortField" :min="0"></el-input-number>
          </el-col>
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-col :span="18">
            <custom-icon v-model="editMenu.icon"></custom-icon>
          </el-col>
        </el-form-item>
        <el-form-item label="有效标志" prop="validInd">
          <el-col :span="18">
            <el-switch v-model="editMenu.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-col>
        </el-form-item>
        <el-form-item label="是否显示" prop="display">
          <el-col :span="18">
            <el-switch v-model="editMenu.display" active-text="显示" inactive-text="隐藏"></el-switch>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" type="primary" @click="validSubmit('editMenu')">确 定</el-button>
        <el-button size="mini" @click="cancelDialog('editMenu')">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 菜单修改/添加dialog end -->
  </div>
</template>

<script>
  import {
    findParentMenu,
    saveOrUpdateMenu,
    delMenuByIds,
    CheckMenuCode
  } from "../../api/sys/menuMag";

  import CustomIcon from '../../components/common/CustomIcon'
  import CustomMenuTree from '../../components/common/CustomMenuTree'

  export default {
    components: {
      CustomIcon,
      CustomMenuTree
    },
    data() {
      return {
        activeNames: ["2", "3"], //折叠面板
        parentMenusShow: false, //是否显示父级菜单
        dialogFormVisible: false, //菜单修改/添加dialog
        menu: null, //本级菜单信息
        parentMenu: null, //父级菜单信息
        editMenu: null, //添加修改菜单信息
        menus: [], // 菜单树数据源
        node: [], //正在操作的节点
        optionType: '', // 操作类型
        rules22: { // 校验
          title: [{
            required: true,
            message: '请输入菜单名称',
            trigger: 'blur'
          }],
          menuCode: [{
            validator: this.checkMenuCode,
            trigger: 'blur'
          }]
        }
      };
    },
    created() {
      this.initMenu(); //本级菜单信息
      this.initEditMenu(); //父级菜单信息
      this.initParentMenu(); //添加修改菜单信息
    },
    methods: {
      //本级菜单信息
      initMenu() {
        this.menu = {
          id: "",
          pid: "",
          title: "",
          menuCode: "",
          sortField: null,
          icon: "",
          validInd: true,
          display: true
        }
      },
      //父级菜单信息
      initParentMenu() {
        this.parentMenu = {
          id: "",
          pid: "",
          sortField: null,
          icon: "",
          menuName: "",
          menuCode: "",
          createTime: "",
          creatorCode: "",
          updateTime: "",
          updaterCode: "",
          description: "",
          validInd: true,
          display: true
        }
      },
      //添加修改菜单信息
      initEditMenu() {
        this.editMenu = {
          id: "",
          pid: "",
          title: "",
          menuCode: "",
          parentTitle: "",
          sortField: null,
          icon: "",
          validInd: true,
          display: true
        }
      },
      // 当前菜单信息
      nodeCheckMenu(data) {
        this.menu = this.VueUtils.deepClone(data);
        this.parentMenusShow = false;
      },
      //查看父菜单信息
      findParentMenu() {
        this.initParentMenu();
        let nodes = this.$refs.tree2.$refs.menuTree.getCheckedNodes();
        if (nodes.length != 1) {
          this.$message.error("请选择一个菜单");
          return;
        }
        let pid = nodes[0].pid;
        if (pid) {
          findParentMenu(pid).then(res => {
            this.parentMenu = res.resData;
            this.parentMenusShow = true;
          }).catch(error => {
            this.$message.error(error);
          });
        } else {
          this.$message.error("菜单为顶级菜单");
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
        let nodes = this.$refs.tree2.$refs.menuTree.getCheckedNodes();
        if (nodes.length > 1) {
          this.$message.error("请选择一个菜单");
          return;
        } else if (nodes.length == 1) {
          this.editMenu.pid = nodes[0].id;
          this.editMenu.parentTitle = nodes[0].title;
          // 当前操作的节点(用于后面提交)
          this.node = nodes[0];
        } else { //添加根节点
          this.editMenu.parentTitle = '根节点';
          this.node = null;
        }
        this.dialogFormVisible = true;

      },
      // 修改菜单
      editMenuEvent() {
        this.optionType = "edit";
        let nodes = this.$refs.tree2.$refs.menuTree.getCheckedNodes();
        if (nodes.length != 1) {
          this.$message.error("请选择一个菜单");
          return;
        }
        this.editMenu = this.VueUtils.deepClone(nodes[0]);
        //        console.info("nodes[0].pid",nodes[0].pid);
        if (nodes[0].pid != null) {
          let parentNode = this.$refs.tree2.$refs.menuTree.getNode(nodes[0].pid);
          //          console.info("parentNode",parentNode);
          this.editMenu.parentTitle = parentNode.data.title;
        } else {
          this.editMenu.parentTitle = '根节点';
        }
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
          let ids = [];
          let nodes = this.$refs.tree2.$refs.menuTree.getCheckedNodes();
          if (nodes) {
            //递归查询出所有的节点id
            this.getIds(nodes, ids);
            //            console.info("ids[]", ids);
            delMenuByIds(ids).then(res => {
              nodes.forEach(node => {
                this.$refs.tree2.$refs.menuTree.remove(node);
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
      cancelDialog(formRule) {
        this.$refs[formRule].resetFields();
        this.dialogFormVisible = false;
        this.initEditMenu();
      },
      // 验证后提交
      validSubmit(formRule) {
        let _this = this;
        this.$refs[formRule].validate((valid) => {
          if (valid) {
            _this.submitMenu(formRule);
          } else {
            return false;
          }
        });
      },
      // 提交
      submitMenu(formRule) {
        let editMenus = this.editMenu;
        //        console.info("editMenus", editMenus)
        saveOrUpdateMenu(editMenus).then(res => {
          let resData = res.resData;

          // 前面点击后缓存后的节点
          let data = this.node;
          let copuEidtMenus = this.VueUtils.deepClone(editMenus);
          if (this.optionType == "edit") { // 修改
            data.title = copuEidtMenus.title;
            data.menuCode = copuEidtMenus.menuCode;
            data.sortField = copuEidtMenus.sortField;
            data.icon = copuEidtMenus.icon;
            data.validInd = copuEidtMenus.validInd;
            data.display = copuEidtMenus.display;
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
          this.dialogFormVisible = false;
          this.initEditMenu();
          this.$message({
            type: 'success',
            message: '提交成功!'
          });
        });
      },
      // 对 MenuCode 校验
      checkMenuCode(rule, value, callback) {
        if (value === '') {
          callback(new Error("菜单CODE不能为空"));
          return false;
        }
        if (this.optionType == "edit") {
          let nodes = this.$refs.tree2.$refs.menuTree.getCheckedNodes();
          let oldTitleCode = nodes[0].menuCode;
          // 与旧的相同，说明没有修改过
          if (oldTitleCode === value) {
            callback();
          }
        }
        CheckMenuCode(value).then(res => {
          let resData = res.resData;
          if (resData >= 1) {
            callback(new Error("菜单CODE已经存在"));
          } else {
            callback();
          }
        });
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
</style>
