<template>
  <div style="float:left;">
    <el-tooltip class="item" effect="dark" content="禁用" placement="top-start" :open-delay="openDelay" v-if="showInd">
      <el-button @click="onInd" type="warning" size="mini" :disabled="disInd">
        <i class="el-icon-warning"></i>
      </el-button>
    </el-tooltip>
    <el-tooltip class="item" effect="dark" content="删除" placement="top-start" :open-delay="openDelay" v-if="showDel">
      <el-button @click="onDel" type="danger" size="mini" :disabled="disDel">
        <i class="el-icon-delete"></i>
      </el-button>
    </el-tooltip>
    <el-tooltip class="item" effect="dark" content="修改" placement="top-start" :open-delay="openDelay" v-if="showEdit">
      <el-button @click="onEdit" type="primary" size="mini" :disabled="disEdit">
        <i class="el-icon-edit"></i>
      </el-button>
    </el-tooltip>
  </div>
</template>

<script>
  export default {
    props: {
      // 提示信息延时
      openDelay: {
        type: Number,
        default: 1500
      },
      // 是否显示禁用按钮
      showInd: {
        type: Boolean,
        default: true
      },
      // 是否禁用禁用按钮
      disInd: {
        type: Boolean,
        default: false
      },
      // 是否显示删除按钮
      showDel: {
        type: Boolean,
        default: true
      },
      // 是否禁用删除按钮
      disDel: {
        type: Boolean,
        default: false
      },
      // 是否显示修改按钮
      showEdit: {
        type: Boolean,
        default: true
      },
      // 是否禁用修改按钮
      disEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {}
    },
    methods: {
      // 禁用事件
      onInd() {
        this.$emit('onInd');
      },
      // 删除事件
      onDel() {
        this.$emit('onDel');
      },
      // 修改事件
      onEdit() {
        this.$emit('onEdit');
      }
    }
  }
</script>

<style>
</style>
