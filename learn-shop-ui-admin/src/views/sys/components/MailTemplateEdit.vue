<template>
  <div>
    <div class="ms-doc">
      <h3>邮件模板信息</h3>
      <article>
        <el-form ref="mailTemplateInfo" :model="mailTemplateInfo" :rules="rulesForm" label-width="100px" size="mini"
                 :inline-message="true">
          <el-form-item label="模板CODE" prop="mailCode">
            <el-col :span="18">
              <el-input v-model="mailTemplateInfo.mailCode" placeholder="请输入内容"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="邮件类型" prop="mailType">
            <template slot-scope="scope">
              <el-col :span="18">
                <custom-select v-model="mailTemplateInfo.mailType"
                               :datasource="mailTypeSelect"
                               :value-key="mailTemplateInfo.mailCode"
                               placeholder="请选择邮件类型">
                </custom-select>
              </el-col>
            </template>
          </el-form-item>
          <el-form-item label="数据来源" prop="dataSources">
            <template slot-scope="scope">
              <el-col :span="18">
                <custom-select v-model="mailTemplateInfo.dataSources"
                               :datasource="dataSourcesSelect"
                               :value-key="mailTemplateInfo.mailCode"
                               placeholder="请选择数据来源">
                </custom-select>
              </el-col>
            </template>
          </el-form-item>
          <el-form-item label="模板描述" prop="descritpion">
            <el-col :span="18">
              <el-input v-model="mailTemplateInfo.descritpion"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="有效标志" prop="validInd">
            <el-col :span="18">
              <el-switch v-model="mailTemplateInfo.validInd" active-text="有效" inactive-text="无效"></el-switch>
            </el-col>
          </el-form-item>
          <el-form-item label="运行SQL" prop="runSql"
                        :required="this.mailTemplateInfo.dataSources == '2' || this.mailTemplateInfo.dataSources == '4'">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.runSql"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="收件人邮箱" prop="toEmails">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.toEmails"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="邮件主题" prop="subject">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.subject"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="邮件模板" prop="mailTemp">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.mailTemp"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit">保存</el-button>
            <el-button type="primary" @click="dialogCronExpVisible = true">配置邮件模板</el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>
    <el-dialog title="Markdown 编辑器"
               v-if="dialogCronExpVisible"
               :visible.sync="dialogCronExpVisible"
               :close-on-click-modal="false">
      <markdown :markdown="mailTemplateInfo.mailMarkdown" @markdownReturn="markdownReturn"></markdown>
    </el-dialog>
  </div>
</template>

<script>
  import {
    SaveMailTemplate,
    UpdateMailTemplate,
    FindMailTemplateById,
    CheckMailCode
  } from "../../../api/sys/mailTemplateMag";
  import CustomSelect from '../../../components/common/CustomSelect.vue';
  import Markdown from '../../edit/Markdown.vue';

  export default {
    components: {
      CustomSelect,
      Markdown
    },
    data() {
      return {
        optionType: '', // 操作类型，edit,add
        mailTemplateInfo: {
          id: null,
          mailCode: '',
          mailType: '',
          dataSources: '',
          runSql: '',
          mailTemp: '',
          mailMarkdown: '',
          descritpion: '',
          validInd: true
        },
        oldMailCode: '',
        mailTypeSelect: [],
        dataSourcesSelect: [],
        dialogCronExpVisible: false,
        rulesForm: {
          mailCode: [{required: true, message: '请输入账号', trigger: 'blur'},
            {validator: this.checkMailCode, trigger: 'blur'}],
          mailTemp: [{required: true, message: '请输入邮件模板内容', trigger: 'blur'}],
          runSql: [{validator: this.validateRunSql, trigger: 'blur'}],
          descritpion: [{required: true, message: '请输入邮件模板描述', trigger: 'blur'}],
        }
      };
    },
    created() {
      this.optionType = this.$route.query.optionType;
      this.mailTypeSelect = JSON.parse(this.$route.query.mailTypeSelect);
      this.dataSourcesSelect = JSON.parse(this.$route.query.dataSourcesSelect);
      if (this.optionType === 'edit') {
        if (this.$route.query.id) {
          FindMailTemplateById(this.$route.query.id).then(res => {
            this.mailTemplateInfo = res.resData;
            this.oldMailCode = res.resData.mailCode;
          })
        }
      }
    },
    methods: {
      // 校验提交
      validSubmit() {
        var _this = this;
        this.$refs['mailTemplateInfo'].validate(valid => {
          if (valid) {
            _this.onSubmit();
          } else {
            return false;
          }
        });
      },
      onSubmit() {
        var _this = this;
        if (_this.optionType === 'edit') {
          UpdateMailTemplate(_this.mailTemplateInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '更新成功!'
            });
            //传递一个map，updateMailTemplate 是 key，resData 是 value
            this.$bus.emit('mailTemplateInfo', res.resData);
            _this.$router.back(-1);
          });
        } else { // add
          SaveMailTemplate(_this.mailTemplateInfo).then(res => {
            _this.$message({
              type: 'success',
              message: '保存成功!'
            });
            //传递一个map，addMailTemplate 是 key，resData 是 value
            this.$bus.emit('mailTemplateInfo', res.resData);
            _this.$router.back(-1);
          });
        }
      },
      onReturn() {
        //调用router回退页面
        this.$router.back(-1);
      },
      // markdown 返回的数据
      markdownReturn(data) {
        this.mailTemplateInfo.mailTemp = data.html;
        this.mailTemplateInfo.mailMarkdown = data.content;
        this.dialogCronExpVisible = false;
      },
      checkMailCode(rule, value, callback) {
        if (this.oldMailCode != '' && this.oldMailCode === value) {
          callback();
          return true;
        }
        CheckMailCode(value).then(res => {
          if (res.resData >= 1) {
            callback(new Error("邮件模板CODE已经存在"));
          } else {
            callback();
          }
        });
      },
      validateRunSql(rule, value, callback) {
        if ((value === '' || value === null) && (this.mailTemplateInfo.dataSources == '2' || this.mailTemplateInfo.dataSources == '4')) {
          callback(new Error('当数据来源为SQL和混合时，请输入运行SQL'));
        } else {
          callback();
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
</style>
