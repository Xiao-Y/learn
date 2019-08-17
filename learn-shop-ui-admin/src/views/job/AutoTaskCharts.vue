<template>
  <div>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="barMain" style="width: 600px;height:400px;"></div>
    <div id="pieMain" style="width: 600px;height:400px;"></div>
    <div id="lineMain" style="width: 600px;height:400px;"></div>

  </div>
</template>

<script>
  import echarts from "echarts/lib/echarts";
  // 引入柱状图
  import "echarts/lib/chart/bar";
  // 拆线
  import "echarts/lib/chart/line";
  // 饼状
  import "echarts/lib/chart/pie";


  import "echarts/lib/component/title";
  import "echarts/lib/component/legend";
  import "echarts/lib/component/toolbox";
  import "echarts/lib/component/markPoint";
  import "echarts/lib/component/tooltip";

  export default {
    name: "jobAutoTaskCharts",
    data() {
      return {
        barOption: {
          title: {
            text: 'ECharts 入门示例'
          },
          tooltip: {},
          legend: {
            data: ['销量']
          },
          xAxis: {
            data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
          },
          yAxis: {},
          series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }]
        },
        pieOption: {
          series: {
            type: 'pie',
            data: [
              {name: 'A', value: 1212},
              {name: 'B', value: 2323},
              {name: 'C', value: 1919}
            ]
          }
        },
        lineOption: {
          title: {text: 'Line Chart'},
          tooltip: {},
          toolbox: {
            feature: {
              dataView: {},
              saveAsImage: {
                pixelRatio: 2
              },
              restore: {}
            }
          },
          xAxis: {},
          yAxis: {},
          series: [{
            type: 'line',
            smooth: true,
            data: [[12, 5], [24, 20], [36, 36], [48, 10], [60, 10], [72, 20]]
          }]
        }
      }
    },
    mounted() {
      var barChart = echarts.init(document.getElementById('barMain'));
      // 使用刚指定的配置项和数据显示图表。
      barChart.setOption(this.barOption);

      var pieMainChart = echarts.init(document.getElementById('pieMain'));
      // 使用刚指定的配置项和数据显示图表。
      pieMainChart.setOption(this.pieOption);

      var lineMainChart = echarts.init(document.getElementById('lineMain'));
      // 使用刚指定的配置项和数据显示图表。
      lineMainChart.setOption(this.lineOption);
    }
  }
</script>

<style scoped>

</style>
