<template>
  <el-popover
    placement="left-start"
    width="540"
    trigger="click"
    @show="reset()"
    :disabled="disabled"
    popper-class="popper-class">
    <div class="ui-fas">
      <el-input v-model="iconNameTemp" @input.native="filterIcons" suffix-icon="el-icon-search"
                placeholder="请输入图标名称"></el-input>
      <ul class="fas-icon-list">
        <li v-for="(item, index) in iconList" :key="index" @click="selectedIcon(item)">
          <i class="iconfont" :class="['icon-' + item]"/>
          <span>{{item}}</span>
        </li>
      </ul>
    </div>
    <el-input slot="reference" placeholder="请输入内容" readonly :value="iconNameTemp" style="cursor: pointer;">
      <template slot="append"><i class="iconfont" :class="['icon-' + iconNameTemp]"></i></template>
    </el-input>
  </el-popover>
</template>
<script>
  import {fontawesome} from '../../static/icon/solid.js'

  export default {
    model: {
      // 双向绑定
      prop: 'iconName',
      event: 'change'
    },
    props: {
      disabled: {
        trpe: Boolean,
        default: false
      },
      iconName: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        iconNameTemp: '',
        iconList: fontawesome
      }
    },
    created() {
      this.iconNameTemp = this.iconName;
    },
    methods: {
      selectedIcon(iconName) {
        this.iconNameTemp = iconName;
        this.$emit('selected', iconName);
        this.$emit('change', iconName);
        // 选择完成后关闭
        document.body.click()
      },
      filterIcons() {
        if (this.iconNameTemp) {
          this.iconList = fontawesome.filter(item => item.includes(this.iconNameTemp))
        } else {
          this.iconList = fontawesome;
        }
      },
      reset() {
        this.iconNameTemp = '';
        this.iconList = fontawesome;
      }
    },
    watch: {
      iconName() {
        this.iconNameTemp = this.iconName;
      }
    }
  }
</script>
