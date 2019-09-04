<template>
  <div>
    <el-row>
      <el-collapse value="1">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :inline="true" :model="queryFilter" ref="queryFilter" class="demo-form-inline" size="mini">
            <el-row>
              <el-form-item label="任务ID" prop="taskId">
                <el-input v-model="queryFilter.taskId" placeholder="任务ID"></el-input>
              </el-form-item>
              <el-form-item label="申请人" prop="applyUserCode">
                <el-input v-model="queryFilter.applyUserCode" placeholder="申请人"></el-input>
              </el-form-item>
              <el-form-item label="申请类型" prop="applyType" width="210">
                <custom-select v-model="queryFilter.applyType"
                               :datasource="applyTypeSelect"
                               placeholder="请选择申请类型">
                </custom-select>
              </el-form-item>
            </el-row>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query :show-add="false" @onQuery="loadDataList" :queryFilter="queryFilter"></button-group-query>
    <el-row>
      <template>
        <el-table border style="width: 100%" ref="taskListRef" :data="tableData" row-key="id">
          <el-table-column label="任务ID" prop="taskId"></el-table-column>
          <el-table-column label="申请人" prop="applyUserCode"></el-table-column>
          <el-table-column label="申请类型" prop="applyType" width="210">
            <template slot-scope="scope">
              <custom-select v-model="scope.row.applyType"
                             :datasource="applyTypeSelect"
                             :value-key="scope.row.applyType"
                             disabled placeholder="请选择申请类型">
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column label="是否结束" prop="isEnd">
            <template slot-scope="scope">
              <el-form-item label="是否有效">
                <el-switch v-model="scope.row.isEnd" active-text="运行" inactive-text="结束" disabled></el-switch>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column type="expand" label="详细" width="50">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand" label-width="120px">
                <el-form-item label="创建人">
                  <span>{{ scope.row.creatorCode }}</span>
                </el-form-item>
                <el-form-item label="更新人">
                  <span>{{ scope.row.updaterCode }}</span>
                </el-form-item>
                <el-form-item label="创建时间">
                  <el-date-picker type="datetime" v-model="scope.row.createTime" readonly></el-date-picker>
                </el-form-item>
                <el-form-item label="更新时间">
                  <el-date-picker type="datetime" v-model="scope.row.updateTime" readonly></el-date-picker>
                </el-form-item>
                <el-form-item label="是否有效">
                  <el-switch v-model="scope.row.validInd" active-text="有效" inactive-text="无效" disabled></el-switch>
                </el-form-item>
                <el-form-item label="定义ID">
                  <span>{{ scope.row.procDefId }}</span>
                </el-form-item>
                <el-form-item label="实例ID">
                  <span>{{ scope.row.procInstId }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <!--  操作按钮组 -->
              <el-tooltip class="item" effect="dark" content="处理" placement="top-start" :open-delay="openDelay"
                          v-if="scope.row.status === '0'">
                <el-button @click="onHandle(scope.row,scope.$index)" type="primary" size="mini">
                  <i class="el-icon-service"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="认领" placement="top-start" :open-delay="openDelay"
                          v-if="scope.row.status === '1'">
                <el-button @click="onClaim(scope.row,scope.$index)" type="warning" size="mini">
                  <i class="el-icon-thumb"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="查看图片" placement="top-start" :open-delay="openDelay">
                <el-button type="success" size="mini" @click="viewDeployImg(scope.row,scope.$index)">
                  <i class="el-icon-picture"></i>
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-row>
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
  </div>
</template>

<script>
  // ===== api start
  import {
    LoadDataTaskList,
    ClaimTask,
  } from "../../api/proc/proceTaskMag";
  import {
    LoadSysDataDictionary
  } from "../../api/sys/DataDictionaryMag";
  // ===== component start
  import CustomSelect from '../../components/common/CustomSelect.vue';
  // import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue'
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  export default {
    components: {
      CustomSelect,
      // ButtonGroupOption,
      ButtonGroupQuery,
      CustomPage
    },
    mixins: [pageMixins],
    data() {
      return {
        openDelay: 1500,
        queryFilter: {
          // 查询条件
          taskId: null,
          applyUserCode: null,
          applyType: null,
        },
        tableData: [], // 列表数据源
        applyTypeSelect: [],// 申请类型的下拉数据源
      }
    },
    created() {
      // 加载申请类型的下拉
      LoadSysDataDictionary('applyType').then(res => {
        this.applyTypeSelect = res.resData;
      });
      // 请数据殂
      this.loadDataList();
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('taskInfo', function (data) {
        var index = this.tableData.findIndex(f => f.id === data.id);
        if (index != -1) { // 更新
          this.tableData.splice(index, 1, data);
        } else { // 添加
          this.tableData.push(data);
        }
      }.bind(this));
    },
    methods: {
      // 获取权限列表数据
      loadDataList() {
        LoadDataTaskList(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.content;
          this.queryFilter.recordCount = data.totalElements;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      viewDeployImg(row, index) {
        const {href} = this.$router.resolve({
          name: "procViewProcessImg",
          query: {
            id: row.id
          }
        });
        window.open(href, '_blank');
      },
      // 认领
      onClaim(row, index) {
        var _this = this;
        ClaimTask(row.taskId).then(res => {
          _this.$message({
            type: 'success',
            message: '认领成功!'
          });
          row.status = '0';
        });
      },
      // 处理
      onHandle(row, index) {
        console.info("applyType:", row.applyType);
        // this.$router.push({
        //   name: 'todoLeaveIndex',
        //   query: {
        //     optionType: 'edit',
        //     id: row.id
        //   }
        // });
      }
    }
  }
</script>

<style scoped>
  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 3px;
    /*滚动条宽度*/
    height: 3px;
    /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px;
    /*滚动条的背景区域的圆角*/
    background-color: white;
    /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #2e363f;
    /*滚动条的背景颜色*/
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
