<template>
  <div>
    <el-form ref="form" label-width="5px">
      <el-form-item>
        <el-radio v-model="sradio" label="1">每{{tabLabel}} 允许的通配符[, - * /]</el-radio>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="sradio" label="1">
          周期从
          <el-input-number v-model="start" @change="handleChange" size="mini" :min="0" :max="60"
                           label="描述文字"></el-input-number>
          -
          <el-input-number v-model="end" @change="handleChange" size="mini" :min="1" :max="60"
                           label="描述文字"></el-input-number>
          {{tabLabel}}
        </el-radio>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="sradio" label="1">
          从
          <el-input-number v-model="start" @change="handleChange" size="mini" :min="0" :max="60"
                           label="描述文字"></el-input-number>
          {{tabLabel}}开始,每
          <el-input-number v-model="end" @change="handleChange" size="mini" :min="1" :max="60"
                           label="描述文字"></el-input-number>
          {{tabLabel}}执行一次
        </el-radio>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="sradio" label="1">
          指定
          <el-form-item v-for="(item,index) in countArray" :key="index">
            <el-checkbox v-for="(item1,index1) in item" :key="index1">{{item1}}</el-checkbox>
          </el-form-item>
        </el-radio>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  // 初始化单位,每行的最大容量
  const NUM = 12;

  export default {
    props: {
      unit: null, // 总单位数据
      tabLabel:null
    },
    data: function () {
      return {
        sradio: 1,
        start: 0,
        end: 2,
        countArray: []
      }
    },
    created() {
      // 初始化单位数据
      this.initCount();
    },
    methods: {
      /**
       * 初始化单位数据
       */
      initCount() {
        var count = parseInt(this.unit / NUM); // 整数
        var remainder = this.unit % NUM; // 余数

        // 只有一行时
        if (count == 0) {
          this._initRow(count, remainder);
        } else if (count > 0) { // 多行时
          for (var i = 0; i < count; i++) {
            this._initRow(i, NUM);
          }
          // 有余数时
          if (remainder !== 0) {
            this._initRow(count, remainder);
          }
        }
      },
      handleClick() {

      },
      handleChange() {

      },
      checked() {

      },
      /**
       * 初始化第一行的数据
       * @param row 第几行,从0开始
       * @param remainder 迭代多少次
       * @private
       */
      _initRow(row, remainder) {
        var _this = this;
        var unit = [];
        for (var j = 0; j < remainder; j++) {
          var temp = row * NUM + j;
          if (temp < 10) {
            temp = "0" + temp;
          }
          unit.push(temp);
        }
        _this.countArray.push(unit);
      }
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 10px;
  }

  .el-form-item {
    margin-bottom: 3px;
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
    padding: 45px;
    word-wrap: break-word;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px;
  }

  .ms-doc article h1 {
    font-size: 32px;
    padding-bottom: 10px;
    margin-bottom: 15px;
    border-bottom: 1px solid #ddd;
  }

  .ms-doc article h2 {
    margin: 24px 0 16px;
    font-weight: 600;
    line-height: 1.25;
    padding-bottom: 7px;
    font-size: 24px;
    border-bottom: 1px solid #eee;
  }

  .ms-doc article p {
    margin-bottom: 15px;
    line-height: 1.5;
  }
</style>
