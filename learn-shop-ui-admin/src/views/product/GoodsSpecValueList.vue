<template>
  <div>
    <el-row>
      <el-table :data="tableData" border style="width:100%">
        <el-table-column label="规格值名称" prop="specValue">
          <template slot-scope="scope">
            <el-input v-model="scope.row.specValue" :readonly="scope.row.readonly" placeholder="请输入内容"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="规格值排序" prop="valueSort" width="200">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.valueSort" :min="0" :disabled="scope.row.readonly"
                             size="mini"></el-input-number>
          </template>
        </el-table-column>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                 @onInd="handleProhibit(scope.row,scope.$index)"
                                 :show-edit="scope.row.id !== null && scope.row.readonly"
                                 :show-ind="scope.row.id !== null"
                                 @onEdit="scope.row.readonly = false"/>
            <div style="float:left;margin-left:10px;">
              <el-tooltip class="item" effect="dark" content="保存" placement="top-start" :open-delay="1500">
                <el-button type="success" size="mini"
                           v-show="!scope.row.readonly"
                           @click="saveSpecValue(scope.row,scope.$index)">
                  <i class="iconfont icon-createtask"></i>
                </el-button>
              </el-tooltip>
            </div>
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
            </el-form>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <div style="float:right;">
      <el-tooltip class="item" effect="dark" content="增加" placement="top-start" :open-delay="1500">
        <el-button type="success" size="mini" @click="addSpecValue">
          <i class="iconfont icon-add"></i>
        </el-button>
      </el-tooltip>
    </div>
  </div>
</template>


<script>
  import {FindValueListBySpecKeyId, ProhibitById, DelById, Add, Update} from "../../api/product/GoodsSpecValueApi";
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";

  // ===== component start
  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';

  export default {
    props: {
      // 规格KEY id
      specKeyId: {
        type: String,
        default: null
      }
    },
    components: {
      ButtonGroupOption,
    },
    data() {
      return {
        dialogTableVisible: false,// 打开规格Value窗口
        tableTitle: '',// KEY name
        tableData: [],
        activeNames: ['1']
      }
    },
    created() {
      // 请数据殂
      this.loadDataList();
    },
    methods: {
      // 获取规格值列表数据
      loadDataList() {
        FindValueListBySpecKeyId(this.specKeyId).then(res => {
          this.tableData = res.resData;
        });
      },
      // 添加规格值
      addSpecValue() {
        this.tableData.push({
          readonly: false,
          id: null,
          specKeyId: this.specKeyId,
          specValue: '',
          valueSort: 0
        });
      },
      handleDelete(row, index) {
        var _this = this;
        if (!row.id) {
          _this.tableData.splice(index, 1);
          return;
        }

        VueUtils.confirmDel(row.specValue, () => {
          DelById(row.id).then(res => {
            _this.tableData.splice(index, 1);
            _this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        });
      },
      handleProhibit(row, index) {
        var _this = this;
        ProhibitById(row.id).then(res => {
          row.validInd = res.resData.validInd;
          _this.$message({
            type: 'success',
            message: '操作成功!'
          });
        });
      },
      saveSpecValue(row, index) {
        if (row.id) {
          Update(row).then(res => {
            this.tableData.splice(index, 1, res.resData);
          });
        } else {
          Add(row).then(res => {
            this.tableData.splice(index, 1, res.resData);
          });
        }
      }
    }
  }
</script>

<style scoped>
  .el-row {
    margin-bottom: 5px;
  }

  .el-form-item {
    margin-bottom: 3px;
  }

  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 3px; /*滚动条宽度*/
    height: 3px; /*滚动条高度*/
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 10px; /*滚动条的背景区域的圆角*/
    background-color: white; /*滚动条的背景颜色*/
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px; /*滚动条的圆角*/
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #2e363f; /*滚动条的背景颜色*/
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
