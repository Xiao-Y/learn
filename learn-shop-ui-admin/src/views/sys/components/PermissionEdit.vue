<template>
  <div>
    <div class="ms-doc">
      <h3>权限信息</h3>
      <article>
        <el-form ref="permissionInfo" :model="permissionInfo" label-width="100px" size="mini">
          <el-form-item label="权限名称" prop="permissionName">
            <el-input v-model="permissionInfo.permissionName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="系统模块" prop="systemModule">
            <template slot-scope="scope">
              <custom-select v-model="permissionInfo.systemModule"
                             :datasource="systemModuleSelect"
                             placeholder="请选择系统模块">
              </custom-select>
            </template>
          </el-form-item>
          <el-form-item label="授权链接" prop="url">
            <el-input v-model="permissionInfo.url" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="权限CODE" prop="permissionCode">
            <el-input v-model="permissionInfo.permissionCode" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="所属菜单" prop="menuName">
            <el-input v-model="permissionInfo.menuName">
              <template slot="append">
                <el-button @click="dialogFormVisible = true">选择菜单</el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="权限描述" prop="description">
            <el-input v-model="permissionInfo.description" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="permissionInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('permissionInfo')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" title="选择菜单">
      <custom-menu-tree ref="menuTree" :checkStrictly="true" :defaultCheckedMenu="permissionInfo.menuIds" :expandAll="false"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="clickMenuSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {FindMenuByPermissionId} from "../../../api/sys/menuMag";
import {SavePermission, UpdatePermission} from "../../../api/sys/permissionMag";
import CustomSelect from '../../../components/common/CustomSelect.vue';
import CustomMenuTree from '../../../components/common/CustomMenuTree.vue';

export default {
  components: {
    CustomSelect,
    CustomMenuTree
  },
  data() {
    return {
      optionType: '', // 操作类型，edit,add
      permissionInfo: {
        permissionName: '',
        permissionCode: '',
        url: '',
        systemModule: '',
        description: null,
        // systemModules: [],
        // 多个英文逗号分隔
        menuName: "",
        // 选种的菜单
        menuIds: [],
        validInd: true
      },
      // 系统模块选择数据源
      systemModuleSelect: [],
      // 菜单树弹出层
      dialogFormVisible: false,
    };
  },
  created() {
    this.optionType = this.$route.query.optionType;
    this.systemModuleSelect = JSON.parse(this.$route.query.systemModuleSelect);
    if (this.optionType === 'edit') {
      this.permissionInfo = JSON.parse(this.$route.query.permissionEdit);
    }
    // 加载选种的菜单
    this.loadCheckMenu(this.permissionInfo.id);
  },
  methods: {
    // 加载选种的菜单
    loadCheckMenu(permissionId) {
      if (!permissionId) {
        return;
      }
      FindMenuByPermissionId(permissionId).then(res => {
        if (res.resData) {
          this.permissionInfo.menuName = res.resData.map(m => m.menuName).join();
          this.permissionInfo.menuIds = res.resData.map(m => m.id);
        }
      });
    },
    // 打开菜单树
    clickMenuSubmit() {
      let checkDataAll = this.$refs.menuTree.getCheckDataAll();
      // console.info("checkDataAll",checkDataAll);
      this.permissionInfo.menuName = checkDataAll.map(m => m.menuName).join();
      this.permissionInfo.menuIds = checkDataAll.map(m => m.id);
      this.dialogFormVisible = false;
    },
    onSubmit() {
      var _this = this;
      if (_this.optionType === 'edit') {
        UpdatePermission(_this.permissionInfo).then(res => {
          _this.$message({
            type: 'success',
            message: '更新成功!'
          });
          //传递一个map，updatePermission 是 key，resData 是 value
          this.$bus.emit('permissionInfo', res.resData);
          _this.$router.back(-1);
        });
      } else { // add
        SavePermission(_this.permissionInfo).then(res => {
          _this.$message({
            type: 'success',
            message: '保存成功!'
          });
          //传递一个map，addPermission 是 key，resData 是 value
          this.$bus.emit('permissionInfo', res.resData);
          _this.$router.back(-1);
        });
      }
    },
    onReturn() {
      //调用router回退页面
      this.$router.back(-1);
    },
    onReset(permissionInfo) {
      this.$refs[permissionInfo].resetFields();
    }
  },
  watch: {
    'permissionInfo.permissionName'() {
      this.permissionInfo.description = this.permissionInfo.permissionName;
    },
    'permissionInfo.url'() {
      var splitAt = this.permissionInfo.url.split("/");
      if (splitAt[1] && splitAt[2]) {
        let suf = "sys:";
        let systemModule = this.permissionInfo.systemModule;
        // console.info("systemModule",systemModule);
        if (systemModule === 'admin-user') {
          suf = "user:";
        } else if (systemModule === 'core-product') {
          suf = "pms:";
        } else if (systemModule === 'public-job') {
          suf = "job:";
        } else if (systemModule === 'core-order') {
          suf = "oms:";
        } else if (systemModule === 'core-search') {
          suf = "sch:";
        } else if (systemModule === 'core-seckill') {
          suf = "sk:";
        } else if (systemModule === 'public-auth') {
          suf = "auth:";
        }
        this.permissionInfo.permissionCode = suf + splitAt[1].substr(0,splitAt[1].length - 3) + ":" + splitAt[2];
      }
    }
  }
};
</script>

<style scoped>
.ms-doc {
  width: 70%;
  margin: 0 auto;
}

.ms-doc h3 {
  padding: 9px 10px 10px;
  margin: 0;
  font-size: 14px;
  line-height: 17px;
  background-color: #f5f5f5;
  border: 1px solid #d8d8d8;
  border-bottom: 0;
  border-radius: 3px 3px 0 0;
}

.ms-doc article {
  padding: 10px;
  word-wrap: break-word;
  background-color: #fff;
  border: 1px solid #ddd;
  border-bottom-right-radius: 3px;
  border-bottom-left-radius: 3px;
}

/*.ms-doc article h1 {*/
/*font-size: 32px;*/
/*padding-bottom: 10px;*/
/*margin-bottom: 15px;*/
/*border-bottom: 1px solid #ddd;*/
/*}*/

/*.ms-doc article h2 {*/
/*margin: 24px 0 16px;*/
/*font-weight: 600;*/
/*line-height: 1.25;*/
/*padding-bottom: 7px;*/
/*font-size: 24px;*/
/*border-bottom: 1px solid #eee;*/
/*}*/

/*.ms-doc article p {*/
/*margin-bottom: 15px;*/
/*line-height: 1.5;*/
/*}*/

.ms-doc article .el-checkbox {
  margin-bottom: 5px;
}

.el-form-item {
  margin-bottom: 3px;
}
</style>
