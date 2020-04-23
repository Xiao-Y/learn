<template>
  <div>
    <div class="ms-doc">
      <h3>数据字典信息</h3>
      <article>
        <el-form ref="dataDictionaryInfo" :model="dataDictionaryInfo" label-width="100px" size="mini">
          <el-form-item label="系统模块" prop="systemModule">
            <custom-select v-model="dataDictionaryInfo.systemModule"
              :datasource="systemModuleSelect"
              placeholder="请选择数据字典系统模块">
            </custom-select>
          </el-form-item>
          <el-form-item label="字典类型" prop="fieldType">
            <custom-select v-model="dataDictionaryInfo.fieldType"
              allow-create
              :datasource="fieldTypeSelect"
              placeholder="请选择数据字典类型">
            </custom-select>
          </el-form-item>
          <el-form-item label="字段VALUE" prop="fieldValue">
            <el-input v-model="dataDictionaryInfo.fieldValue" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="字段显示" prop="fieldDisplay">
            <el-input v-model="dataDictionaryInfo.fieldDisplay" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="字段说明" prop="descritpion">
            <el-input v-model="dataDictionaryInfo.descritpion" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="字段排序" prop="fieldOrder">
            <el-input-number v-model="dataDictionaryInfo.fieldOrder" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-switch v-model="dataDictionaryInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset('dataDictionaryInfo')">重置</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>

  </div>
</template>

<script>
  import {SaveOrUpdate} from "../../../api/sys/DataDictionaryMag";
  import CustomSelect from '../../../components/common/CustomSelect.vue';

  export default {
    components: {
      CustomSelect
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        dataDictionaryInfo: {
          systemModule: '',
          fieldType: '',
          fieldValue: '',
          fieldDisplay: '',
          descritpion: '',
          fieldOrder: 0,
          validInd: true
        },
        fieldTypeSelect: [],
        systemModuleSelect: [],
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      this.fieldTypeSelect = JSON.parse(this.$route.query.fieldTypeSelect);
      this.systemModuleSelect = JSON.parse(this.$route.query.systemModuleSelect);
      if (this.optionType === 'edit') {
        this.dataDictionaryInfo = JSON.parse(this.$route.query.dataDictionaryEdit);
      }
    },
    methods: {
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit') {
          SaveOrUpdate(_this.dataDictionaryInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            //传递一个map，updatePermission 是 key，resData 是 value
            this.$bus.emit('dataDictionaryInfo', res.resData);
            _this.$router.back(-1);
          });
        } else { // add
          SaveOrUpdate(_this.dataDictionaryInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '保存成功!'
            });
            //传递一个map，addPermission 是 key，resData 是 value
            this.$bus.emit('dataDictionaryInfo', res.resData);
            _this.$router.back(-1);
          });
        }
      },
      onReturn() {
        //调用router回退页面
        this.$router.back(-1);
      },
      onReset(dataDictionaryInfo) {
        this.$refs[dataDictionaryInfo].resetFields();
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

  .ms-doc article .el-checkbox {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }
</style>
