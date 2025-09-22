<template>
  <div>
    <el-row>
      <el-table :data="tableData" border stripe style="width:100%">
        <el-table-column label="规格编号" prop="specNo"></el-table-column>
        <el-table-column label="规格名称" prop="specName">
          <template slot-scope="scope">
            <el-input v-model="scope.row.specName" :readonly="readonly" placeholder="请输入内容"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="规格排序" prop="keySort" width="200">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.keySort" :min="0" :disabled="readonly" size="mini"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                 @onInd="handleProhibit(scope.row,scope.$index)"
                                 :show-edit="false"
                                 :disInd="!scope.row.validInd"
                                 v-if="showOption"/>
            <div style="float:left;margin-left:10px;">
              <el-tooltip class="item" effect="dark" content="修改规格值" placement="top-start" :open-delay="1500">
                <el-button type="success" size="mini" v-if="scope.row.id"
                           @click="editSpecValue(scope.row,scope.$index)">
                  <i class="el-icon-thumb"></i>
                </el-button>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
        <el-table-column type="expand" label="详细" width="50">
          <template slot-scope="scope">
            <el-form label-position="left" inline class="ms-table-expand" label-width="120px">
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
    <!-- 规格value  -->
    <el-dialog :closeOnClickModal="false" :title="tableTitle" :visible.sync="dialogTableVisible"
               v-if="dialogTableVisible">
      <goods-spec-value-list :spec-key-id="specKeyId" v-if="dialogTableVisible" ref="GoodsSpecValueListRef"/>
    </el-dialog>
  </div>
</template>


<script>
  import {FindKeyListByCategoryId, ProhibitById, DelById, SaveSpecKeyList} from "../../api/product/GoodsSpecKeyApi";
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";

  // ===== component start
  import GoodsSpecValueList from './GoodsSpecValueList.vue';

  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';

  export default {
    props: {
      readonly: {
        type: Boolean,
        default: true
      },
      showOption: {
        type: Boolean,
        default: false
      },
      // 商品分类id
      categoryId: {
        type: String,
        default: null
      }
    },
    components: {
      GoodsSpecValueList,
      ButtonGroupOption,
    },
    data() {
      return {
        dialogTableVisible: false,// 打开规格Value窗口
        tableTitle: '',// KEY name
        specKeyId: null,// 规格KEY ID
        tableData: [],
        activeNames: ['1']
      }
    },
    created() {
      // 请数据殂
      this.loadDataList();
    },
    methods: {
      // 获取权限列表数据
      loadDataList() {
        FindKeyListByCategoryId(this.categoryId).then(res => {
          this.tableData = res.resData;
        });
      },
      // 添加权限
      handleAdd() {
        this.tableData.push({
          categoryId: this.categoryId,
          specName: '',
          keySort: 0
        });
      },
      saveList() {
        SaveSpecKeyList(this.tableData).then(res => {
          this.tableData = res.resData;
        });
      },
      handleDelete(row, index) {
        var _this = this;

        if (!row.id) {
          _this.tableData.splice(index, 1);
          return;
        }

        VueUtils.confirmDel(row.specName, () => {
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

        VueUtils.confirmInd(row.specName, () => {
          ProhibitById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      editSpecValue(row, index) {
        this.dialogTableVisible = true;
        this.specKeyId = row.id;
        this.tableTitle = row.specName;
      }
    }
  }
</script>

