<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-setting"></i> 自述</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="ms-doc">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="秒" name="second">
          <el-form ref="form" :model="form" label-width="5px">
            <el-form-item>
              <el-radio v-model="sradio" label="1">每秒 允许的通配符[, - * /]</el-radio>
            </el-form-item>
            <el-form-item>
              <el-radio v-model="sradio" label="1">
                周期从
                <el-input-number v-model="num1" @change="handleChange" size="mini" :min="1" :max="60"
                                 label="描述文字"></el-input-number>
                -
                <el-input-number v-model="num1" @change="handleChange" size="mini" :min="1" :max="60"
                                 label="描述文字"></el-input-number>
                秒
              </el-radio>
            </el-form-item>
            <el-form-item>
              <el-radio v-model="sradio" label="1">
                从
                <el-input-number v-model="num1" @change="handleChange" size="mini" :min="1" :max="60"
                                 label="描述文字"></el-input-number>
                秒开始,每
                <el-input-number v-model="num1" @change="handleChange" size="mini" :min="1" :max="60"
                                 label="描述文字"></el-input-number>
                秒执行一次
              </el-radio>
            </el-form-item>
            <el-form-item>
              <el-radio v-model="sradio" label="1">
                指定
                <el-form-item>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                </el-form-item>
                <el-form-item>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                  <el-checkbox v-model="checked">备选项</el-checkbox>
                </el-form-item>
              </el-radio>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="分钟" name="minute">分钟</el-tab-pane>
        <el-tab-pane label="小时" name="hour">小时</el-tab-pane>
        <el-tab-pane label="日" name="day">日</el-tab-pane>
        <el-tab-pane label="月" name="month">月</el-tab-pane>
        <el-tab-pane label="周" name="week">周</el-tab-pane>
        <el-tab-pane label="年" name="year">年</el-tab-pane>
      </el-tabs>
      <div class="ms-doc">
        <h3>README.md</h3>
        <article>
          <h1>表达式</h1>

        </article>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    data: function () {
      return {
        activeName: 'second',
        sradio: "1",
        num1: 1
      }
    },
    methods: {
      handleClick() {

      },
      handleChange(){

      },
      checked(){

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

  .ms-doc {
    width: 100%;
    max-width: 980px;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif;
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
