<template>
  <el-select
    @change="changeEvent"
    v-model="currentValue"
    :value-key="valueKey"
    :filterable="filterable"
    :default-first-option="defaultFirstOption"
    :multiple="multiple"
    :disabled="disabled"
    :placeholder="placeholder">
    <el-option
      v-for="item in currentSource"
      :key="item.id"
      :label="item.fieldDisplay"
      :value="item.fieldValue">
    </el-option>
  </el-select>
</template>

<script>

  import {LoadSysDataDictionary} from "../../api/sys/DataDictionaryMag";

  export default {
    // 双向绑定
    model: {
      prop: 'parentValues',
      event: 'change'
    },
    props: {
      valueKey:{
        type: String,
        default: null
      },
      // 是否可以过滤
      filterable: {
        type: Boolean,
        default: true
      },
      // 是否默认选种第一个
      defaultFirstOption: {
        type: Boolean,
        default: true
      },
      // 是否多选
      multiple: {
        type: Boolean,
        default: false
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
      // 下拉数据源
      datasource: {
        type: Array,
        default: null
      },
      fieldType: {
        type: String,
        default: null
      },
      parentValues: {
        type: Array,
        default: null
      }
    },
    data() {
      return {
        currentValue: [], // currentValue 当前选种的，接收父组件 v-model 传来的值；
        currentSource: [] // currentSource 当前数据源|；
      }
    },
    created() {
      // 当没有外部数据源并且fieldType不为空时，查询数据字典
      if (this.datasource == null && this.fieldType != null) {
        // console.info("查询数据字典 fieldType：", this.fieldType);
        this.LoadSysDataDictionary(this.fieldType);
      }
      // 当数据源中有数据时，加载数据源
      if (this.datasource && this.datasource.length > 0) {
        // console.info("created 加载数据源字典：", this.datasource);
        this.currentSource = this.datasource;
      }
      console.info("created parentValues：", this.parentValues);
      if(this.parentValues){
        this.currentValue = this.parentValues;
      }
    },
    methods: {
      changeEvent(val) {
        // 通知父级中的 v-model 数值变化
        this.$emit('change', val);
        // 通知父级中的 onchange 方法
        this.$emit('onchange', val);
      },
      //加载下拉列表
      LoadSysDataDictionary(fieldType) {
        LoadSysDataDictionary(fieldType).then(res => {
          this.currentSource = res.resData;
        });
      }
    },
    watch: {
      // 有时第一次加载的时候 created 中 datasource 为空，以下同理
      datasource: function (newVal, oldVal) {
        // console.info("watch 加载数据源字典：", newVal);
        this.currentSource = newVal;
      },
      parentValues:function (newVal, oldVal) {
        console.info("watch parentValues：", newVal);
        this.currentValue = newVal;
      }
    }
  }
</script>
