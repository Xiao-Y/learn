<template>
  <table style="width: 100%;">
    <tr style="height: 350px">
      <el-tabs v-model="cron_tabs" type="border-card" style="height: 345px">
        <el-tab-pane v-for="obj in timeArray"
                     :key="obj.resultNum"
                     :label="obj.name"
                     :name="obj.resultNum">
          <div style="margin-top: 5px">
            <el-radio @change="changeRadio" v-model="obj.radio" label="1">{{obj.label}}</el-radio>
          </div>
          <!-- 天,月,周 公共的 -->
          <div v-if="obj.resultNum == 'day' || obj.resultNum == 'month' || obj.resultNum == 'week'">
            <div style="margin-top: 5px">
              <el-radio @change="changeRadio" v-model="obj.radio" label="2">不指定</el-radio>
            </div>
          </div>
          <div style="margin-top: 5px">
            <el-radio @change="changeRadio" v-model="obj.radio" label="3">周期 从
              <template>
                <template v-if="obj.resultNum == 'week'">{{obj.name}}</template>
                <el-input-number size="mini" v-model="obj.num.cycle1" controls-position="right"
                                 :max="maxNum" :min="minNum" @change="changeNumber"
                                 @focus="changeNumber('3')"></el-input-number>
                -
                <el-input-number size="mini" v-model="obj.num.cycle2" controls-position="right"
                                 :max="maxNum" :min="minNum" @change="changeNumber"
                                 @focus="changeNumber('3')"></el-input-number>
                <template v-if="obj.resultNum != 'week'">{{obj.name}}</template>
              </template>
            </el-radio>
          </div>
          <div style="margin-top: 5px">
            <el-radio @change="changeRadio" v-model="obj.radio" label="4">从
              <template>
                <el-input-number size="mini" v-model="obj.num.begin" controls-position="right" :max="maxNum"
                                 :min="minNum"
                                 @change="changeNumber" @focus="changeNumber('4')"></el-input-number>
                {{obj.name}}开始,每
                <el-input-number size="mini" v-model="obj.num.end" controls-position="right" :max="maxNum" :min="minNum"
                                 @change="changeNumber" @focus="changeNumber('4')"></el-input-number>
                {{obj.name}}执行一次
              </template>
            </el-radio>
          </div>
          <!-- 天 -->
          <div v-if="obj.resultNum == 'day'">
            <div style="margin-top: 5px">
              <el-radio @change="changeRadio" v-model="obj.radio" label="5">每月
                <template>
                  <el-input-number size="mini" v-model="obj.num.workDay" controls-position="right" :max="maxNum"
                                   :min="minNum" @change="changeNumber" @focus="changeNumber('5')"></el-input-number>
                  号最近的那个工作日
                </template>
              </el-radio>
            </div>
            <div style="margin-top: 5px">
              <el-radio @change="changeRadio" v-model="obj.radio" label="6">本月最后一天</el-radio>
            </div>
          </div>
          <!-- 周 -->
          <div v-if="obj.resultNum == 'week'">
            <div style="margin-top: 5px">
              <el-radio @change="changeRadio" v-model="obj.radio" label="7">第
                <template>
                  <el-input-number size="mini" v-model="obj.num.weekNum1" controls-position="right" :max="maxNum"
                                   :min="minNum" @change="changeNumber" @focus="changeNumber('7')"></el-input-number>
                  周的星期
                  <el-input-number size="mini" v-model="obj.num.weekNum2" controls-position="right" :max="maxNum"
                                   :min="minNum" @change="changeNumber" @focus="changeNumber('7')"></el-input-number>
                </template>
              </el-radio>
            </div>
            <div style="margin-top: 5px">
              <el-radio @change="changeRadio" v-model="obj.radio" label="8">本月最后一个星期
                <template>
                  <el-input-number size="mini" v-model="obj.num.weekLast" controls-position="right" :max="maxNum"
                                   :min="minNum" @change="changeNumber" @focus="changeNumber('8')"></el-input-number>
                </template>
              </el-radio>
            </div>
          </div>

          <div style="margin-top: 5px" v-if="obj.allElement.length > 0">
            <el-radio @change="changeRadio" v-model="obj.radio" label="9">指定
              <template>
                <el-checkbox-group v-model="obj.checked" @change="changeChecked">
                  <el-checkbox v-for="item in obj.allElement"
                               :label="item.value"
                               :key="item.label"
                               style="float:left;margin-left: 0px;">
                    {{item.label}}
                  </el-checkbox>
                </el-checkbox-group>
              </template>
            </el-radio>
          </div>
        </el-tab-pane>
      </el-tabs>
    </tr>
    <tr>
      <next-run-time :cronExp="triggerCron" :isTestRun="isTestRun"></next-run-time>
    </tr>
    <tr>
      <el-button type="primary" @click="saveCron" size="mini">确定</el-button>
      <el-button @click="cancelCron" size="mini">取消</el-button>
    </tr>
  </table>
