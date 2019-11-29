<!-- 下拉列表组件： 通过 category_id 查询 p_goods_spec_key 表中有效数据，当选定后，自动带出 p_goods_spec_value 表中的值。
  如果指定 key 和 value 则直接带出 name -->
<template>
  <div class="spec_select">
    <el-select
      @change="changeKeyEvent"
      v-model="specKeyTemp"
      :filterable="filterable"
      :disabled="disabled"
      :size="size"
      :clearable="clearable"
      :placeholder="placeholder">
      <el-option
        v-for="item in currentKeys"
        :key="item.id"
        :label="item.specName"
        :value="item.id">
      </el-option>
    </el-select>
    <el-select
      @change="changeValueEvent"
      v-model="specValueTemp"
      :filterable="filterable"
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

  import {FindListByCategoryId, FindListBySpecKeyId} from "../../api/product/GoodsSpecApi";

  export default {
    props: {
      // 是否可以过滤
      filterable: {
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
        default: false
      },
      categoryId: {
        type: String,
        default: null
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
      validData: {
        default: null
      }
    },
    data() {
      return {
        specKeyTemp: null, // specKey 当前选种的，接收父组件 v-model 传来的值；
        specValueTemp: null, // specValue 当前选种的，接收父组件 v-model 传来的值；
        validDataTemp: null, // specValue 当前选种的，接收父组件 v-model 传来的值；
        currentKeys: [], // currentKeys 当前数据源|；
        currentValues: [], // currentValues 当前数据源|；
      }
    },
    created() {
      this.specKeyTemp = this.specKey;
      this.specValueTemp = this.specValue;
      this.validDataTemp = this.validData;

      if (this.categoryId !== null) {
        this.LoadSelectKeyData(this.categoryId);
      }
      // console.info("this.validData:",this.validData)
    },
    methods: {
      changeKeyEvent(val) {
        // console.info(this.validDataTemp);
        // console.info(this.specKey);
        if (this.validDataTemp) {
          var index = this.validDataTemp.findIndex(f => f.specKeyId == val);
          if (index !== -1) {
            this.specKeyTemp = this.specKey;
            if (this.specKeyTemp === null) {
              this.specValueTemp = null;
              this.currentValues = [];
            }
            this.$message({
              showClose: true,
              message: '规格类型不能重复',
              type: 'error'
            });
            return;
          }
        }
        this.specValueTemp = null;
        this.currentValues = [];
        if (val) {
          this.LoadSelectValueData(val);
        }
        this.$emit("change", this.specKey, this.specKeyTemp, this.specValue, this.specValueTemp);
      },
      changeValueEvent() {
        this.$emit("change", this.specKey, this.specKeyTemp, this.specValue, this.specValueTemp);
      },
      //加载下拉列表
      LoadSelectKeyData(categoryId) {
        FindListByCategoryId(categoryId).then(res => {
          this.currentKeys = res.resData;
        });
      },
      LoadSelectValueData(specKeyId) {
        FindListBySpecKeyId(specKeyId).then(res => {
          this.currentValues = res.resData;
        });
      }
    },
    watch: {
      currentKeys() {
        this.LoadSelectValueData(this.specKeyTemp);
      },
      validData: {
        handler(newName, oldName) {
          this.validDataTemp = this.validData;
          // console.info("watch:newName", newName);
          // console.info("watch:oldName", oldName);
          // console.info("watch:", this.validData);
        },
        deep: true
      }
    }
  }
</script>

<style scoped>
  .spec_select {
    margin-bottom: 5px;
  }
</style>
