<template>
  <div>
    <div class="ms-doc">
      <h3>表达式</h3>
      <el-row>
        <el-col :span="3">
          <div class="grid-content bg-purple"></div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple-light">秒</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple">分钟</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple-light">小时</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple">日</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple-light">月</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple">周</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content bg-purple-light">年</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="3">
          <div class="grid-content">表达式字段：</div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content ">
            <el-input></el-input>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="3">
          <div class="grid-content">Cron 表达式：</div>
        </el-col>
        <el-col :span="18">
          <div class="grid-content">
            <el-input></el-input>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content">
            <el-button type="warning" size="mini">反解析到UI</el-button>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <div class="grid-content-item">最近5次运行时间：</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div class="grid-content-item">2018-04-05</div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

  export default {
    data: function () {
      return {
      }
    },
    methods: {

    }
  }
</script>

<style scoped>
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
