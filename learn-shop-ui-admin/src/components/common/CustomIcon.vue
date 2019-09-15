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
<style scoped>
  .ui-fas {
    overflow: auto;
    height: 400px;
  }

  .fas-icon-list li {
    width: 250px; /*如果显示三列 则width改为70px*/
    float: left;
    display: block;
  }

  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 3px;
    /*滚动条宽度*/
    height: 3px;
    /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px;
    /*滚动条的背景区域的圆角*/
    background-color: white;
    /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #2e363f;
    /*滚动条的背景颜色*/
  }
</style>