</template>

<script>
  import NextRunTime from './NextRunTime'

  export default {
    components: {
      NextRunTime
    },
    props: {
      cron: {
        type: String,
        default: '* * * * * ? *'
      },
      // 是否立即运行
      isTestRun: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        triggerCron: '',// 传入后台
        cron_tabs: 'second', // 默认选中的tab
        second: {},
        minute: {},
        hour: {},
        day: {},
        month: {},
        week: {},
        year: {},
        // 用于el-tab-pane 循环
        timeArray: [],
      }
    },
    created() {
      this.initData();
      this.triggerCron = this.cron;
      this.cronTempToRadio();
    },
    methods: {
      initData: function () {
        // 初始化页面数据
        this.second = this.TimeBase('second', '秒');
        this.minute = this.TimeBase('minute', '分钟');
        this.hour = this.TimeBase('hour', '小时');
        this.day = this.TimeBase('day', '天');
        this.month = this.TimeBase('month', '月');
        this.week = this.TimeBase('week', '周');
        this.year = this.TimeBase('year', '年');

        this.timeArray.push(this.second);
        this.timeArray.push(this.minute);
        this.timeArray.push(this.hour);
        this.timeArray.push(this.day);
        this.timeArray.push(this.month);
        this.timeArray.push(this.week);
        this.timeArray.push(this.year);
        for (var i = 0; i < 60; i++) {
          var value = i;
          var label = value < 10 ? '0' + value : value + '';
          value = i + '';
          // 秒
          this.second.allElement.push({value: value, label: label});
          // 分
          this.minute.allElement = this.second.allElement;
          // 时
          if (i < 24) {
            this.hour.allElement.push({value: value, label: label});
          }
          value = (i + 1) + '';
          label = value < 10 ? '0' + value : value + '';
          value = value + '';
          // 天
          if (i < 31) {
            this.day.allElement.push({value: value, label: label});
          }
          // 周
          if (i < 7) {
            this.week.allElement.push({value: value, label: label});
          }
          // 月
          if (i < 12) {
            this.month.allElement.push({value: value, label: label});
          }
        }
      },
      // 时间基类
      TimeBase: function (resultNum, name) {
        var radio = '1';
        var label = '每' + name + ' 允许的通配符[, - * /]';
        if (resultNum === 'day') {
          label = '每天 允许的通配符[, - * / L W]';
        } else if (resultNum === 'week') {
          label = '每周 允许的通配符[, - * / L #]';
          radio = '2';
        }
        var begin = 1;
        if (resultNum === 'second' || resultNum === 'minute' || resultNum === 'hour') {
          begin = 0;
        }

        var rs = {
          name: name,
          resultNum: resultNum,
          allElement: [],
          radio: radio,
          checked: [],
          label: label,
          num: {
            cycle1: 1,
            cycle2: 2,
            begin: begin,
            end: 1,
            workDay: 1,
            weekNum1: 1,
            weekNum2: 1,
            weekLast: 1
          }
        };
        return rs;
      },
      getObject: function () {
        switch (this.cron_tabs) {
          case 'second':
            return this.second;
          case 'minute':
            return this.minute;
          case 'hour':
            return this.hour;
          case 'day':
            return this.day;
          case 'month':
            return this.month;
          case 'week':
            return this.week;
          case 'year':
            return this.year;
          default:
            return null;
        }
      },
      changeRadio() {
        var temp = this.getObject();
        if (temp.radio != 9) {
          temp.checked = [];
        }
        this.changetriggerCron();
      },
      changeNumber(radio) {
        if (radio && typeof (radio) == 'string') {
          var temp = this.getObject();
          temp.radio = radio;
        }
        this.changetriggerCron();
      },
      changeChecked() {
        var temp = this.getObject();
        temp.radio = '9';
        this.changetriggerCron();
      },
      changetriggerCron() {
        var cronTemp = ['*', '*', '*', '*', '*', '?', '*'];
        for (var index in this.timeArray) {
          var temp = this.timeArray[index];
          cronTemp[index] = this.radioToCronTemp(temp);
        }
        this.triggerCron = cronTemp.join(" ");
        // console.info(this.triggerCron);
      },
      radioToCronTemp(temp) {
        switch (temp.radio) {
          case '1':
            return '*';
          case '2':
            return '?';
          case '3':
            return temp.num.cycle1 + '-' + temp.num.cycle2;
          case '4':
            return temp.num.begin + '/' + temp.num.end;
          case '5':
            return temp.num.workDay + 'W';
          case '6':
            return 'L';
          case '7':
            return temp.num.weekNum1 + '#' + temp.num.weekNum2;
          case '8':
            return temp.num.weekLast + 'L';
          case '9':
            return temp.checked.join();
          default:
            return '*';
        }
      },
      cronTempToRadio() {
        if (this.triggerCron && this.triggerCron !== '') {
          var cronTempArray = this.triggerCron.split(" ");
          for (var index in cronTempArray) {
            var cronTemp = cronTempArray[index];
            var temp = this.timeArray[index];
            if (cronTemp === '*') {
              temp.radio = '1';
            } else if (cronTemp === '?') {
              temp.radio = '2';
            } else if (cronTemp.includes("-")) {
              temp.radio = '3';
              temp.num.cycle1 = cronTemp.split("-")[0];
              temp.num.cycle2 = cronTemp.split("-")[1];
            } else if (cronTemp.includes("/")) {
              temp.radio = '4';
              temp.num.begin = cronTemp.split("/")[0];
              temp.num.end = cronTemp.split("/")[1];
            } else if (cronTemp.includes("W")) {
              temp.radio = '5';
              temp.num.workDay = cronTemp.replace("W", "");
            } else if (cronTemp === "L") {
              temp.radio = '6';
            } else if (cronTemp.includes("#")) {
              temp.radio = '7';
              temp.num.weekNum1 = cronTemp.split("#")[0];
              temp.num.weekNum2 = cronTemp.split("#")[1];
            } else if (cronTemp.includes("L")) {
              temp.radio = '8';
              temp.num.weekLast = cronTemp.replace("W", "");
            } else {
              temp.radio = '9';
              cronTemp.includes(",") ? temp.checked = cronTemp.split(",") : temp.checked.push(cronTemp);
            }
          }
        }
      },
      saveCron() {
        this.$emit("saveCron", this.triggerCron);
      },
      cancelCron() {
        this.$emit("cancelCron", this.triggerCron);
      }
    },
    computed: {
      maxNum() {
        var obj = this.cron_tabs;
        return (obj === 'second' || obj === 'minute') ? 59 : obj === 'hour' ? 23 : obj === 'month' ? 12 : obj === 'day' ? 31 : 7;
      },
      minNum() {
        var obj = this.cron_tabs;
        return (obj === 'second' || obj === 'minute' || obj === 'hour') ? 0 : 1;
      }
    }
  }
</script>
