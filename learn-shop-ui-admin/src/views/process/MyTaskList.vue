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
                               :clearable="true"
                               :datasource="applyTypeSelect"
                               placeholder="请选择申请类型">
                </custom-select>
              </el-form-item>
              <el-form-item label="是否结束" prop="isEndStatus" width="210">
                <el-select v-model="queryFilter.isEndStatus" clearable placeholder="请选择申请类型">
                  <el-option key="1" label="审批结束" value="1"></el-option>
                  <el-option key="0" label="进行中" value="0"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="待办项目">
                <el-select v-model="toDo" @change="changToDo">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
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
              <custom-select v-model="scope.row.applyType" clearable
                             :datasource="applyTypeSelect"
                             :value-key="scope.row.applyType"
                             disabled placeholder="请选择申请类型">
              </custom-select>
            </template>
          </el-table-column>
          <el-table-column label="是否结束" prop="isEnd">
            <template slot-scope="scope">
              {{scope.row.isEnd ? '是' : '否'}}
            </template>
          </el-table-column>
          <el-table-column label="是否挂起" prop="suspensionStatus">
            <template slot-scope="scope">
              {{scope.row.suspensionStatus | suspensionStatusFilter}}
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
              <div style="float:left;">
                <!--  操作按钮组 -->
                <el-tooltip class="item" effect="dark" content="处理" placement="top-start" :open-delay="openDelay"
                            v-if="scope.row.claimStatus === '0' && toDo === 'myTasks' && scope.row.suspensionStatus === 1">
                  <el-button @click="onHandle(scope.row,scope.$index)" type="primary" size="mini">
                    <i class="el-icon-service"></i>
                  </el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="认领" placement="top-start" :open-delay="openDelay"
                            v-if="scope.row.claimStatus === '1' && toDo === 'myTasks' && scope.row.suspensionStatus === 1">
                  <el-button @click="onClaim(scope.row,scope.$index)" type="warning" size="mini">
                    <i class="el-icon-thumb"></i>
                  </el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="查看详细" placement="top-start" :open-delay="openDelay"
                            v-if="toDo === 'myStart' || toDo === 'ongoing'">
                  <el-button @click="viewDetile(scope.row,scope.$index)" type="warning" size="mini">
                    <i class="el-icon-view"></i>
                  </el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="删除" placement="top-start" :open-delay="openDelay"
                            v-if="scope.row.isEnd && toDo === 'myStart'">
                  <el-button @click="onDel(scope.row,scope.$index)" type="danger" size="mini">
                    <i class="el-icon-delete"></i>
                  </el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="查看图片" placement="top-start" :open-delay="openDelay">
                  <el-button type="success" size="mini" @click="viewExecutionImg(scope.row,scope.$index)">
                    <i class="el-icon-picture"></i>
                  </el-button>
                </el-tooltip>
              </div>
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
    DeleteApplyInfoById,
    MyStartProdeList,
  } from "../../api/proc/applyMag";
  import {
    LoadSysDataDictionary
  } from "../../api/sys/DataDictionaryMag";

  // ===== component start
  import CustomSelect from '../../components/common/CustomSelect.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue'
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  export default {
    components: {
      CustomSelect,
      ButtonGroupQuery,
      CustomPage
    },
    mixins: [pageMixins],
    data() {
      return {
        openDelay: 1500,
        options: [{
          value: 'myTasks',
          label: '我的任务'
        }, {
          value: 'myStart',
          label: '我发起的'
        }, {
          value: 'ongoing',
          label: '进行中的'
        }],
        toDo: 'myTasks',// 待办项目,默认为：我的任务列表
        queryFilter: {
          // 查询条件
          taskId: null,
          applyUserCode: null,
          applyType: null,
          isEnd: null,
          isEndStatus: null
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
    methods: {
      // 获取权限列表数据
      loadDataList() {
        if (this.$route.query.command) {
          this.toDo = this.$route.query.command;
        }

        // 从todo过来时，设置参数
        if (this.toDo === 'ongoing') {
          this.queryFilter.isEnd = false;
          this.queryFilter.isEndStatus = '0';
        }

        if (this.queryFilter.applyType === '') {
          this.queryFilter.applyType = null;
        }

        if (this.toDo === 'myStart' || this.toDo === 'ongoing') {
          MyStartProdeList(this.queryFilter).then(res => {
            var data = res.resData;
            this.tableData = data.tableData;
            this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
          });
        } else {
          LoadDataTaskList(this.queryFilter).then(res => {
            var data = res.resData;
            this.tableData = data.tableData;
            this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
          });
        }
      },
      // 查看执行流程
      viewExecutionImg(row, index) {
        const {href} = this.$router.resolve({
          name: "procViewProcessImg",
          query: {
            proceImgId: row.procInstId,
            proceType: 'execution'
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
          row.claimStatus = '0';
        });
      },
      // 处理
      onHandle(row, index) {
        this.$router.push({
          name: 'workbenchApplyInfo',
          query: {
            optionType: 'commit',
            applyType: row.applyType,
            applyId: row.id,
            taskId: row.taskId,
            // 流程图使用
            proceType: 'execution',
            proceImgId: row.procInstId,
            // 批注使用
            procInstId: row.procInstId,
          }
        });
      },
      // 删除已经结束的申请信息
      onDel(row, index) {
        var _this = this;
        VueUtils.confirmDel(row.mailCode, () => {
          DeleteApplyInfoById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      },
      // 查看详细
      viewDetile(row, index) {
        this.$router.push({
          name: 'workbenchApplyInfo',
          query: {
            optionType: 'view',
            applyType: row.applyType,
            applyId: row.id,
            proceType: 'execution',
            proceImgId: row.procInstId,
            procInstId: row.procInstId,
          }
        });
      },
      changToDo() {
        this.queryFilter.isEnd = null;
        this.queryFilter.isEndStatus = null;
        this.queryFilter.pageNo = 1;
        this.loadDataList();
      }
    },
    filters: {
      suspensionStatusFilter(value) {
        var statusName = '';
        if (value === 1) {
          statusName = '活动';
        } else if (value === 2) {
          statusName = '挂起'
        }
        return statusName;
      }
    },
    watch: {
      $route() {
        // 从todo过来时，设置参数
        // this.toDo = 'myTasks';
        this.queryFilter.isEnd = null;
        this.queryFilter.isEndStatus = null;
        this.loadDataList();
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
