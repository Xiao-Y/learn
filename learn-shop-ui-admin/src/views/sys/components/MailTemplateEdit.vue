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
                               @change="pageShowChange"
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
                               @change="pageShowChange"
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
          <el-form-item label="运行SQL" prop="runSql" v-if="pageShow.runSqlShow" required>
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.runSql" rows="6"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="单行结果" prop="singleResult" v-if="pageShow.singleResultShow" required>
            <el-col :span="18">
              <el-switch v-model="mailTemplateInfo.singleResult" active-text="是" inactive-text="否"></el-switch>
            </el-col>
          </el-form-item>
          <el-form-item label="模板名称" prop="templateName" v-if="pageShow.templateNameShow" required>
            <el-col :span="18">
              <el-input v-model="mailTemplateInfo.templateName" rows="6" aria-placeholder="请输入模板文件的名称"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="收件人邮箱" prop="toEmails">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.toEmails"
                        placeholder="收件人邮箱可以在发送邮件时指定，并且优先使用指定的邮箱"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="邮件主题" prop="subject">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.subject"
                        placeholder="邮件主题可以在发送邮件时指定，并且优先使用指定的主题"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="邮件模板" prop="mailTemp" v-if="pageShow.mailTempShow">
            <el-col :span="18">
              <el-input type="textarea" v-model="mailTemplateInfo.mailTemp" rows="10"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item size="mini">
            <el-button type="primary" @click="validSubmit">保存</el-button>
            <el-button type="warning" @click="dialogMarkdownVisible = true" v-if="pageShow.mailMarkdownButtonShow">
              配置邮件模板
            </el-button>
            <el-button @click="onReturn">返回</el-button>
          </el-form-item>
        </el-form>
      </article>
    </div>
    <el-dialog title="Markdown 编辑器"
               v-if="dialogMarkdownVisible"
               :visible.sync="dialogMarkdownVisible"
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
          mailType: '1',
          dataSources: '1',
          runSql: '',
          singleResult: true,
          templateName: '',
          mailTemp: '',
          mailMarkdown: '',
          descritpion: '',
          toEmails: '',
          attachment: false,
          validInd: true
        },
        oldMailCode: '',
        mailTypeSelect: [],
        dataSourcesSelect: [],
        dialogMarkdownVisible: false,
        pageShow: {// 控制页面显示
          runSqlShow: false,
          singleResultShow: false,
          templateNameShow: false,
          mailTempShow: true,
          mailMarkdownButtonShow: false
        },
        rulesForm: {
          mailCode: [{required: true, message: '请输入账号', trigger: 'blur'},
            {validator: this.checkMailCode, trigger: 'blur'}],
          mailTemp: [{required: true, message: '请输入邮件模板内容', trigger: 'blur'}],
          runSql: [{validator: this.validateRunSql, trigger: 'blur'}],
          templateName: [{validator: this.validatetemplateName, trigger: 'blur'}],
          descritpion: [{required: true, message: '请输入邮件模板描述', trigger: 'blur'}],
          toEmails: [{validator: this.validateToEmails, trigger: 'blur'}],
          templateName: [{required: true, message: '请输入模板名称', trigger: 'blur'}],
        }
      };
    },
    created() {
      this.initPageShow();
      this.optionType = this.$route.query.optionType;
      this.mailTypeSelect = JSON.parse(this.$route.query.mailTypeSelect);
      this.dataSourcesSelect = JSON.parse(this.$route.query.dataSourcesSelect);
      if (this.optionType === 'edit') {
        if (this.$route.query.id) {
          FindMailTemplateById(this.$route.query.id).then(res => {
            this.mailTemplateInfo = res.resData;
            this.oldMailCode = res.resData.mailCode;
            this.pageShowChange();
          })
        }
      }
    },
    methods: {
      initPageShow() {
        Object.assign(this.pageShow, {
          runSqlShow: false,
          singleResultShow: false,
          templateNameShow: false,
          mailTempShow: true,
          mailMarkdownButtonShow: false
        });
      },
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
        this.dialogMarkdownVisible = false;
      },
      pageShowChange() {
        this.$refs['mailTemplateInfo'].clearValidate();
        // 默认
        this.initPageShow();
        // 邮件类型，1-普通邮件，2-html邮件,4-FreeMarker 模板邮件,5-Thymeleaf 模板邮件
        var mailType = this.mailTemplateInfo.mailType;
        // 数据来源，1-固定内容，2-SQL查询，3-参数设置,4-混合（2、3都有）
        var dataSources = this.mailTemplateInfo.dataSources;

        // console.info("mailType:", mailType)
        // console.info("dataSources:", dataSources)

        // 4-FreeMarker 模板邮件,5-Thymeleaf
        if (mailType === '4' || mailType === '5') {
          this.pageShow.templateNameShow = true;
          this.pageShow.mailTempShow = false;
        }
        // 2-html邮件
        if (mailType === '2') {
          this.pageShow.mailMarkdownButtonShow = true;
        }
        // 1-固定内容,3-参数设置
        if (dataSources !== '1' && dataSources !== '3') {
          this.pageShow.runSqlShow = true;
          if (mailType === '4' || mailType === '5') {
            this.pageShow.singleResultShow = true;
          }
        }
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
      },
      templateName(rule, value, callback) {
        if ((value === '' || value === null) && (this.mailTemplateInfo.mailType == '5' || this.mailTemplateInfo.mailType == '4')) {
          callback(new Error('当使用 Thymeleaf 或者 Freemarker 时，请输入模板名称'));
        } else {
          callback();
        }
      },
      validateToEmails(rule, value, callback) {
        if (value === '' || value === null) {
          callback();
          return true;
        }
        value = value.replace(/\s*|\t|\r|\n/g, '');
        this.mailTemplateInfo.toEmails = value;

        var message = '';
        var emails = value.split(";");
        for (var index in emails) {
          var email = emails[index];
          if (email === '') {
            continue;
          }
          var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
          if (!reg.test(email)) {
            message += email + ";";
          }
        }
        message !== '' ? callback(new Error(message + '邮箱格式不正确')) : callback();
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
