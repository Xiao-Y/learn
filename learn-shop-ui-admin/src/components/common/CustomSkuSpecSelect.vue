<!-- 下拉列表组件： 通过 category_id 查询 p_goods_spec_key 表中有效数据，当选定后，自动带出 p_goods_spec_value 表中的值。
  如果指定 key 和 value 则直接带出 name -->
<template>
  <div class="spec_select">
    <el-select
      @change="changeEvent(true)"
      @clear="clearEvent(null,null)"
      v-model="specKeyTemp"
      :filterable="filterable"
      :default-first-option="defaultFirstOption"
      :disabled="true"
      :size="size"
      :clearable="clearable"
      :placeholder="placeholder">
      <el-option
        v-for="item in selectKeyData"
        :key="item.id"
        :label="item.specName"
        :value="item.id">
      </el-option>
    </el-select>
    <el-select
      @change="changeEvent(false)"
      @clear="clearEvent(specKeyTemp,null)"
      v-model="specValueTemp"
      :filterable="filterable"
      :default-first-option="defaultFirstOption"
      :disabled="disabled"
      :size="size"
      :clearable="clearable"
      :placeholder="placeholder">
      <el-option
        v-for="item in currentValues"
        :key="item.id"
        :label="item.specValue"
        :value="item.id">
      </el-option>
    </el-select>
  </div>
</template>

<script>

  import {FindValueListBySpecKeyId} from "../../api/product/GoodsSpecValueApi";

  export default {
    props: {
      // 是否可以过滤
      filterable: {
        type: Boolean,
        default: true
      },
      // 在输入框按下回车，选择第一个匹配项
      defaultFirstOption: {
        type: Boolean,
        default: true
      },
      // 是否禁用
      disabled: {
        type: Boolean,
        default: false
      },
      // 提示信息
      placeholder: {
        type: String,
        default: '请选择'
      },
      // 下拉的大小
      size: {
        type: String,
        default: 'mini'
      },
      clearable: {
        type: Boolean,
        default: true
      },
      // 选种的下拉
      specKey: {
        type: String,
        default: null
      },
      // 选种的下拉
      specValue: {
        type: String,
        default: null
      },
      index: {
        type: Number,
        default: null
      },
      selectKeyData: {
        type: Array,
        default: new Array()
      }
    },
    data() {
      return {
        specKeyTemp: null, // specKey 当前选种的，接收父组件 v-model 传来的值；
        specValueTemp: null, // specValue 当前选种的，接收父组件 v-model 传来的值；
        currentValues: [], // currentValues 当前数据源|；
      }
    },
    created() {
      this.specKeyTemp = this.specKey;
      this.specValueTemp = this.specValue;
    },
    methods: {
      /**
       * 下拉列表切换时触发，用于通知父组件
       * @param isCheck 是否检查重复，key 需要检查，value 不需要
       */
      changeEvent(isCheck) {
        var specValue = null;
        if (this.specValueTemp) {
          var value = this.currentValues.find(f => {
            return f.id === this.specValueTemp;
          });
          specValue = value.specValue;
        }
        this.$emit("change", this.index, this.specKeyTemp, this.specValueTemp, specValue, isCheck);
      },
      /**
       * 对子组件设置值
       * @param specKeyId
       * @param specValueId
       */
      setSpecKey(specKeyId, specValueId) {
        this.specKeyTemp = specKeyId;
        this.specValueTemp = specValueId;
        this.LoadSelectValueData(specKeyId);
      },
      /**
       * 点击下拉的clear 时触发
       * @param specKeyId
       * @param specValueId
       */
      clearEvent(specKeyId, specValueId) {
        if (specKeyId === null) {
          this.specValueTemp = null;
        }
        var specValue = null;
        if (this.specValueTemp) {
          var value = this.currentValues.find(f => {
            return f.id === this.specValueTemp;
          });
          specValue = value.specValue;
        }
        this.$emit("change", this.index, specKeyId, specValueId, specValue, false);
      },
      //加载下拉列表
      LoadSelectValueData(specKeyId) {
        if (specKeyId) {
          FindValueListBySpecKeyId(specKeyId).then(res => {
            this.currentValues = res.resData;
          });
        }
      }
    },
    watch: {
      // key 加载完成后加载 value
      selectKeyData() {
        this.LoadSelectValueData(this.specKeyTemp);
      }
    }
  }
</script>

<style scoped>
  .spec_select {
    margin-bottom: 5px;
  }
</style>
