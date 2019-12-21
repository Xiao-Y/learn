<template>
  <div>
    <div class="ms-doc">
      <h3>权限信息</h3>
      <article>
        <el-form ref="permissionInfo" :model="permissionInfo" label-width="100px" size="mini">
          <el-form-item label="权限名称" prop="permissionName">
            <el-input v-model="permissionInfo.permissionName" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="权限CODE" prop="permissionCode">
            <el-input v-model="permissionInfo.permissionCode" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="授权链接" prop="url">
            <el-input v-model="permissionInfo.url" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="系统模块" prop="systemModules">
            <template slot-scope="scope">
              <custom-select v-model="permissionInfo.systemModules"
                             :datasource="systemModuleSelect"
                             placeholder="请选择系统模块"
                             multiple>
              </custom-select>
            </template>
          </el-form-item>
          <el-form-item label="权限描述" prop="descritpion">
            <el-input type="textarea" v-model="permissionInfo.descritpion"></el-input>
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

  </div>
</template>

<script>
  import {SavePermission, UpdatePermission} from "../../../api/sys/permissionMag";
  import CustomSelect from '../../../components/common/CustomSelect.vue';

  export default {
    components: {
      CustomSelect
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        permissionInfo: {
          permissionName: '',
          permissionCode: '',
          url: '',
          systemModule: '',
          descritpion: null,
          systemModules: [],
          validInd: true
        },
        systemModuleSelect: []
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      this.systemModuleSelect = JSON.parse(this.$route.query.systemModuleSelect);
      if (this.optionType === 'edit') {
        this.permissionInfo = JSON.parse(this.$route.query.permissionEdit);
      }
    },
    methods: {
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
        this.permissionInfo.descritpion = this.permissionInfo.permissionName;
      },
      'permissionInfo.url'() {
        var splitAt = this.permissionInfo.url.split("/");
        if (splitAt[1] && splitAt[2]) {
          this.permissionInfo.permissionCode = splitAt[1].replace(splitAt[1][0], splitAt[1][0].toUpperCase()) + "-" + splitAt[2]
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
