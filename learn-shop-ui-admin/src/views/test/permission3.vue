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
          <cron-select :unit="60" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
        <el-tab-pane label="分钟" name="minute">
          <cron-select :unit="60" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
        <el-tab-pane label="小时" name="hour">
          <cron-select :unit="24" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
        <el-tab-pane label="日" name="day">
          <cron-select :unit="31" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
        <el-tab-pane label="月" name="month">
          <cron-select :unit="12" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
        <el-tab-pane label="周" name="week">
          <cron-select :unit="7" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
        <el-tab-pane label="年" name="year">
          <cron-select :unit="0" :tabLabel="tabLabel" :reveal="reveal"></cron-select>
        </el-tab-pane>
      </el-tabs>
      <cron-expr></cron-expr>
    </div>
  </div>
</template>

<script>

  import cronSelect from "../../views/job/components/cronSelect"
  import cronExpr from "../../views/job/components/cronExpr"

  export default {
    components: {
      cronSelect,
      cronExpr
    },
    data: function () {
      return {
        activeName: 'second',
        tabLabel: null,
        reveal: ['01', '04', '05', '11'] // 指定显示的,默认全不显示
      }
    },
    methods: {
      handleClick(tab, event) {
        this.tabLabel = tab.label;
        var name = tab.name;
        if (name == 'second' || name == 'minute' || name == 'hour') {
          this.reveal = ['01', '04', '05', '11'];
        } else if (name == 'day') {
          this.reveal = ['01', '02', '04', '05', '06', '07', '11'];
        } else if (name == 'month') {
          this.reveal = ['01', '02', '04', '12', '11'];
        } else if (name == 'week') {
          this.reveal = ['01', '02', '08', '09', '10', '11'];
        } else if (name == 'year') {
          this.reveal = ['01', '03', '04'];
        }
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

  .el-row {
    margin-bottom: 0px;

  &
  :last-child {
    margin-bottom: 0;
  }

  }
  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    text-align: center;
    line-height: 36px;
    border-radius: 4px;
    min-height: 36px;
  }

  .grid-content-item {
    text-align: left;
    line-height: 36px;
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
