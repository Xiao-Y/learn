<template>
  <table class="tp-table" cellpadding="0" cellspacing="0" style="width: 100%;height: 450px">
    <tr style="height: 280px">
      <el-tabs v-model="cron_tabs" type="border-card" @tab-click="handleTabClick" style="margin:15px;">
        <el-tab-pane v-for="obj in timeArray"
                     :key="obj.resultNum"
                     :label="obj.name"
                     :name="obj.name">

          <el-select v-model="obj.selected" @change="handleSelectChange" style="width: 30%;vertical-align: top; height: 180px">
            <el-option v-for="item in obj.select"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value"/>
            </el-option>
          </el-select>

          <div style="width: 60%;margin-left: 15px;display: inline-block;">
            <el-date-picker v-if="obj.selected == 1" type="datetime"
                            :disabled="isReadonly"
                            v-model="triggerCronShow"
                            @change="handleDataChange">
            </el-date-picker>

            <div class="checkbox-content-div" v-if="obj.selected == 2">
              <el-checkbox-group v-model="obj.checked" @change="handleCheckedChange">
                <el-checkbox v-for="item in obj.allElement"
                             :label="item"
                             :key="item"
                             style="float:left;margin-left: 15px;">
                  {{item}}
                </el-checkbox>
              </el-checkbox-group>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </tr>
    <tr>
      <auto-task-next-run-time ref="nextRunTime" :cron="triggerCron"></auto-task-next-run-time>
    </tr>
    <tr>
      <el-button type="primary" @click="saveCron" size="mini">确定</el-button>
      <el-button @click="cancelCron" size="mini">取消</el-button>
    </tr>
  </table>
</template>

<script>
  import AutoTaskNextRunTime from './AutoTaskNextRunTime'

  export default {
    components: {
      AutoTaskNextRunTime
    },
    props: {
      cron: {
        type: String,
        default: '* * * * * ?'
      }
    },
    data() {
      return {
        triggerCron: '* * * * * ?',// 传入后台
        triggerCronShow: null, // 页面显示的
        second: {},
        minute: {},
        hour: {},
        day: {},
        month: {},
        // 用于el-tab-pane 循环
        timeArray: [],
        // 默认选中的tab
        cron_tabs: '秒',
        cron_result: ['', '', '', '', '', '?'],
        isReadonly: false,
      }
    },
    created() {
      this.initData();
      this.triggerCron = this.cron;
      this.$nextTick(() => {
        this.$refs.nextRunTime.testRun();
      });
    },
    methods: {
      initData: function () {
        // 给调度时间一个默认值
        // this.triggerCronShow = new Date();
        // this.triggerCron = this.formatTime(this.triggerCronShow);
        // 初始化页面数据
        this.second = this.TimeBase(0, '秒');
        this.minute = this.TimeBase(1, '分钟');
        this.hour = this.TimeBase(2, '小时');
        this.day = this.TimeBase(3, '天');
        this.month = this.TimeBase(4, '月');
        this.timeArray.push(this.second);
        this.timeArray.push(this.minute);
        this.timeArray.push(this.hour);
        this.timeArray.push(this.day);
        this.timeArray.push(this.month);
        for (var i = 0; i < 60; i++) {
          var value;
          if (i < 10) {
            value = '0' + i;
          } else {
            value = i;
          }
          // 秒、分
          this.second.allElement.push(value);
          // 时
          if (i < 24) {
            this.hour.allElement.push(i + 1);
          }
          // 天
          if (i < 31) {
            this.day.allElement.push(i + 1);
          }
          // 月
          if (i < 12) {
            this.month.allElement.push(i + 1);
          }
        }
        this.minute.allElement = this.second.allElement;
      },
      // 时间基类
      TimeBase: function (resultNum, name) {
        var rs = {
          name: name,
          resultNum: resultNum,
          allElement: [],
          checked: [],
          select: [{
            value: '0',
            label: '每' + name
          },
            {
              value: '1',
              label: '指定时间'
            },
            {
              value: '2',
              label: '循环'
            }
          ],
          selected: '1',
          temp1: '',
          temp2: ''
        };
        return rs;
      },
      formatTime: function (time) {
        var rs = time.getSeconds() + ' ' +
          time.getMinutes() + ' ' +
          time.getHours() + ' ' +
          time.getDate() + ' ' +
          (time.getMonth() + 1) + ' ? ' +
          time.getFullYear();
        return rs;
      },
      getObject: function () {
        switch (this.cron_tabs) {
          case '秒':
            return this.second;
          case '分钟':
            return this.minute;
          case '小时':
            return this.hour;
          case '天':
            return this.day;
          case '月':
            return this.month;
          default:
            return null;
        }
      },
      removeZero: function (str) {
        if (str.__proto__.constructor === Array) {
          for (var i = 0; i < str.length; i++) {
            str[i] = parseInt(str[i])
          }
        }
        return str;
      },
      handleTabClick: function () {
      },
      handleSelectChange: function () {
        var temp = this.getObject();
        // 指定时间选项
        this.triggerCronShow = '';
        // 周期选项
        temp.checked = [];
        this.cron_result[temp.resultNum] = '';
        // 3、4选项
        temp.temp1 = '';
        temp.temp2 = '';
        this.changetriggerCron(temp);
      },
      // 周期选项内 的checked组变更时间
      handleCheckedChange: function () {
        // 根据tabs标题确定目前操作的时间类型，并返回集合次类型的对象
        var temp = this.getObject();
        this.changetriggerCron(temp);
      },
      changetriggerCron: function (temp) {
        // 周期选项处理
        if (temp.selected === '2') {
          // 深拷贝选中值 后面有去0操作
          this.cron_result[temp.resultNum] = JSON.parse(JSON.stringify(temp.checked));
        }
        this.triggerCron = '';
        for (var i = 0; i < this.cron_result.length; i++) {
          if (this.cron_result[i] === '') {
            this.triggerCron += '* ';
          } else {
            this.triggerCron += this.removeZero(this.cron_result[i]) + ' ';
          }
        }
      },
      handleDataChange: function () {
        if (typeof (this.triggerCronShow) === 'undefined' ||
          this.triggerCronShow === '') {
          return;
        }
        this.triggerCron = this.formatTime(this.triggerCronShow);
      },
      saveCron() {
        this.$emit("saveCron", this.triggerCron);
      },
      cancelCron() {
        this.$emit("cancelCron", this.triggerCron);
      }
    },
    watch: {
      'cron':function () {
        this.triggerCron = this.cron;
      }
    }
  }
</script>
