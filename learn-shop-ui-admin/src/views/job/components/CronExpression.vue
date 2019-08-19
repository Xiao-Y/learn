<template>
  <table class="tp-table" cellpadding="0" cellspacing="0" style="width: 100%;height: 450px">
    <tr style="height: 350px">
      <el-tabs v-model="cron_tabs" type="border-card" style="margin:15px;">
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
                <el-input-number size="mini" v-model="obj.num.begin" controls-position="right"
                                 :max="maxNum" :min="minNum" @change="changeNumber"
                                 @focus="changeNumber('4')"></el-input-number>
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
                               :label="item"
                               :key="item"
                               style="float:left;margin-left: 15px;">
                    {{item}}
                  </el-checkbox>
                </el-checkbox-group>
              </template>
            </el-radio>
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
        default: '* * * * * ? *'
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
      this.$nextTick(() => {
        this.$refs.nextRunTime.testRun();
      });
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
          // 秒
          this.second.allElement.push(i);
          // 分
          this.minute.allElement = this.second.allElement;
          // 时
          if (i < 24) {
            this.hour.allElement.push(i + 1);
          }
          // 天
          if (i < 31) {
            this.day.allElement.push(i + 1);
          }
          // 周
          if (i < 7) {
            this.week.allElement.push(i + 1);
          }
          // 月
          if (i < 12) {
            this.month.allElement.push(i + 1);
          }
        }
      },
      // 时间基类
      TimeBase: function (resultNum, name) {
        var radio = "1";
        var label = '每' + name + ' 允许的通配符[, - * /]';
        if (resultNum === 'day') {
          label = '每天 允许的通配符[, - * / L W]';
        } else if (resultNum === 'week') {
          label = '每周 允许的通配符[, - * / L #]';
          radio = "2";
        }

        var rs = {
          name: name,
          resultNum: resultNum,
          allElement: [],
          radio: radio,
          checked: [],
          label: label,
          cronTemp: '',
          num: {
            cycle1: 1,
            cycle2: 2,
            begin: 1,
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
        if (radio) {
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

          switch (temp.radio) {
            case '1':
              temp.cronTemp = '*';
              break;
            case '2':
              temp.cronTemp = '?';
              break;
            case '3':
              temp.cronTemp = temp.num.cycle1 + '-' + temp.num.cycle2;
              break;
            case '4':
              temp.cronTemp = temp.num.begin + '/' + temp.num.end;
              break;
            case '5':
              temp.cronTemp = temp.num.workDay + 'W';
              break;
            case '6':
              temp.cronTemp = 'L';
              break;
            case '7':
              temp.cronTemp = temp.num.weekNum1 + '#' + temp.num.weekNum2;
              break;
            case '8':
              temp.cronTemp = temp.num.weekLast + 'L';
              break;
            case '9':
              temp.cronTemp = temp.checked.join();
              break;
            default:
              temp.cronTemp = '*';
          }
          cronTemp[index] = temp.cronTemp;
        }
        this.triggerCron = cronTemp.join(" ");
        console.info(this.triggerCron);
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
        if (obj === 'second' || obj === 'minute' || obj === 'hour') {
          var temp = this.getObject();
          temp.num.begin = 0;
          return 0;
        }
        return 1;
      }
    }
  }
</script>
