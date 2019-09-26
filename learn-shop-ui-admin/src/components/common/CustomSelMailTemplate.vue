<template>
  <div>
    <el-input :value="mailCode" :size="size" placeholder="请输入内容" :readonly="inputReadOnly">
      <el-button slot="append" icon="el-icon-setting" :disabled="buttonDisabled" @click="mailTemplateDialog">
        {{buttonText}}
      </el-button>
    </el-input>

    <!-- dialog 选择邮件模板 -->
    <el-dialog title="选择邮件模板"
               v-if="dialogVisible"
               :fullscreen="true"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false">
      <mail-template-list @change="selectMailTemplate" :selectView="true" :mailCode="mailCode"></mail-template-list>
    </el-dialog>
  </div>
</template>

<script>
  import MailTemplateList from '../../views/sys/MailTemplateList.vue';

  import {
    FindMailTemplateById,
  } from "../../api/sys/mailTemplateMag";

  export default {
    components: {
      MailTemplateList
    },
    name: "CustomSelMailTemplate",
    model: {
      // 双向绑定
      prop: 'templateId',
      event: 'change'
    },
    props: {
      // 输入域大小
      size: {
        type: String,
        default: ''
      },
      // button 显示的文字
      buttonText: {
        type: String,
        default: '配置'
      },
      // 是否禁用button
      buttonDisabled: {
        type: Boolean,
        default: false
      },
      // 是否只读input
      inputReadOnly: {
        type: Boolean,
        default: false
      },
      // 双向绑定
      templateId: {
        type: Number,
        default: null
      }
    },
    data() {
      return {
        dialogVisible: false,
        mailCode: null
      }
    },
    created() {
      if (this.templateId) {
        FindMailTemplateById(this.templateId).then(res => {
          this.mailCode = res.resData.mailCode;
        });
      }
    },
    methods: {
      selectMailTemplate(row) {
        this.$emit('change', row.id);
        this.mailCode = row.mailCode;
        this.dialogVisible = false;
      },
      // 打开 mailTemplate 选择窗口
      mailTemplateDialog() {
        this.dialogVisible = true;
      },
      cancel() {
        this.dialogVisible = false;
      }
    }
  }
</script>
