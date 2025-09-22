<template>
  <div>
    <el-row>
      <el-collapse v-model="activeNames">
        <el-collapse-item name="1">
          <template slot="title">
            <b>查询条件</b><i class="el-icon-search"></i>
          </template>
          <el-form :model="queryFilter" ref="queryFilter" inline size="mini">
            <el-form-item label="分类名称" prop="categoryName">
              <el-input v-model="queryFilter.categoryName" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-row>
    <!-- 查询按钮组 -->
    <button-group-query @onAdd="handleAdd" @onQuery="loadDataList" :queryFilter="queryFilter"/>
    <el-row>
      <el-table :data="tableData" border stripe style="width:100%">
        <el-table-column label="分类名称" prop="categoryName"></el-table-column>
        <el-table-column label="分类排序" prop="categorySort" width="200"></el-table-column>
        <el-table-column fixed="right" label="操作" width="240">
          <template slot-scope="scope">
            <!--  操作按钮组 -->
            <button-group-option @onDel="handleDelete(scope.row,scope.$index)"
                                 @onEdit="handleEdit(scope.row,scope.$index)"
                                 @onInd="handleProhibit(scope.row,scope.$index)"
                                 :disInd="!scope.row.validInd"></button-group-option>
<!--            <div style="float:left;margin-left:10px;">-->
<!--              <el-tooltip class="item" effect="dark" content="查询商品规格" placement="top-start" :open-delay="1500">-->
<!--                <el-button type="success" size="mini" @click="handleSpceKey(scope.row,scope.$index)">-->
<!--                  <i class="el-icon-view"></i>-->
<!--                </el-button>-->
<!--              </el-tooltip>-->
<!--            </div>-->
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
    <!-- 分页组件  -->
    <custom-page :queryPage="queryFilter" @onQuery="loadDataList"></custom-page>
    <el-dialog :title="tableTitle" :visible.sync="dialogTableVisible" v-if="dialogTableVisible">
      <goods-spec-key-list :category-id="categoryId" v-if="dialogTableVisible"/>
    </el-dialog>
  </div>
</template>


<script>
  import {FindListByPage, ProhibitById,DelById} from "../../api/product/GoodsCategoryApi";
  // ===== 工具类 start
  import VueUtils from "../../utils/vueUtils";
  import pageMixins from "../../utils/pageMixins";

  // ===== component start
  import GoodsSpecKeyList from './GoodsSpecKeyList';

  import ButtonGroupOption from '../../components/common/ButtonGroupOption.vue';
  import ButtonGroupQuery from '../../components/common/ButtonGroupQuery.vue';
  import CustomPage from '../../components/common/CustomPage.vue'

  export default {
    name: "proGoodsCategoryListIndex",
    components: {
      GoodsSpecKeyList,
      ButtonGroupOption,
      ButtonGroupQuery,
      CustomPage
    },
    mixins: [pageMixins],
    data() {
      return {
        dialogTableVisible:false,// 打开规格窗口
        tableTitle: '',// 分类名称
        categoryId: null,// 商品分类id
        queryFilter: {
          // 查询条件
          categoryName: null,
        },
        tableData: [],
        activeNames: ['1']
      }
    },
    created() {
      // 请数据殂
      this.loadDataList();
    },
    //每次激活时
    activated() {
      // 根据key名获取传递回来的参数，data 就是 map
      this.$bus.once('notifyInfo', function (data) {
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
        FindListByPage(this.queryFilter).then(res => {
          var data = res.resData;
          this.tableData = data.tableData;
          this.queryFilter.recordCount = data.recordCount;
          this.queryFilter.totalPages = data.totalPages;
        });
      },
      // 添加权限
      handleAdd() {
        this.$router.push({
          name: 'proGoodsCategoryEdit',
          query: {
            optionType: 'add',
          }
        });
      },
      handleEdit(row, index) {
        this.$router.push({
          name: 'proGoodsCategoryEdit',
          query: {
            optionType: 'edit',
            editData: JSON.stringify(row),
          }
        });
      },
      handleDelete(row, index) {
        var _this = this;

        VueUtils.confirmDel(row.categoryName, () => {
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

        VueUtils.confirmInd(row.categoryName, () => {
          ProhibitById(row.id).then(res => {
            row.validInd = res.resData.validInd;
            _this.$message({
              type: 'success',
              message: '禁用成功!'
            });
          });
        });
      },
      // handleSpceKey(row,index){
      //   this.dialogTableVisible = true;
      //   this.categoryId = row.id;
      //   this.tableTitle = row.categoryName;
      // }
    }
  }
</script>

